package com.OfficeManager.app.controllers;

import com.OfficeManager.app.dtos.PersonDto;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.impl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
public class PeopleRestController {

    @Autowired
    PersonServiceImpl personService;

    @GetMapping("")
    public ResponseEntity<List<PersonDto>> getPeople () {
        ArrayList<PersonDto> list = new ArrayList<PersonDto>();
        for (Person p: personService.fetchAll()) {
            list.add(mapPersonToPersonDto(p));
        }
        return new ResponseEntity<List<PersonDto>>(list, HttpStatus.OK);
    }

    public PersonDto mapPersonToPersonDto(Person person){
        return new PersonDto(person);
    }
}
