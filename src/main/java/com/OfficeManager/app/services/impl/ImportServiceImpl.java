package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IDepartmentDao;
import com.OfficeManager.app.daos.IOfficeDao;
import com.OfficeManager.app.daos.IPersonDao;
import com.OfficeManager.app.daos.IStatusDao;
import com.OfficeManager.app.entities.Office;
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

    @Autowired
    IOfficeDao officeDao;

    @Autowired
    IPersonDao personDao;

    @Autowired
    IDepartmentDao departmentDao;

    @Autowired
    IStatusDao statusDao;


    private java.sql.Date convertExcelToDate(double date) {
        return new Date((long) ((date - nbJoursBetween1900_1970) * 24 * 60 * 60 * 1000));
    }

    @Override
    public String importBureau( MultipartFile loriatab) throws IOException {
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
                        officeDao.save(new Office(Math.floor(Math.random()*5+1), etage, numero, batiment, ""));
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

    @Override
    public String importAffectation(String path) throws IOException {
        return null;
    }

}
