package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IPersonDao;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("personService")
@Transactional
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDao personDao;

    @Override
    public List<Person> fetchAll() {
        return personDao.findAll();
    }
}
