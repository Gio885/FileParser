package model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import utils.Order;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser extends FileParser {
    public CsvParser(String percorso) {
        super(percorso);
    }

    @Override
    public void readData() throws Exception {
        try {
            Reader in = new FileReader(loadFile());
            lista = new ArrayList<>();
            List<CSVRecord> valori = CSVFormat.DEFAULT.builder().setCommentMarker('-').setDelimiter(";").setEscape('\\').build().parse(in).getRecords();
            List<Method> metodiUtente = new ArrayList<>(Arrays.asList(Utente.class.getDeclaredMethods())).stream()
                    .filter(metodo -> metodo.getAnnotation(Order.class) != null)
                    .sorted(Comparator.comparingInt(metodo -> metodo.getAnnotation(Order.class).value()))
                    .collect(Collectors.toList());
            for (CSVRecord v : valori) {
                System.out.println(v);
                Utente utente = new Utente();
                for (int i = 0; i < v.size(); i++) {
                    if (!v.get(i).isBlank()) {
                        System.out.println(v.get(i) + " " + metodiUtente.get(i).getName());
                        metodiUtente.get(i).invoke(utente, v.get(i));
                    }
                }
                lista.add(utente);
            }

         /*   valori.forEach(riga -> {
                Utente utente = new Utente();
                for (int i = 0; i < riga.size(); i++) {
                    try {
                        metodiUtente.get(i).invoke(utente, riga.get(i));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
                lista.add(utente);
            });*/
            //lista.forEach(e -> System.out.println(e.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("C'e stato un errore nella lettura del file --- readData CsvParser");
        }
    }

    public void fromByteArray(byte[] file) {
        try {
            ByteArrayInputStream s = new ByteArrayInputStream(new FileInputStream(loadFile()).readAllBytes());
            lista = new ArrayList<>();
            Reader in = new InputStreamReader(s);
            List<CSVRecord> valori = CSVFormat.DEFAULT.builder().setDelimiter(';').setEscape('\\').build().parse(in).getRecords();
            List<Method> metodiUtente = new ArrayList<>(Arrays.asList(Utente.class.getDeclaredMethods())).stream()
                    .filter(metodo -> metodo.getAnnotation(Order.class) != null)
                    .sorted(Comparator.comparingInt(metodo -> metodo.getAnnotation(Order.class).value()))
                    .collect(Collectors.toList());
            for (CSVRecord v : valori) {
                System.out.println(v);
                Utente utente = new Utente();
                for (int i = 0; i < v.size(); i++) {
                    if (!v.get(i).isBlank()) {
                        System.out.println(v.get(i) + " " + metodiUtente.get(i).getName());
                        metodiUtente.get(i).invoke(utente, v.get(i));
                    }
                }
                lista.add(utente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







                    /*

                    public void readData() throws Exception {
        try {
            Scanner scanner = new Scanner(loadFile());
            scanner.useDelimiter("\r\n");
            List<Utente> listaUtenti = new ArrayList<Utente>();
            while (scanner.hasNext()) {
                String primaRiga = scanner.next();
                if (!primaRiga.isBlank()) {
                    Utente utente = new Utente();
                    String[] campi = primaRiga.split(";");
                    for (int i = 0; i < campi.length; i++) {
                        if (campi[i] != "") {
                            if (i == Costanti.CF) {
                                utente.setCf(campi[i]);
                            } else if (i == Costanti.CODICE_ENTE) {
                                utente.setCodiceEnte(campi[i]);
                            } else if (i == Costanti.NOME) {
                                utente.setNome(campi[i]);
                            } else if (i == Costanti.COGNOME) {
                                utente.setCognome(campi[i]);
                            }
                        }
                    }
                    listaUtenti.add(utente);
                }
                setLista(listaUtenti);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("C'e stato un errore nella lettura del file --- readData CsvParser");
        }
    }



 public void readData2() throws Exception {
        try {
            Scanner scanner = new Scanner(loadFile());
            scanner.useDelimiter("\r\n");
            lista = new ArrayList<>();
            while (scanner.hasNext()) {
                String[] primaRigaSplit = scanner.next().split("\r\n");
                //List<String> valori = Arrays.asList(String.join(" ",primaRigaSplit).split(";"));
                String[] valori = String.join(" ", primaRigaSplit).split(";");
                Utente utente = new Utente();
                List<Method> metodiUtente = new ArrayList<>(Arrays.asList(Utente.class.getDeclaredMethods())).stream()
                        .filter(metodo -> metodo.getAnnotation(Order.class) != null)
                        .sorted(Comparator.comparingInt(metodo -> metodo.getAnnotation(Order.class).value()))
                        .collect(Collectors.toList());
                for (int i = 0; i < valori.length; i++) {
                    if (!valori[i].isBlank()) {
                        metodiUtente.get(i).invoke(utente, valori[i]);
                    }
                }
                IntStream.range(0,valori.length).forEach(i->{
                    if(!valori[i].isBlank()){
                        try {
                            metodiUtente.get(i).invoke(utente,valori[i]);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                lista.add(utente);
                        }
                        //lista.forEach(e -> System.out.println(e.toString()));
                        } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("C'e stato un errore nella lettura del file --- readData CsvParser");
                        }
                        }
                    valori.forEach(valore->campiUtente.forEach(campoUtente->{
                        if(campo !=""){
                            for(int i=0;i<metodiUtente.size();i++){
                                CICLO VALORE CAMPI E CAMPI UTENTE, VERIFICO VALORE CAMPO VUOTO SE NON LO E INVOCO IL METODO FACCENDO IL MATCH CON IL NOME CAMPO..... TUTTO QUESTO
                                 SE L'ORDINE DEI CAMPI UTENTE E IN ORDINE
                            }
                        }
                    }));
                    for (int i = 0; i < campi.length; i++) {
                        if (campi[i] != "") {
                            int finalI = i;
                            metodiUtente.forEach(e-> {
                                try{
                                    if(finalI == 0 && e.getName().replace("set","").toLowerCase().equalsIgnoreCase(Costanti.CF_STRING)){
                                        e.invoke(utente,campi[finalI]);
                                    } else if (finalI == 1 && e.getName().replace("set","").toLowerCase().equalsIgnoreCase(Costanti.CODICE_ENTE_STRING)){
                                        e.invoke(utente,campi[finalI]);
                                    } else if (finalI == 2 && e.getName().replace("set","").toLowerCase().equalsIgnoreCase(Costanti.NOME_STRING)){
                                        e.invoke(utente,campi[finalI]);
                                    } else if (finalI == 3 && e.getName().replace("set","").toLowerCase().equalsIgnoreCase(Costanti.COGNOME_STRING)){
                                        e.invoke(utente,campi[finalI]);
                                    }
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                } catch (InvocationTargetException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                        }

                         public void readData3(){
        try{
            //Scanner scanner = new Scanner(loadFile());
            //scanner.useDelimiter("\r\n");
            List<Utente> listaUtenti = new ArrayList<Utente>();
            List<Field> campiUtente = Arrays.asList(Utente.class.getDeclaredFields());
            List<Method> metodiUtente =  new ArrayList<>(Arrays.asList(Utente.class.getDeclaredMethods())).stream().filter(metodo->metodo.getAnnotation(Order.class) != null).sorted(Comparator.comparingInt(metodo->metodo.getAnnotation(Order.class).value())).collect(Collectors.toList());
            //List<Method> metodiUtente =  new ArrayList<>(Arrays.asList(Utente.class.getDeclaredMethods())).stream().filter(metodo->metodo.isAnnotationPresent(Order.class)).collect(Collectors.toList());
            //System.out.println(metodiUtente.get(0).getName());
            //metodiUtente.forEach(e->System.out.println(e.getName()));
            for(Method m : metodiUtente){
                System.out.println(m.getName());
            }
            Utente utente = new Utente();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
                    }*/