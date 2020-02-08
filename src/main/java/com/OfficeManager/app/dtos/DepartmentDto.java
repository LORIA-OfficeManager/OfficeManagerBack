package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.Department;

public class DepartmentDto {
    private Integer id;
    private String name;

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
}
