package main;

import dao.UtenteDao;
import factory.FileFactory;
import model.FileParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main {

    public static void main(String[] args) {
        try {
            String percorsoFileCSV = "C:\\Sviluppo\\FileParserDoc\\fileTextRead.csv";
            String percorsoFileEXCEL = "C:\\Sviluppo\\FileParserDoc\\fileExcelRead.xlsx";
            FileParser fileParser = FileFactory.fileFactory(percorsoFileEXCEL);
            fileParser.readData();
            new UtenteDao().saveJson(fileParser.getLista(), "C:\\Sviluppo\\FileParserDoc\\JsonUscitaDatiFromEXCEL.csv");
            //new UtenteDao().saveUser(fileParser.getLista());
             /*CsvParser csvParser = new CsvParser("D:\\FileParser\\fileTextRead.csv");
            csvParser.readData2();*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
