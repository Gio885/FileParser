package model;

import java.io.File;
import java.util.List;

public abstract class FileParser {

    protected String percorso;
    protected List<Utente> lista;
    public FileParser(String percorso) {
        this.percorso = percorso;
    }
    public abstract void readData() throws Exception;
    protected File loadFile(){
        return new File(percorso);
    }
    public List<Utente> getLista() {
        return lista;
    }
    public void setLista(List<Utente> lista) {
        this.lista = lista;
    }


}