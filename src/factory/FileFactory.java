package factory;

import model.CsvParser;
import model.ExcelParser;
import model.FileParser;
import utils.Costanti;

import java.util.Locale;

public class FileFactory {

    public enum EstensioneFile {
        CSV,
        XLSX
    }

    private FileFactory() {
    }
    public static FileParser fileFactory(String percorso) throws Exception {
        try {
            String[] splitPercorso = percorso.split("\\.");
            String estensioneFile = splitPercorso[splitPercorso.length - 1].toUpperCase();
            EstensioneFile estensione = EstensioneFile.valueOf(estensioneFile);
            switch (estensione) {
                case CSV:
                    return new CsvParser(percorso);
                case XLSX:
                    return new ExcelParser(percorso);
                default:
                    throw new Exception("Formato file non valido: " + estensione);
            }
        }catch (Exception e) {
            throw new Exception(e.getMessage() != null ? e.getMessage() : "Errore durante la creazione del parser del file");
        }
    }
}
