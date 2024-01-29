package main;

import dao.UtenteDao;
import factory.FileFactory;
import model.FileParser;

public class Main {

    public static void main(String[] args) {
        try {
            String percorsoFileCSV = "D:\\FileParser\\fileTextRead.csv";
            String percorsoFileEXCEL = "D:\\FileParser\\fileExcelRead.xlsx";
            FileParser fileParser = FileFactory.fileFactory(percorsoFileCSV);
            fileParser.readData();
            new UtenteDao().saveJson(fileParser.getLista(), "D:\\FileParser\\JsonUscitaDatiFromEXCEL.csv");
            //new UtenteDao().saveUser(fileParser.getLista());
             /*CsvParser csvParser = new CsvParser("D:\\FileParser\\fileTextRead.csv");
            csvParser.readData2();*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
