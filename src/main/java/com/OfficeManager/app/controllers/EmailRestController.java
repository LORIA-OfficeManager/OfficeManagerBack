package com.OfficeManager.app.controllers;

import com.OfficeManager.app.dtos.MessageDto;
import com.OfficeManager.app.services.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins="*")
@Controller
@RequestMapping(value = "/reportError")
public class EmailRestController {

    @Autowired
    EmailServiceImpl emailService;

    @PostMapping
    public ResponseEntity<String> sendErrorEmail(@RequestBody MessageDto message){
        emailService.sendSimpleMessage(message.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
