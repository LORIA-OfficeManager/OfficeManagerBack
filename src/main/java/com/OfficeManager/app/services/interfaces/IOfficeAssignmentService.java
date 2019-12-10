package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.OfficeAssignment;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IOfficeAssignmentService {

    public List<OfficeAssignment> fetchAll();

    public OfficeAssignment saveOfficeAssignement(OfficeAssignment officeAssignment);

    public List<OfficeAssignment> findByOfficeID(int id);

    public Double findOccupationByOfficeId(int id);

    public Boolean hasStrangerByOfficeId(int id);
}
