package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPersonDao extends JpaRepository<Person, Integer> {

    @Query(
            value = "SELECT email FROM person",
            nativeQuery = true)
    List<String> fetchAllEmail();

    @Query(
            value = "SELECT * FROM person p WHERE p.email = :email",
            nativeQuery = true)
    Person getByEmail(@Param("email") String email);
}
