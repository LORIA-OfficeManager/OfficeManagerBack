package fr.loria.office;

import java.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App {
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


    public static void main(String[] args) throws IOException, InvalidFormatException {
        //importAffectation();
        importBureau();
    }

    static java.sql.Date convertExcelToDate(double date) {
        return new Date((long) ((date - nbJoursBetween1900_1970) * 24 * 60 * 60 * 1000));
    }

    static void ecrire() {
        //1. Créer un Document vide
        XSSFWorkbook wb = new XSSFWorkbook();
        //2. Créer une Feuille de calcul vide
        Sheet feuille = wb.createSheet("new sheet");
        //3. Créer une ligne et mettre qlq chose dedans
        Row row = feuille.createRow((short) 0);
        //4. Créer une Nouvelle cellule
        Cell cell = row.createCell(0);
        //5. Donner la valeur
        cell.setCellValue(1.2);

        //Ajouter d'autre cellule avec différents type
        /*int*/
        row.createCell(1).setCellValue(3);
        /*char*/
        row.createCell(2).setCellValue('c');
        /*String*/
        row.createCell(3).setCellValue("chaine");
        /*boolean*/
        row.createCell(4).setCellValue(false);

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("nouveauFichier.xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void importBureau() throws IOException {
        File repertoireCourant = new File(".").getCanonicalFile();
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
        switch (dialogue.showOpenDialog(null)) {
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Import annulé");
                break;
            case JFileChooser.APPROVE_OPTION:
                StringBuilder sb = new StringBuilder();
                String batiment, bureau;
                int etage, numero, ligneActuelle = 0;
                double capacite;

                FileInputStream fichier = new FileInputStream(new File(String.valueOf(dialogue.getSelectedFile())));
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
                            numero = Integer.parseInt(bureau.substring(2));
                            sb.append("INSERT INTO MYTABLE ("+4+", "+etage+", "+numero+", "+batiment+", 'Ceci est une description');\n");
                        }
                    }
                }
                System.out.println(sb.toString());
                break;
        }
    }

    static void importAffectation() throws IOException {
        File repertoireCourant = new File(".").getCanonicalFile();
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
        switch (dialogue.showOpenDialog(null)) {
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Import annulé");
                break;
            case JFileChooser.APPROVE_OPTION:
                StringBuilder sb = new StringBuilder();
                int ligneActuelle = 0;
                int lignePasse = 0;
                String nom, prenom, email, statut, bureau, labo, departement;
                java.sql.Date debut, fin;

                FileInputStream fichier = new FileInputStream(new File(String.valueOf(dialogue.getSelectedFile())));
                //créer une instance workbook qui fait référence au fichier xlsx
                XSSFWorkbook wb = new XSSFWorkbook(fichier);
                XSSFSheet sheet = wb.getSheetAt(0);

                FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

                System.out.println("Derniere ligne : "+sheet.getLastRowNum());
                for (ligneActuelle = 2; ligneActuelle < sheet.getLastRowNum(); ligneActuelle++) {//parcourir les lignes
                    Row ligne = sheet.getRow(ligneActuelle);
                    //Si le bureau n'est pas renseigné, on passe la ligne
                    if (!ligne.getCell(OFFICE).getStringCellValue().isEmpty()) {
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

                        sb.append("INSERT INTO MYTABLE ("+nom+", "+prenom+", "+email+", "+debut+", "+fin+", "+statut+", "+bureau+", "+labo+", "+departement+");\n");
                    } else {
                        lignePasse++;
                    }
                /*for (Cell cell : ligne) {//parcourir les colonnes
                    System.out.print("|| Numero de colonne : "+cell.getColumnIndex()+"||");
                    //évaluer le type de la cellule
                    switch (cell.getColumnIndex())
                    {
                        case 0:
                            System.out.print("Je gère le nom");
                            nom = cell.getStringCellValue().toLowerCase();
                            break;
                        case 1:
                            System.out.print("Je gère le prenom");
                            prenom = cell.getStringCellValue().toLowerCase();
                            break;
                        case 2:
                            System.out.print("Je gère le mail");
                            if (cell.getStringCellValue().isEmpty()) {
                                System.out.print("Création d'un mail : "+prenom +"."+nom+"@loria.fr");
                            }else {
                                System.out.print("Voici un mail : "+cell.getStringCellValue() + "\t");
                            }
                            break;
                        case 3:
                            System.out.print("Je gère le debut");
                            break;
                        case 4:
                            System.out.print("Je gère la fin");
                            break;
                        case 5:
                            System.out.print("Je gère le statut");
                            break;
                        case 6:
                            System.out.print("Je gère le bureau");
                            break;
                        case 7:
                            System.out.print("Je passe cette colonne");
                            break;
                        case 8:
                            System.out.print("Je gère le labo");
                            break;
                        case 9:
                            System.out.print("Je gère la structure");
                            break;
                        default:
                            System.out.print("TROP LOIN");
                            break;
                    }*/
                }
                System.out.println("Nombre de ligne passé (non inséré) : "+lignePasse);
                System.out.println(sb.toString());
                /*switch (formulaEvaluator.evaluateInCell(cell).getCellType())
                {
                    case NUMERIC:
                        System.out.print(convertExcelToDate(cell.getNumericCellValue()).toString() + "\t\t");
                        break;
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    default:
                        break;
                }*/
                break;
        }
    }
}
