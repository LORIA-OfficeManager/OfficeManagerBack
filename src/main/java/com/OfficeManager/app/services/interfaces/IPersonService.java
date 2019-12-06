package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Person;

import java.util.List;

public interface IPersonService {

    public List<Person> fetchAll();

    public Person savePerson(Person person);
}
