package com.OfficeManager.daos;

import com.OfficeManager.entities.Department;
import com.OfficeManager.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDepartmentDao extends JpaRepository<Department, Integer> {

    @Query("SELECT * "
            + "FROM DEPARTMENT "
    )
    public List<Department> fetchAll();
}
