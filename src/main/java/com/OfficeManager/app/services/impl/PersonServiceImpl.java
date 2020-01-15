package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IPersonDao;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("personService")
@Transactional
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDao personDao;

    @Override
    public List<Person> fetchAll() {
        return personDao.findAll();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return personDao.findById(id);
    }

    @Override
    public Person savePerson(Person person) {
        return personDao.save(person);
    }

    @Override
    public List<String> fetchAllEmail() {
        return personDao.fetchAllEmail();
    }
}
