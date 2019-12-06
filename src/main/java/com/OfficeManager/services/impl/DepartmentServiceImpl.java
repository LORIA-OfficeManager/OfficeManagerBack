package com.OfficeManager.services.impl;

import com.OfficeManager.daos.IDepartmentDao;
import com.OfficeManager.entities.Department;
import com.OfficeManager.services.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private IDepartmentDao departmentDao;

    @Override
    public List<Department> fetchAll() {
        return departmentDao.fetchAll();
    }
}
