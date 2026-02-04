package com.geosieben.gsbworkday.dto;

import java.util.List;

import com.geosieben.gsbworkday.entity.Clients;
import com.geosieben.gsbworkday.entity.ProjectCategories;

public class ProjectRequiredDto {
    private List<EmployeeResponseDto> employees;
    private List<Clients> clients;
    private List<ProjectCategories> categories;
    public ProjectRequiredDto(List<EmployeeResponseDto> employees, List<Clients> clients,
            List<ProjectCategories> categories) {
        this.employees = employees;
        this.clients = clients;
        this.categories = categories;
    }
    public List<EmployeeResponseDto> getEmployees() {
        return employees;
    }
    public void setEmployees(List<EmployeeResponseDto> employees) {
        this.employees = employees;
    }
    public List<Clients> getClients() {
        return clients;
    }
    public void setClients(List<Clients> clients) {
        this.clients = clients;
    }
    public List<ProjectCategories> getCategories() {
        return categories;
    }
    public void setCategories(List<ProjectCategories> categories) {
        this.categories = categories;
    }
    

}
