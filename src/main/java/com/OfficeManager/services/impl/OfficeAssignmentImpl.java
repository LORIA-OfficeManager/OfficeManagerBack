package com.OfficeManager.services.impl;

import com.OfficeManager.daos.IOfficeAssignmentDao;
import com.OfficeManager.entities.OfficeAssignment;
import com.OfficeManager.services.interfaces.IOfficeAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("officeAssignmentService")
@Transactional
public class OfficeAssignmentImpl implements IOfficeAssignmentService {

    @Autowired
    private IOfficeAssignmentDao officeAssignmentDao;

    @Override
    public List<OfficeAssignment> fetchAll() {
        return officeAssignmentDao.fetchAll();
    }
}
