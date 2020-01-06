package com.OfficeManager.app.controllers;

import com.OfficeManager.app.dtos.SingleOfficeDto;
import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.entities.OfficeAssignment;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.impl.OfficeAssignmentServiceImpl;
import com.OfficeManager.app.services.impl.OfficeServiceImpl;
import com.OfficeManager.app.services.impl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping(value = "/assignement", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssignmentRestController {

    @Autowired
    OfficeServiceImpl officeService;

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    OfficeAssignmentServiceImpl officeAssignmentService;

    @PostMapping("{idO}/{idP}")
    ResponseEntity<String> getOffice(@PathVariable Integer idO, @PathVariable Integer idP) {
        Optional<Office> office = officeService.findById(idO);
        if (!office.isPresent())
            return new ResponseEntity<String>("Office "+ idO.toString() +" doesn't exist", HttpStatus.BAD_REQUEST);

        Optional<Person> person = personService.findById(idP);
        if (!person.isPresent())
            return new ResponseEntity<String>("Person "+ idO.toString() +" doesn't exist", HttpStatus.BAD_REQUEST);
        officeAssignmentService.closeLastsOfficeAssignmentByPersonID(idP);
        officeAssignmentService.saveOfficeAssignement(new OfficeAssignment(LocalDate.now().plusDays(1), LocalDate.of(2099,12,31), person.get(), office.get()));


        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
