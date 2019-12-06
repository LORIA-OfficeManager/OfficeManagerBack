package com.OfficeManager.daos;

import com.OfficeManager.entities.OfficeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOfficeAssignmentDao extends JpaRepository<OfficeAssignment, Integer> {

    @Query("SELECT * "
            + "FROM OFFICE_ASSIGNMENT "
    )
    public List<OfficeAssignment> fetchAll();
}
