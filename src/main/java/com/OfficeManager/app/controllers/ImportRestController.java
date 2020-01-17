package com.OfficeManager.app.controllers;

import com.OfficeManager.app.entities.*;
import com.OfficeManager.app.services.impl.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.*;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST})
public class ImportRestController {

    @Autowired
    ImportServiceImpl importService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/office", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> importOffice(@RequestParam(value = "file") MultipartFile file) throws IOException {
        //System.out.println(file.getOriginalFilename());
        return new ResponseEntity<String>(importService.importBureau(file), HttpStatus.OK);
    }

    @PostMapping("/person")
    public ResponseEntity<String> importPerson() throws IOException {
        return new ResponseEntity<String>(importService.importAffectation("Model bureaux Loria.xls"), HttpStatus.OK);
    }
}
