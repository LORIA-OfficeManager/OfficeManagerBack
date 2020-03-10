package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {

    public List<Department> fetchAll();

    public Optional<Department> findById(int id);

    public Department findByName(String name);

    public Department saveDepartment(Department department);

    public void deleteById(Integer id);

    public boolean isAuthorisedName(String name);

    public Department getDefault();
}
