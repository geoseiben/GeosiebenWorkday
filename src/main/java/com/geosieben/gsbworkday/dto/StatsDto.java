package com.geosieben.gsbworkday.dto;

public class StatsDto {
    private Long pendingLeaves;
    private Long pendingtickets;
    private Long totalActiveEmployees;
    private Long activeMale;
    private  Long activeFemale;
    private Long totalSeparatedEmployees;
    private Long separatedMale;
    private  Long separatedFemale;

    public StatsDto(Long totalActiveEmployees,Long activeMale, Long activeFemale,  Long totalSeparatedEmployees, Long separatedMale, Long separatedFemale, Long pendingLeaves,Long pendingtickets) {
        this.pendingLeaves = pendingLeaves;
        this.pendingtickets = pendingtickets;
        this.totalActiveEmployees = totalActiveEmployees;
        this.activeMale = activeMale;
        this.activeFemale = activeFemale;
        this.totalSeparatedEmployees = totalSeparatedEmployees;
        this.separatedMale = separatedMale;
        this.separatedFemale = separatedFemale;
    }

    public Long getPendingLeaves() {
        return pendingLeaves;
    }

    public void setPendingLeaves(Long pendingLeaves) {
        this.pendingLeaves = pendingLeaves;
    }

    public Long getPendingtickets() {
        return pendingtickets;
    }

    public void setPendingtickets(Long pendingtickets) {
        this.pendingtickets = pendingtickets;
    }

    public Long getTotalActiveEmployees() {
        return totalActiveEmployees;
    }

    public void setTotalActiveEmployees(Long totalActiveEmployees) {
        this.totalActiveEmployees = totalActiveEmployees;
    }

    public Long getActiveMale() {
        return activeMale;
    }

    public void setActiveMale(Long activeMale) {
        this.activeMale = activeMale;
    }

    public Long getActiveFemale() {
        return activeFemale;
    }

    public void setActiveFemale(Long activeFemale) {
        this.activeFemale = activeFemale;
    }

    public Long getTotalSeparatedEmployees() {
        return totalSeparatedEmployees;
    }

    public void setTotalSeparatedEmployees(Long totalSeparatedEmployees) {
        this.totalSeparatedEmployees = totalSeparatedEmployees;
    }

    public Long getSeparatedMale() {
        return separatedMale;
    }

    public void setSeparatedMale(Long separatedMale) {
        this.separatedMale = separatedMale;
    }

    public Long getSeparatedFemale() {
        return separatedFemale;
    }

    public void setSeparatedFemale(Long separatedFemale) {
        this.separatedFemale = separatedFemale;
    }
}
