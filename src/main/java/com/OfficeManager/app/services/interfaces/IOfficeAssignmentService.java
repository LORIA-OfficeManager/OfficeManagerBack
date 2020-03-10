package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.OfficeAssignment;

import java.util.List;

public interface IOfficeAssignmentService {

    public List<OfficeAssignment> fetchAll();

    public OfficeAssignment saveOfficeAssignement(OfficeAssignment officeAssignment);

    public List<OfficeAssignment> findByOfficeID(int id, boolean filterCurDate);

    public void closeLastsOfficeAssignmentByPersonID(int id);

    public List<Integer> findAllStatusByOfficeId(int id);

    public Boolean hasStrangerByOfficeId(int id);

    public void exportOfficeAssignment();
}
