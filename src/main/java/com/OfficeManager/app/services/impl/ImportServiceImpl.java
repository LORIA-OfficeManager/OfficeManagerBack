package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.*;
import com.OfficeManager.app.dtos.MessageDto;
import com.OfficeManager.app.entities.*;
import com.OfficeManager.app.services.interfaces.IImportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final int LIGNE_START_BUREAU = 4;
    private static final int LIGNE_START_AFFECTATION = 2;

    private FileWriter log;
    private String fileName;

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

    @Autowired
    ITeamDao teamDao;


    private java.sql.Date convertExcelToDate(double date) {
        return new Date((long) ((date - nbJoursBetween1900_1970) * 24 * 60 * 60 * 1000));
    }

    @Override
    public MessageDto importBureau(MultipartFile loriatab) throws IOException {
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

        File file = new File(System.getProperty("java.io.tmpdir")+"/"+loriatab.getOriginalFilename());
        String extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
        file.deleteOnExit();
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

        for (ligneActuelle = LIGNE_START_BUREAU ; ligneActuelle <= sheet.getLastRowNum() ; ligneActuelle++) {
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

        MessageDto mes = new MessageDto();
        mes.setType("importBureau");
        mes.setMessage("Nb bureaux ajoutés : "+nbLigneAdd+", nb bureaux déjà dans la base de données : "+nbLigneDejaPresente);
        return mes;
    }

    @Override
    public MessageDto importAffectation(MultipartFile loriatab, boolean wipe) throws IOException {
        if(wipe){
            officeAssignmentDao.deleteAll();
            personDao.deleteAll();
        }

        File file = new File(System.getProperty("java.io.tmpdir")+"/"+loriatab.getOriginalFilename());
        file.deleteOnExit();
        loriatab.transferTo(file);

        return parsingImport(file, false);

    }

    @Scheduled(cron="0 0 2 * * ?")
    protected MessageDto importScheduled() throws IOException {
        officeAssignmentDao.deleteAll();
        personDao.deleteAll();

        File file = new File("ListeAffectation.xlsx");
        file.deleteOnExit();


        return parsingImport(file, false);
    }

    private MessageDto parsingImport(File file, boolean fullLog) throws IOException {
        int ligneActuelle;
        int nbPersonneDejaLa = 0;
        int nbPersonneAjoute = 0;
        int nbPersonneUpdate = 0;
        int nbAffectationAjoute = 0;
        String nom, prenom, email, statut, bureau, labo, departement;
        java.sql.Date debut, fin;
        List<String> emails = personDao.fetchAllEmail();
        Workbook wb;
        Sheet sheet;

        String extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
        FileInputStream fichier = new FileInputStream(file);

        try {
            String date = LocalDateTime.now().toString();
            date = date.substring(0,date.length()-4).replace(':','-');
            fileName = "log/"+date+"log.txt";
            log = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.write("Import affection commence ("+LocalDate.now()+") :\n");

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

        for (ligneActuelle = LIGNE_START_AFFECTATION; ligneActuelle < sheet.getLastRowNum(); ligneActuelle++) {//parcourir les lignes
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
            departement = ligne.getCell(DEP)==null?null:ligne.getCell(DEP).getStringCellValue();

            nom = nom.substring(0, 1).toUpperCase() + nom.substring(1);
            prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);

            Person person;
            String building, num;
            int floor;

            if (labo.toUpperCase().equals("LORIA")){
                //Ici la personne n'existe pas dans la BDD
                if (!emails.contains(email)) {

                    Status stat = statusDao.findByName(statut);
                    if (stat == null && statut != null){
                        log.write(ligneActuelle+1+" : "+prenom + " " + nom + " a été affecté au statut par défaut car son statut ne correspond à aucun dans la liste des status de la base de données.\n");
                    }
                    stat = stat == null ? statusDao.findById(0).get() : stat;

                    Optional<Team> team = teamDao.findByName(departement);
                    if (!team.isPresent() && departement != null){
                        log.write(ligneActuelle+1+" : "+prenom + " " + nom + " a été affecté à la team par défaut car sa team ne correspond à aucune dans la liste des status de la base de données.\n");
                    }
                    team = !team.isPresent() ? teamDao.findByName("Loria") : team;

                    person = new Person(prenom, nom, email, false, debut.toLocalDate(), fin.toLocalDate(), stat, team.get());
                    personDao.save(person);
                    emails.add(email);
                    nbPersonneAjoute++;
                    nbAffectationAjoute = ajoutAffectation(nbAffectationAjoute, bureau, person,ligneActuelle,fullLog);
                    //Ici la personne existe déjà dans la BDD, qu'elle est une affectation ou pas
                } else {
                    person = personDao.getByEmail(email);
                    nbPersonneDejaLa++;
                    OfficeAssignment assignment = getCurrentAssignment(person.getId());
                    //Si le mec avait déjà un bureau d'affecté actuellement
                    if(assignment != null){
                        nbPersonneUpdate = updateAffectation(nbPersonneUpdate, bureau, person, assignment,ligneActuelle);
                    } else {
                        nbAffectationAjoute = ajoutAffectation(nbAffectationAjoute, bureau, person, ligneActuelle,fullLog);
                    }
                }
            }
        }
        fichier.close();
        log.close();
        MessageDto mes = new MessageDto();
        mes.setType("importAffectation");
        StringBuilder message = new StringBuilder();
        message.append("Nb ligne ajoutés : "+nbPersonneAjoute+", nb lignes déjà là : "+nbPersonneDejaLa+", nb lignes update : "+ nbPersonneUpdate +", nb affectation ajoutés : "+nbAffectationAjoute+"\n");
        BufferedReader  bufferedReader = new BufferedReader(new FileReader(fileName));
        String ligne;
        while ((ligne = bufferedReader.readLine()) != null){
            message.append(ligne+"\n");
        }
        mes.setMessage(message.toString());
        return mes;
    }

    private int ajoutAffectation(int nbAffectationAjoute, String bureau, Person person, int ligneCourante, boolean fulllog) throws IOException {
        String building;
        int floor;
        String num;//Si il a un bureau correct, on l'affecte
        if(bureau != null && bureauCorrect(bureau.toUpperCase())){
            building = bureau.charAt(0)+"";
            floor = Integer.parseInt(bureau.charAt(1)+"");
            num = bureau.substring(2);
            Office o = officeDao.getByName(num, floor, building);
            if (o != null) {
                officeAssignmentDao.save(new OfficeAssignment(LocalDate.now(), LocalDate.parse("2099-12-31"), person, o));
                if (fulllog){
                    log.write(ligneCourante+1+" : "+person.getFirstName() + " " + person.getLastName() + " à été affecté avec succès.\n");
                }
                nbAffectationAjoute++;
            } else {
                log.write(ligneCourante+1+" : "+person.getFirstName() + " " + person.getLastName() + " n'a pas été affecté à un bureau car le bureau n'est pas enregistré dans la base de données.\n");
            }
        } else if (bureau != null && !bureauCorrect(bureau.toUpperCase())){
            log.write(ligneCourante+1+" : "+person.getFirstName() + " " + person.getLastName() + " n'a pas été affecté à un bureau car le bureau ne respecte pas le schema suivant A000.\n");
        }
        return nbAffectationAjoute;
    }

    private int updateAffectation(int nbPersonneUpdate, String bureau, Person person, OfficeAssignment assignment, int ligneCourante) {
        String building;
        int floor;
        String num;
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
                officeAssignmentDao.save(new OfficeAssignment(LocalDate.now(), LocalDate.parse("2099-12-31"), person, officeDao.getByName(num, floor, building)));
                nbPersonneUpdate++;
            }
            //si son bureau n'est plus sur dans le excel cela veut dire qu'il a juste été desafecté
        } else if (bureau == null){
            assignment.setEndDate(LocalDate.now());
            officeAssignmentDao.save(assignment);
            nbPersonneUpdate++;
        }
        return nbPersonneUpdate;
    }

    private boolean bureauCorrect(String bureau) {
        return bureau.matches("^[ABC][0-9][0-9][0-9]");
    }

    private OfficeAssignment getCurrentAssignment(int id){
        for (OfficeAssignment assignment : personDao.getOne(id).getAssignments()){
            if (assignment.getStartDate().toEpochDay() <= LocalDate.now().toEpochDay() && assignment.getEndDate().toEpochDay() >= LocalDate.now().toEpochDay()){
                return assignment;
            }
        }
        return null;
    }
}
