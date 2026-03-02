package com.geosieben.gsbworkday.service;


import com.geosieben.gsbworkday.entity.BookedHours;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.BankInfoRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.BookerHoursRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TrackerService {

    private final BankInfoRepository bankInfoRepository;

    @Autowired
    private AllocationRepository repository;
    @Autowired 
    BookerHoursRepository bookerHoursRepository;
    @Autowired
    private BasicInfoRepository basicInfoRepository;


    TrackerService(BankInfoRepository bankInfoRepository) {
        this.bankInfoRepository = bankInfoRepository;
    }

    @Transactional
    public ProjectAllocation handleAction(int id, String action, String employeeId,String percentage) {
        ProjectAllocation task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task ID " + id + " not found"));

        long nowMs = System.currentTimeMillis();

        return switch (action.toLowerCase()) {
            case "start", "resume" -> startTask(task, nowMs);
            case "pause", "stop" -> pauseTask(task, nowMs,percentage,employeeId);
            case "submit" -> submitTask(task, nowMs);
            case "rejectqc" -> rejectQcTask(task, nowMs);
            default -> throw new IllegalArgumentException("Unknown action: " + action);
        };
    }

    /**
     * Logic for Starting or Resuming a task
     */
    private ProjectAllocation startTask(ProjectAllocation task, long nowMs) {
        int startStatus;
        // Rework logic: If status was 8 (Rework Paused) or 7 (Rework Priority) -> 9 (Rework Running)
        if (task.getStatus() != null && (task.getStatus() == 8 || task.getStatus() == 7)) {
            startStatus = 9;
        } else {
            // QC logic vs Prod logic
            startStatus = "QC".equalsIgnoreCase(task.getType()) ? 5 : 2;
        }

        task.setIsRunning(true);
        task.setStartTime(nowMs);
        task.setStatus(startStatus);
        
        return repository.save(task);
    }

    /**
     * Logic for Pausing a task
     */
    private ProjectAllocation pauseTask(ProjectAllocation task, long nowMs,String percentage,String eid) {
        if (Boolean.TRUE.equals(task.getIsRunning())) {
            updateElapsedTime(task, nowMs);
        }
        int percent=Integer.parseInt(percentage);
        BigDecimal hours=((BigDecimal) (task.getType().equals("Prod")?task.getHrsAssigned():task.getQcHrs()));
        double parsedHours=hours.doubleValue();
        if(percent>0){
            BookedHours current=(BookedHours) bookerHoursRepository.findbydateAndAllotmentforToday(eid, task.getAllotmentid());
            if(current!=null){
                int  percentageExisting=current.getCompletionPercentage();
                double bookebalHours=(((parsedHours/100)*(percent-percentageExisting)))+current.getHoursBooked().doubleValue();
                current.setCompletionPercentage(percent);
                current.setHoursBooked(BigDecimal.valueOf(bookebalHours));
                current.setUpdatedOn(LocalDateTime.now());
                bookerHoursRepository.save(current);
            }
            else{

                 BookedHours existingHours=bookerHoursRepository.findbyAndAllotmentforBookedSoFar(eid, task.getAllotmentid());
                 if(existingHours==null){
                    BookedHours newHours=new BookedHours();
                    newHours.setCompletionPercentage(percent);
                    double bookebalHours=(((parsedHours/100)*(percent)));
                    newHours.setDate(LocalDate.now());
                    newHours.setHoursBooked(BigDecimal.valueOf( bookebalHours));
                    newHours.setProjectAllocation(task);
                    newHours.setEmployeeBasicInfo((EmployeeBasicInfo)basicInfoRepository.getReferenceById(eid));
                    newHours.setType(task.getType());
                    bookerHoursRepository.save(newHours);
                 }
                 else {
                    BookedHours lastHoubsBooked=(BookedHours) bookerHoursRepository.findbyAndAllotmentforBookedSoFar(eid, task.getAllotmentid());
                int  percentageExisting=lastHoubsBooked.getCompletionPercentage();
                double bookebalHours=(((parsedHours/100)*(percent-percentageExisting)))+lastHoubsBooked.getHoursBooked().doubleValue();
                BookedHours todaysHours= new BookedHours();
                todaysHours.setDate(LocalDate.now());
                todaysHours.setEmployeeBasicInfo(lastHoubsBooked.getEmployeeBasicInfo());
                todaysHours.setType(lastHoubsBooked.getType());
                todaysHours.setCompletionPercentage(percent);
                todaysHours.setHoursBooked(BigDecimal.valueOf(bookebalHours));
                todaysHours.setUpdatedOn(LocalDateTime.now());
                todaysHours.setProjectAllocation(task);
                bookerHoursRepository.save(todaysHours);
                 }

            }
        }
        int pauseStatus = 1; // Default: Prod Paused
        if (task.getStatus() != null && task.getStatus() == 9) {
            pauseStatus = 8; // Rework Paused
        } else if ("QC".equalsIgnoreCase(task.getType())) {
            pauseStatus = 4; // QC Paused
        }

        task.setIsRunning(false);
        task.setStartTime(null);
        task.setStatus(pauseStatus);
        task.setPausedOn(LocalDateTime.now());
        
        return repository.save(task);
    }

    /**
     * Logic for Submitting/Completing a task
     */
    private ProjectAllocation submitTask(ProjectAllocation task, long nowMs) {
        // Prevent resubmitting if already in a completed status (3, 6, 10)
        List<Integer> finalStatuses = Arrays.asList(3, 6, 10);
        if (finalStatuses.contains(task.getStatus())) {
            throw new IllegalStateException("Task is already completed or submitted.");
        }

        updateElapsedTime(task, nowMs);
        LocalDate today = LocalDate.now();

        int newStatus;
        int qcTrigger = 0;

        if (task.getStatus() != null && task.getStatus() == 9) {
            newStatus = 10; // Rework Completed
        } else if ("Prod".equalsIgnoreCase(task.getType()) || "Pilot".equalsIgnoreCase(task.getType())) {
            
            if (task.getQcHrs() == null || task.getQcHrs().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                newStatus = 3; // Prod Completed (No QC required)
            } else {
                newStatus = 3; // Prod Submitted (QC required)
                qcTrigger = 1;
                ProjectAllocation allotment=new ProjectAllocation();
                allotment.setType("QC");
                allotment.setQcAssinee(task.getQcAssinee());
                allotment.setQcHrs(task.getQcHrs());
                allotment.setStatus(4);
                allotment.setAllottedBy(task.getAllottedBy());
                allotment.setProject(task.getProject());
                allotment.setQcDeadline(task.getQcDeadline());
                allotment.setAllotmentOn(LocalDateTime.now());
                allotment.setFeederAlloted(task.getFeederAlloted());
                repository.save(allotment);
            }
            task.setCompletionDate(today);
        } else if ("QC".equalsIgnoreCase(task.getType())) {
            newStatus = 6; // QC Complete
            task.setQcCompletionDate(today);
            
            // Sync completion date back to the original Production task
            repository.findRelatedProdTask(task.getFeederAlloted(), task.getProject().getPid())
                    .ifPresent(prod -> {
                        prod.setQcCompletionDate(today);
                        repository.save(prod);
                    });
        } else {
            newStatus = 3;
        }

        task.setIsRunning(false);
        task.setStartTime(null);
        task.setStatus(newStatus);
        task.setQcAllotted(qcTrigger);

        return repository.save(task);
    }

    /**
     * Logic for rejecting a QC task
     */
    private ProjectAllocation rejectQcTask(ProjectAllocation task, long nowMs) {
        if (!"QC".equalsIgnoreCase(task.getType())) {
            throw new IllegalArgumentException("Only QC tasks can be rejected.");
        }

        // 1. Stop the current QC task and set to Rejected status (7)
        updateElapsedTime(task, nowMs);
        task.setIsRunning(false);
        task.setStartTime(null);
        task.setStatus(7);

        // 2. Locate the linked Production task and set it to Rework Paused (8)
        repository.findRelatedProdTask(task.getFeederAlloted(), task.getProject().getPid())
                .ifPresent(prod -> {
                    prod.setIsRunning(false);
                    prod.setStatus(8);
                    repository.save(prod);
                });

        return repository.save(task);
    }

    /**
     * Helper to calculate and accumulate elapsed time
     */
    private void updateElapsedTime(ProjectAllocation task, long nowMs) {
        if (task.getStartTime() != null) {
            long sessionMs = nowMs - task.getStartTime();
            long totalMs = (task.getElapsedTime() != null ? task.getElapsedTime() : 0) + sessionMs;
            task.setElapsedTime(totalMs);
            task.setPausedTime(totalMs);
        }
    }

    public ProjectAllocation handleTaskToggle(int id, String string) {
        throw new UnsupportedOperationException("Unimplemented method 'handleTaskToggle'");
    }
}