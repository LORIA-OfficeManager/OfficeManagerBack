package com.OfficeManager.services.interfaces;

import com.OfficeManager.entities.Department;

import java.util.List;

public interface IDepartmentService {

    public List<Department> fetchAll();
}
