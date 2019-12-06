package fr.loria.office;

import java.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final int nbJoursBetween1900_1970 = 25569;

    public static void main( String[] args ) throws IOException, InvalidFormatException {
        lire();
    }

    static Date convertExcelToDate(double date){
        return new Date((long)((date-nbJoursBetween1900_1970)*24*60*60*1000));
    }

    static void ecrire(){
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

    static void lire() throws IOException {
        File repertoireCourant = new File(".").getCanonicalFile();
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
        switch (dialogue.showOpenDialog(null)){
            case JFileChooser.CANCEL_OPTION:

                break;
            case JFileChooser.APPROVE_OPTION:
                FileInputStream fichier = new FileInputStream(new File(String.valueOf(dialogue.getSelectedFile())));
                //créer une instance workbook qui fait référence au fichier xlsx
                XSSFWorkbook wb = new XSSFWorkbook(fichier);
                XSSFSheet sheet = wb.getSheetAt(0);

                FormulaEvaluator formulaEvaluator =
                        wb.getCreationHelper().createFormulaEvaluator();

                for (Row ligne : sheet) {//parcourir les lignes
                    for (Cell cell : ligne) {//parcourir les colonnes
                        //évaluer le type de la cellule
                        switch (formulaEvaluator.evaluateInCell(cell).getCellType())
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
                        }
                    }
                    System.out.println();
                }

                break;
        }
    }
}
