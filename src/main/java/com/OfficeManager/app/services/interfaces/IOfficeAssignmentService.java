package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.OfficeAssignment;

import java.util.List;

public interface IOfficeAssignmentService {

    public List<OfficeAssignment> fetchAll();

    public OfficeAssignment saveOfficeAssignement(OfficeAssignment officeAssignment);
}
