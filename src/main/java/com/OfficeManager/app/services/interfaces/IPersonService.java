package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IPersonService {

    public List<Person> fetchAll();

    public Optional<Person> findById(Integer id);

    public Person savePerson(Person person);

    List<String> fetchAllEmail();
}
