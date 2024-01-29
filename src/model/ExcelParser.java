package model;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Order;

public class ExcelParser extends FileParser {

    public ExcelParser(String percorso) {
        super(percorso);
    }

    @Override
    public void readData() throws Exception {
        try {
            FileInputStream fileInputStream = new FileInputStream(loadFile());
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            lista = new ArrayList<>();
            Sheet foglioDiLavoro = workbook.getSheetAt(0);
            for (Row riga : foglioDiLavoro) {
                Utente utente = new Utente();
                List<Method> metodiUtente = new ArrayList<>(Arrays.asList(Utente.class.getDeclaredMethods())).stream().filter(metodo -> metodo.isAnnotationPresent(Order.class)).sorted(
                        Comparator.comparingInt(metodo -> metodo.getAnnotation(Order.class).value())).collect(Collectors.toList());
                for (Cell cella : riga) {
                    metodiUtente.get(cella.getColumnIndex()).invoke(utente, cella.getStringCellValue());
                }
                lista.add(utente);
            }
            //lista.forEach(e-> System.out.println(e.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("C'e stato un errore nella lettura del file --- readData ExcelParser");
        }
    }
}
