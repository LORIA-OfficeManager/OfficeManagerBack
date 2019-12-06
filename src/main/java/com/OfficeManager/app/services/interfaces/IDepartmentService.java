package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Department;

import java.util.List;

public interface IDepartmentService {

    public List<Department> fetchAll();
}
