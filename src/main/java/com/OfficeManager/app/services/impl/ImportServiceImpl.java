package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.*;
import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.entities.OfficeAssignment;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.interfaces.IImportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("importService")
@Transactional
public class ImportServiceImpl implements IImportService {

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

    private String log;

    @Autowired
    IOfficeDao officeDao;

    @Autowired
    IPersonDao personDao;

    @Autowired
    IDepartmentDao departmentDao;

    @Autowired
    IStatusDao statusDao;

    @Autowired
    IOfficeAssignmentDao officeAssignmentDao;


    private java.sql.Date convertExcelToDate(double date) {
        return new Date((long) ((date - nbJoursBetween1900_1970) * 24 * 60 * 60 * 1000));
    }

    @Override
    public String importBureau(MultipartFile loriatab) throws IOException {
        String path = "tata.xlsx";
        String batiment, bureau;
        int etage, ligneActuelle = 0, nbLigneDejaPresente = 0, nbLigneAdd = 0;
        double capacite;
        String numero;
        //pour chaque office on a [0]=batiment [1]=floor [2]=num
        List<String[]> offices = officeDao.fetchAllFullName();
        List<String> officesName = new ArrayList<String>();
        for (String[] o : offices) {
            officesName.add(o[0]+o[1]+o[2]);
        }

        String[] tab = path.split("\\.");
        String extension = tab.length >= 2 ? tab[tab.length-1] : "fichier sans extension";
        File file = new File(System.getProperty("java.io.tmpdir")+"/"+loriatab.getOriginalFilename());
        loriatab.transferTo(file);
        FileInputStream fichier = new FileInputStream(file);

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

        for (ligneActuelle = 4 ; ligneActuelle <= sheet.getLastRowNum() ; ligneActuelle++) {
            Row ligne = sheet.getRow(ligneActuelle);
            for (Cell cell : ligne) {
                if (!cell.getStringCellValue().isEmpty()) {
                    bureau = cell.getStringCellValue().trim();
                    batiment = bureau.substring(0, 1);
                    etage = Integer.parseInt(bureau.substring(1, 2));
                    numero = bureau.substring(2);

                    if (!officesName.contains(batiment+etage+numero)) {
                        officeDao.save(new Office(2.0, etage, numero, batiment, ""));
                        officesName.add(batiment+etage+numero);
                        nbLigneAdd++;
                    } else {
                        nbLigneDejaPresente++;
                    }
                }
            }
        }
        fichier.close();
        log = "Nb ligne ajoutés : "+nbLigneAdd+", nb lignes déjà là : "+nbLigneDejaPresente;
        //return "Nb ligne ajoutés : "+nbLigneAdd+", nb lignes déjà là : "+nbLigneDejaPresente;
        //return log;
        return "{\"type\":\""+"importBureau\",\n"+"\"text\":\""+"Nb ligne ajoutés : "+nbLigneAdd+", nb lignes déjà là : "+nbLigneDejaPresente+"\"}";

    }

    @Override
    public String importAffectation(MultipartFile loriatab) throws IOException {
        String path = "tata2.xlsx";
        int ligneActuelle = 0;
        int nbPersonneDejaLa = 0;
        int nbPersonneAjoute = 0;
        int nbPersonneUpdate = 0;
        int nbAffectationAjoute = 0;

        String nom, prenom, email, statut, bureau, labo, departement;
        java.sql.Date debut, fin;
        List<String> emails = personDao.fetchAllEmail();

        String[] tab = path.split("\\.");
        String extension = tab.length >= 2 ? tab[tab.length - 1] : "fichier sans extension";
        File file = new File(System.getProperty("java.io.tmpdir")+"/"+loriatab.getOriginalFilename());
        loriatab.transferTo(file);
        FileInputStream fichier = new FileInputStream(file);

        //créer une instance workbook qui fait référence au fichier xlsx
        Workbook wb;
        Sheet sheet;
        switch (extension) {
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

            Person person;
            String building, num;
            int floor;

            if (!emails.contains(email)) {
                person = new Person(prenom, nom, email, false, debut.toLocalDate(), fin.toLocalDate(), statusDao.findById(0).get(), null, departmentDao.findById(0).get());
                personDao.save(person);
                emails.add(email);
                nbPersonneAjoute++;
                if(bureau != null && bureauCorrect(bureau.toUpperCase())){
                    building = bureau.charAt(0)+"";
                    floor = Integer.parseInt(bureau.charAt(1)+"");
                    num = bureau.substring(2);
                    officeAssignmentDao.save(new OfficeAssignment(LocalDate.now(), person.getEndDateContract(), person, officeDao.getByName(num, floor, building)));
                    nbAffectationAjoute++;
                }
            } else {
                person = personDao.getByEmail(email);
                nbPersonneDejaLa++;
                OfficeAssignment assignment = person.getCurrentAssignment();
                //Si le mec avait déjà un bureau d'affecté actuellement
                if(assignment != null){
                    Office office = assignment.getOffice();
                    //Si le bureau du excel est bien formé si il existe
                    if(bureau != null && bureauCorrect(bureau.toUpperCase())) {
                        building = bureau.charAt(0) + "";
                        floor = Integer.parseInt(bureau.charAt(1) + "");
                        num = bureau.substring(2);
                        //Si le bureau est différent de celui courrament affecté à la personne courante
                        if (!building.equals(office.getBuilding()) || !num.equals(office.getNum()) || floor != office.getFloor()){
                            assignment.setEndDate(LocalDate.now());
                            officeAssignmentDao.save(assignment);
                            officeAssignmentDao.save(new OfficeAssignment(LocalDate.now(), person.getEndDateContract(), person, officeDao.getByName(num, floor, building)));
                            nbPersonneUpdate++;
                        }
                    }
                }
            }
        }
        fichier.close();
        return "{\"type\":\""+"importAffectation\",\n"+"\"text\":\""+"Nb ligne ajoutés : "+nbPersonneAjoute+", nb lignes déjà là : "+nbPersonneDejaLa+", nb lignes update : "+ nbPersonneUpdate +"\"}";
    }

    private boolean bureauCorrect(String bureau) {
        return bureau.matches("^[ABC][0-9][0-9][0-9]");
    }
}
