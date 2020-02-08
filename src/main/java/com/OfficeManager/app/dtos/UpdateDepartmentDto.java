package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.Department;

public class UpdateDepartmentDto {
    private String name;

    public UpdateDepartmentDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}