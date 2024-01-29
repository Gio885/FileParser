package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import utils.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Utente {

    private String cf;
    private String codiceEnte;
    private String nome;
    private String cognome;

    public String getCf() {
        return cf;
    }

    @Order(0)
    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    @Order(1)
    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public String getNome() {
        return nome;
    }

    @Order(2)
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    @Order(3)
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "cf='" + cf + '\'' +
                ", codiceEnte='" + codiceEnte + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }
}
