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
            value = "SELECT * FROM office_assignment o WHERE o.office_id = :id AND (o.start_date <= NOW() AND o.end_date >= NOW())",
            nativeQuery = true)
    public List<OfficeAssignment> findByOfficeIdFilterCurDate(@Param("id") int id);

    @Query(
            value = "SELECT p.status_status_id FROM office_assignment o NATURAL JOIN person p JOIN status s where s.status_id = p.status_status_id AND o.office_id = :id AND (o.start_date <= NOW() AND o.end_date >= NOW())",
            nativeQuery = true)
    public List<Integer> findAllStatusByOfficeId(@Param("id") int id);

    @Query(
            value = "SELECT COUNT(*) FROM office_assignment o NATURAL JOIN person p WHERE o.office_id = :id AND (p.end_date_contract < NOW() OR p.start_date_contract > NOW()) AND (o.start_date <= NOW() AND o.end_date >= NOW())",
            nativeQuery = true)
    public Integer nbStrangerByOfficeId(@Param("id") int id);
}
