package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.Department;

import java.util.List;

public class DepartmentDto {
    private Integer id;
    private String name;
    private List<TeamDto> teams;

    public DepartmentDto() {}

    public DepartmentDto(Department department){
        this.id = department.getId();
        this.name = department.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDto> teams) {
        this.teams = teams;
    }

}
