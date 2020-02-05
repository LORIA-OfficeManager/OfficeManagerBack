package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDepartmentDao extends JpaRepository<Department, Integer> {

    @Query(
            value = "SELECT * FROM department d WHERE d.name = :name",
            nativeQuery = true)
    Department findByName(String name);

}
