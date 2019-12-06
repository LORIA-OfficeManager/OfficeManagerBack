package com.OfficeManager.daos;

import com.OfficeManager.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStatusDao extends JpaRepository<Status, Integer> {

    @Query("SELECT * "
            + "FROM STATUS "
    )
    public List<Status> fetchAll();
}
