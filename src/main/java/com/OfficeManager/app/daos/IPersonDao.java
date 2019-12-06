package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPersonDao extends JpaRepository<Person, Integer> {

//    @Query("SELECT * "
//            + "FROM PERSON "
//    )
//    public List<Person> fetchAll();
}
