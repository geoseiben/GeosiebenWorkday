package com.geosieben.gsbworkday.dto;

public class ClientWiseProjectResponseDto {
    private int cid;
    private String clientId;
    private String clientName;
    private long active;
    private long finished;
    private long withdrawn;
   private long total;
    // 1. You MUST have a default constructor for many JSON libraries
    public ClientWiseProjectResponseDto() {
    }

    // 2. You MUST have this constructor for the JPA "SELECT new" query
    public ClientWiseProjectResponseDto(int cid,String clientId, String clientName, long active, long finished, long withdrawn,long total) {
        this.cid=cid;
        this.clientId = clientId;
        this.clientName = clientName;
        this.active = active;
        this.finished = finished;
        this.withdrawn = withdrawn;
        this.total=total;
    }

    // Standard Getters and Setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public long getActive() { return active; }
    public void setActive(long active) { this.active = active; }

    public long getFinished() { return finished; }
    public void setFinished(long finished) { this.finished = finished; }

    public long getWithdrawn() { return withdrawn; }
    public void setWithdrawn(long withdrawn) { this.withdrawn = withdrawn; }

    public long getTotal() {
       return total;
    }

    public void setTotal(long total) {
       this.total = total;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    
}