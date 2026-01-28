package com.geosieben.gsbworkday.dto;
import java.time.LocalDate;

public class PendingLeaveResponseDto {
    private int id;
    private String employeename;
    private String eid;
    private String leavetype;
    private String reason;
    private LocalDate fromdate;
    private LocalDate todate;
    private double days;
    private String fromSession;
    private String toSession;

    public PendingLeaveResponseDto(int id, String employeename, String eid, String leavetype, String reason, LocalDate fromdate, LocalDate todate, double days, String fromSession, String toSession) {
        this.id = id;
        this.employeename = employeename;
        this.eid = eid;
        this.leavetype = leavetype;
        this.reason = reason;
        this.fromdate = fromdate;
        this.todate = todate;
        this.days = days;
        this.fromSession = fromSession;
        this.toSession = toSession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getLeavetype() {
        return leavetype;
    }

    public void setLeavetype(String leavetype) {
        this.leavetype = leavetype;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getFromdate() {
        return fromdate;
    }

    public void setFromdate(LocalDate fromdate) {
        this.fromdate = fromdate;
    }

    public LocalDate getTodate() {
        return todate;
    }

    public void setTodate(LocalDate todate) {
        this.todate = todate;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
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
}
