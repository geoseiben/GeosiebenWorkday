package com.geosieben.gsbworkday.dto;

public class LeaveBalanceReposnseDto {
    private String EID;
    private String employeename;
    private double casualleaves;
    private double sickleaves;
    private double ristrictedleaves;
    private double earnedleaves;

    public LeaveBalanceReposnseDto(String EID,String employeename, double casualleaves, double sickleaves, double ristrictedleaves, double earnedleaves) {
        this.EID = EID;
        this.employeename=employeename;
        this.casualleaves = casualleaves;
        this.sickleaves = sickleaves;
        this.ristrictedleaves = ristrictedleaves;
        this.earnedleaves = earnedleaves;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public double getCasualleaves() {
        return casualleaves;
    }

    public void setCasualleaves(double casualleaves) {
        this.casualleaves = casualleaves;
    }

    public double getSickleaves() {
        return sickleaves;
    }

    public void setSickleaves(double sickleaves) {
        this.sickleaves = sickleaves;
    }

    public double getRistrictedleaves() {
        return ristrictedleaves;
    }

    public void setRistrictedleaves(double ristrictedleaves) {
        this.ristrictedleaves = ristrictedleaves;
    }

    public double getEarnedleaves() {
        return earnedleaves;
    }

    public void setEarnedleaves(double earnedleaves) {
        this.earnedleaves = earnedleaves;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }
}

