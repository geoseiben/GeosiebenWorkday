package com.geosieben.gsbworkday.dto;

public class ActiveTickets {
    private int ticketid;
    private String issue;
    private String description;
    private String priority;
    private String host;
    private String anydeskid;
    private String employeename;
    private int status;

    
    public ActiveTickets(int ticketid, String issue, String description, String priority, String host, String anydeskid,
            String employeename, int status) {
        this.ticketid = ticketid;
        this.issue = issue;
        this.description = description;
        this.priority = priority;
        this.host = host;
        this.anydeskid = anydeskid;
        this.employeename = employeename;
        this.status = status;
    }
    public int getTicketid() {
        return ticketid;
    }
    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }
    public String getIssue() {
        return issue;
    }
    public void setIssue(String issue) {
        this.issue = issue;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getAnydeskid() {
        return anydeskid;
    }
    public void setAnydeskid(String anydeskid) {
        this.anydeskid = anydeskid;
    }
    public String getEmployeename() {
        return employeename;
    }
    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    

}
