package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import model.Utente;
import utils.Costanti;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UtenteDao {

    public void saveUser(List<Utente> listaUtenti) throws Exception {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase mongoDatabase = mongoClient.getDatabase("digitap");
            MongoCollection<Document> collection = mongoDatabase.getCollection("utente");
            collection.insertMany(listaUtenti.stream()
                    .map(utente -> createDocument(utente, Costanti.CF_STRING, Costanti.CODICE_ENTE_STRING, Costanti.NOME_STRING, Costanti.COGNOME_STRING))
                    .collect(Collectors.toList()));
          /*  for(Utente utente : listaUtenti){
                Document document = createDocument(utente,Costanti.CF_STRING,Costanti.CODICE_ENTE_STRING,Costanti.NOME_STRING,Costanti.COGNOME_STRING);
                collection.insertOne(document);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Errore connessione al db --- saveUser");
        }
    }

    /*
    Libreria Jackson utilizzata anche nel progetto ExoChat, offre strumenti per lavorare con il formato json, ObjectMapper viene utilizzato
    per serializzare e deserializzare,ovvero convertire oggetti java in json o da json in oggetti java, in questo caso sto serializzando un oggetto java
    in un json in un file ma potrei anche serializzarlo in un array di byte e portarlo a front end come gia fatto nel progetto della banca per la lista
    delle transazioni, annotazione @JsonInclude(JsonInclude.Include.NON_NULL) sopra la classe Utente, per non serializzare e non deserializzare le proprieta NULL
    Serializzazione, processo di conversione di un oggetto in un formato che puo essere trasmesso in rete o memorizzato in un db. Deserializzazione processo inverso,
    dati serializzati che vengono convertiti in un oggetto.
     */
    public void saveJson(List<Utente> listaUtenti, String percorso) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(percorso), listaUtenti);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("C'e stato un errore nel salvataggio del file --- saveJson");
        }
    }

    private Document createDocument(Utente utente, String... args) {
        Document document = new Document();
        for (String s : args) {
            if (s.equalsIgnoreCase(Costanti.CF_STRING) && Objects.nonNull(utente.getCf())) {
                document.append(s, utente.getCf());
            } else if (s.equalsIgnoreCase(Costanti.CODICE_ENTE_STRING) && Objects.nonNull(utente.getCodiceEnte())) {
                document.append(s, utente.getCodiceEnte());
            } else if (s.equalsIgnoreCase(Costanti.NOME_STRING) && Objects.nonNull(utente.getNome())) {
                document.append(s, utente.getNome());
            } else if (s.equalsIgnoreCase(Costanti.COGNOME_STRING) && Objects.nonNull(utente.getCognome())) {
                document.append(s, utente.getCognome());
            }
        }
        return document;
    }
}
