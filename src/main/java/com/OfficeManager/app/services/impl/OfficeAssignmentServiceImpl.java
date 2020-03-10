package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IOfficeAssignmentDao;
import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.entities.OfficeAssignment;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.interfaces.IOfficeAssignmentService;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@Service("officeAssignmentService")
@Transactional
public class OfficeAssignmentServiceImpl implements IOfficeAssignmentService {

    @Autowired
    private IOfficeAssignmentDao officeAssignmentDao;

    @Override
    public List<OfficeAssignment> fetchAll() {
        return officeAssignmentDao.findAll();
    }

    @Override
    public OfficeAssignment saveOfficeAssignement(OfficeAssignment officeAssignment) {
        return officeAssignmentDao.save(officeAssignment);
    }

    @Override
    public List<OfficeAssignment> findByOfficeID(int id, boolean filterCurDate) {
        return filterCurDate ? officeAssignmentDao.findByOfficeIdFilterCurDate(id) : officeAssignmentDao.findByOfficeId(id);
    }

    @Override
    public void closeLastsOfficeAssignmentByPersonID(int id) {
        officeAssignmentDao.closeOfficeAssignmentByPersonID(id);
    }

    @Override
    public List<Integer> findAllStatusByOfficeId(int id) {
        return officeAssignmentDao.findAllStatusByOfficeId(id);
    }

    @Override
    public Boolean hasStrangerByOfficeId(int id) {
        return officeAssignmentDao.nbStrangerByOfficeId(id)!=0;
    }

    @Override
    public void exportOfficeAssignment(){

        //Une feuille de calcul commence à 0, comme un tableau normal

        //Récupération de toutes les données de OfficeAssignment
        List<OfficeAssignment> data = this.fetchAll();

        //Création de la feuille de calcul
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("result");

        //Création style titre
        XSSFCellStyle styleTitre = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setFontName("Arial");

        XSSFCellStyle styleCell = wb.createCellStyle();
        XSSFFont font2 = wb.createFont();
        font2.setBold(true);
        styleCell.setFont(font2);

        styleTitre.setFont(font);

        //Création de la première ligne du tableau (titre)
        XSSFRow row = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Personnel Equipe");
        cell.setCellStyle(styleTitre);
        cell = row.createCell(1);
        cell.setCellValue(new Date().toString());
        cell.setCellStyle(styleTitre);

        //Création de la deuxième ligne : légendes
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("Nom");
        cell.setCellStyle(styleCell);
        cell = row.createCell(1);
        cell.setCellValue("Prénom");
        cell = row.createCell(2);
        cell.setCellValue("Adresse mail");
        cell = row.createCell(3);
        cell.setCellValue("Début dossier");
        cell = row.createCell(4);
        cell.setCellValue("Fin dossier");
        cell = row.createCell(5);
        cell.setCellValue("Satut niveau 3");
        cell = row.createCell(6);
        cell.setCellValue("Bureau");
        cell = row.createCell(7);
        cell.setCellValue("Laboratoire");
        cell = row.createCell(8);
        cell.setCellValue("Structure");

        //Remplissage de la feuille de calcul
        Person p;
        Office o;
        int i = 2;

        for(OfficeAssignment office : data) {
            p = office.getPerson();
            o = office.getOffice();
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(p.getLastName());
            cell = row.createCell(1);
            cell.setCellValue(p.getFirstName());
            cell = row.createCell(2);
            cell.setCellValue(p.getEmail());
            cell = row.createCell(3);
            cell.setCellValue(p.getStartDateContract().toString());
            cell = row.createCell(4);
            cell.setCellValue(p.getEndDateContract().toString());
            cell = row.createCell(5);
            cell.setCellValue(p.getStatus().getName());
            cell = row.createCell(6);
            cell.setCellValue(o.getNum());
            cell = row.createCell(7);
            cell.setCellValue(p.getDepartment().getName());
            cell = row.createCell(8);
            cell.setCellValue(p.getTeam().getName());

            i++;
        }

        try{
            String systemHome = System.getProperty("user.home");
            File file = new File(systemHome + "/Downloads/OfficeAssignments.xlsx");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        }
        catch(Exception e){
            System.out.println("Impossible d'exporter le fichier");
            e.printStackTrace();
        }

    }
}
