package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public class LeaveRequestDto {
    private String leaveType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String fromSession;
    private String toSession;
    private String comment;
    private double days;

    public LeaveRequestDto(String leaveType, LocalDate fromDate, LocalDate toDate, String fromSession, String toSession, String comment, double days) {
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromSession = fromSession;
        this.toSession = toSession;
        this.comment = comment;
        this.days = days;
    }
    public  LeaveRequestDto(){}


    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getFromSession() {
        return fromSession;
    }

    public void setFromSession(String fromSession) {
        this.fromSession = fromSession;
    }

    public String getToSession() {
        return toSession;
    }

    public void setToSession(String toSession) {
        this.toSession = toSession;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }
}
