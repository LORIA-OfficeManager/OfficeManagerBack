package com.OfficeManager.services.interfaces;

import com.OfficeManager.entities.OfficeAssignment;

import java.util.List;

public interface IOfficeAssignmentService {

    public List<OfficeAssignment> fetchAll();
}
