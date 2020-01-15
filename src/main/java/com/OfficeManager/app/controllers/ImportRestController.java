package com.OfficeManager.app.controllers;

import com.OfficeManager.app.entities.*;
import com.OfficeManager.app.services.impl.DepartmentServiceImpl;
import com.OfficeManager.app.services.impl.OfficeServiceImpl;
import com.OfficeManager.app.services.impl.PersonServiceImpl;
import com.OfficeManager.app.services.impl.StatusServiceImpl;
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

import javax.swing.*;
import java.io.*;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImportRestController {

    private static final int NAME = 0;
    private static final int FIRSTNAME = 1;
    private static final int MAIL = 2;
    private static final int START = 3;
    private static final int END = 4;
    private static final int RANK = 5;
    private static final int OFFICE = 6;
    private static final int LAB = 8;
    private static final int DEP = 9;
    private static final int nbJoursBetween1900_1970 = 25569;

    @Autowired
    OfficeServiceImpl officeService;

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    StatusServiceImpl statusService;


    @PostMapping("/office")
    public ResponseEntity<String> importOffice() throws IOException {
        return new ResponseEntity<String>(importBureau("Liste_bureaux.xlsx"), HttpStatus.OK);
    }

    @PostMapping("/person")
    public ResponseEntity<String> importPerson() throws IOException {
        return new ResponseEntity<String>(importAffectation("Model bureaux Loria.xls"), HttpStatus.OK);
    }

    private java.sql.Date convertExcelToDate(double date) {
        return new Date((long) ((date - nbJoursBetween1900_1970) * 24 * 60 * 60 * 1000));
    }

    private String importBureau(String path) throws IOException {
        String batiment, bureau;
        int etage, ligneActuelle = 0, nbLigneDejaPresente = 0, nbLigneAdd = 0;
        double capacite;
        String numero;
        //pour chaque office on a [0]=batiment [1]=floor [2]=num
        List<String[]> offices = officeService.fetchAllFullName();
        List<String> officesName = new ArrayList<String>();
        for (String[] o : offices) {
            officesName.add(o[0]+o[1]+o[2]);
        }

        FileInputStream fichier = new FileInputStream(new File(path));
        //créer une instance workbook qui fait référence au fichier xlsx
        XSSFWorkbook wb = new XSSFWorkbook(fichier);
        XSSFSheet sheet = wb.getSheetAt(0);

        for (ligneActuelle = 4 ; ligneActuelle <= sheet.getLastRowNum() ; ligneActuelle++) {
            Row ligne = sheet.getRow(ligneActuelle);
            for (Cell cell : ligne) {
                if (!cell.getStringCellValue().isEmpty()) {
                    bureau = cell.getStringCellValue().trim();
                    batiment = bureau.substring(0, 1);
                    etage = Integer.parseInt(bureau.substring(1, 2));
                    numero = bureau.substring(2);

                    if (!officesName.contains(batiment+etage+numero)) {
                        officeService.saveOffice(new Office(Math.floor(Math.random()*5+1), etage, numero, batiment, ""));
                        officesName.add(batiment+etage+numero);
                        nbLigneAdd++;
                    } else {
                        nbLigneDejaPresente++;
                    }
                }
            }
        }
        fichier.close();
        return "Nb ligne ajoutés : "+nbLigneAdd+", nb lignes déjà là : "+nbLigneDejaPresente;
    }

    private String importAffectation(String path) throws IOException {
        int ligneActuelle = 0;
        int lignePasse = 0;
        int nbLigneDejaLa = 0;
        int nbLigneAjoute = 0;
        String nom, prenom, email, statut, bureau, labo, departement;
        java.sql.Date debut, fin;
        List<String> emails = personService.fetchAllEmail();

        String[] tab = path.split("\\.");
        String extension = tab.length >= 2 ? tab[tab.length-1] : "fichier sans extension";

        FileInputStream fichier = new FileInputStream(new File(path));

        //créer une instance workbook qui fait référence au fichier xlsx
        Workbook wb;
        Sheet sheet;
        switch(extension){
            case "xlsx":
                wb = new XSSFWorkbook(fichier);
                break;
            case "xls":
                wb = new HSSFWorkbook(fichier);
                break;
            default:
                System.err.println("Fichier incompatible");
                wb = null;
                System.exit(1);
                break;
        }
        sheet = wb.getSheetAt(0);

        //FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

        for (ligneActuelle = 2; ligneActuelle <= sheet.getLastRowNum(); ligneActuelle++) {//parcourir les lignes
            Row ligne = sheet.getRow(ligneActuelle);
            //Si le bureau n'est pas renseigné, on passe la ligne
            if (!ligne.getCell(OFFICE).getStringCellValue().isEmpty() || true) {
                bureau = ligne.getCell(OFFICE).getStringCellValue();
                nom = ligne.getCell(NAME).getStringCellValue().toLowerCase();
                prenom = ligne.getCell(FIRSTNAME).getStringCellValue().toLowerCase();
                //Si le mail n'est pas renseigné, on en génère une de type prenom.nom@loria.fr
                if (ligne.getCell(MAIL).getStringCellValue().isEmpty()) {
                    email = prenom + "." + nom + "@loria.fr";
                } else {
                    email = ligne.getCell(MAIL).getStringCellValue();
                }
                debut = convertExcelToDate(ligne.getCell(START).getNumericCellValue());
                fin = convertExcelToDate(ligne.getCell(END).getNumericCellValue());
                statut = ligne.getCell(RANK).getStringCellValue();
                labo = ligne.getCell(LAB).getStringCellValue();
                departement = ligne.getCell(DEP).getStringCellValue();

                if (!emails.contains(email)) {
                    personService.savePerson(new Person(prenom, nom, email, false, debut.toLocalDate(), fin.toLocalDate(), statusService.findById(0).get(), null, departmentService.findById(0).get()));
                    emails.add(email);
                    nbLigneAjoute++;
                } else {
                    nbLigneDejaLa++;
                }

            } else {
                lignePasse++;
            }
        }
        fichier.close();
        return "Nombre de ligne ajoutés : " + nbLigneAjoute+", ligne déjà présente : "+nbLigneDejaLa;
    }

}
