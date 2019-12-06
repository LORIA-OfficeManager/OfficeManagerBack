package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOfficeDao extends JpaRepository<Office, Integer> {

//    @Query("SELECT * "
//            + "FROM OFFICE "
//            + "WHERE OFFICE_ID = :id"
//    )
//    public Office findByOfficeId(@Param("id") Integer office_id);
//
//    @Query("SELECT * "
//            + "FROM OFFICE "
//            + "WHERE NUM = :num AND FLOOR = :floor AND BUILDING = :building"
//    )
//    public Office findByFullName(@Param("num") Integer num, @Param("floor") Integer floor, @Param("building") String building);
//
//    @Query("SELECT * "
//            + "FROM OFFICE "
//    )
//    public List<Office> fetchAll();

}
