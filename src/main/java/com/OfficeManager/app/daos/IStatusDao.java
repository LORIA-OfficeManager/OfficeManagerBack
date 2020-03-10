package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IStatusDao extends JpaRepository<Status, Integer> {

    @Query(
            value = "SELECT * FROM status s WHERE s.name = :name",
            nativeQuery = true)
    Status findByName(String name);
}
