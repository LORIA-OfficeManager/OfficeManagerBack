package com.OfficeManager.services.interfaces;

import com.OfficeManager.entities.Person;

import java.util.List;

public interface IPersonService {

    public List<Person> fetchAll();
}
