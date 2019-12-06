package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IOfficeAssignmentDao;
import com.OfficeManager.app.entities.OfficeAssignment;
import com.OfficeManager.app.services.interfaces.IOfficeAssignmentService;
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
        return officeAssignmentDao.findAll();
    }

    @Override
    public OfficeAssignment saveOfficeAssignement(OfficeAssignment officeAssignment) {
        return officeAssignmentDao.save(officeAssignment);
    }
}
