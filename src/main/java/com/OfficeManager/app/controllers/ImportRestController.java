package com.OfficeManager.app.controllers;

import com.OfficeManager.app.dtos.MessageDto;
import com.OfficeManager.app.services.impl.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST})
public class ImportRestController {

    @Autowired
    ImportServiceImpl importService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/office", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> importOffice(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return new ResponseEntity<MessageDto>(importService.importBureau(file), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> importPerson(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "wipe") Boolean wipe) throws IOException {
        return new ResponseEntity<MessageDto>(importService.importAffectation(file, wipe), HttpStatus.OK);
    }
}
