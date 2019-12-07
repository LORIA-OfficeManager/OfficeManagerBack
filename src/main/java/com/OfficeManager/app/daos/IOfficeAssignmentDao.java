package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.OfficeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOfficeAssignmentDao extends JpaRepository<OfficeAssignment, Integer> {
    @Query(
            value = "SELECT * FROM office_assignment o WHERE o.office_id = :id",
            nativeQuery = true)
    public List<OfficeAssignment> findByOfficeId(@Param("id") int id);

    @Query(
            value = "SELECT COUNT(*) FROM office_assignment o WHERE o.office_id = :id",
            nativeQuery = true)
    public Integer findOccupationByOfficeId(@Param("id") int id);

    @Query(
            value = "SELECT COUNT(*) FROM office_assignment o WHERE o.office_id = :id AND o.end_date < CURDATE()",
            nativeQuery = true)
    public Integer nbStrangerByOfficeId(@Param("id") int id);
}
