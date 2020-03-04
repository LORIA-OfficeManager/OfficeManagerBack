package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDepartmentDao extends JpaRepository<Department, Integer> {

    @Query(
            value = "SELECT * FROM department d WHERE d.name = :name",
            nativeQuery = true)
    Department findByName(String name);

    @Modifying
    @Query(
            value = "UPDATE Team t SET department = (SELECT d FROM Department d WHERE d.name = :default) WHERE t.department.id = :id"
    )
    void switcTeamToDefaultDepartment(@Param("id") int id, @Param("default") String defaultDep);

    @Query(
            value = "SELECT (count(d) > 0) FROM Department d WHERE d.name = :name"
    )
    boolean existsByName(String name);

}
