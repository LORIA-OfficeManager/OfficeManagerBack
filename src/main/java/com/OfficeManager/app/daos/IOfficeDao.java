package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOfficeDao extends JpaRepository<Office, Integer> {

    @Query(
            value = "SELECT building, floor, num FROM office",
            nativeQuery = true)
    List<String[]> fetchAllFullName();

    @Query(
            value = "SELECT * FROM office o WHERE o.num = :num AND o.floor = :floor AND o.building = :building",
            nativeQuery = true)
    Office getByName(@Param("num") String num, @Param("floor") int floor, @Param("building") String building);
}
