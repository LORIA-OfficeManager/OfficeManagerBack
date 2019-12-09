package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDepartmentDao extends JpaRepository<Department, Integer> {

}
