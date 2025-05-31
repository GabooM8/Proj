package the_knife.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ristorante implements Serializable {
    int id;
    String nome;
    String indirizzo;
    String nazione;
    String citta;
    int prezzo;
    int numStelle;
    String cucina;
    double latitudine;
    double longitudine;
    boolean delivery;
    boolean prenotazione;
    ArrayList<Integer> recensioni;
    //String telefono; // per delivery
    //String weburl; // per prenotazione online

    public Ristorante() {
    }

    public Ristorante(int id,String nome, String indirizzo, String nazione, String citta, int prezzo, int numStelle, String cucina, double latitudine, double longitudine, boolean delivery, boolean prenotazione) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.nazione = nazione;
        this.citta = citta;
        this.prezzo = prezzo;
        this.numStelle = numStelle;
        this.cucina = cucina;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.delivery = delivery;
        this.prenotazione = prenotazione;
        this.recensioni = new ArrayList<>();
        /*if (weburl == null || weburl.isEmpty()) {
            this.weburl = "N/A";
        } else {
            this.weburl = weburl;
        }
        if (telefono == null || telefono.isEmpty()) {
            this.telefono = "N/A";
        } else {
            this.telefono = telefono;
        }*/
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public String getNazione() {
        return nazione;
    }
    public String getCitta() {
        return citta;
    }
    public int getPrezzo() {
        return prezzo;
    }
    public int getNumStelle() {
        return numStelle;
    }
    public String getCucina() {
        return cucina;
    }
    public double getLatitudine() {
        return latitudine;
    }
    public double getLongitudine() {
        return longitudine;
    }
    public boolean getDelivery() {
        return delivery;
    }
    public boolean getPrenotazione() {
        return prenotazione;
    }
    public List<Integer> getRecensioni() {
        return recensioni;
    }
    /*public String getTelefono() {
        return telefono;
    }
    public String getWeburl() {
        return weburl;
    }*/

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setNumStelle(int numStelle) {
        this.numStelle = numStelle;
    }
    public void setCucina(String cucina) {
        this.cucina = cucina;
    }
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }
    /*public void setTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            this.telefono = "N/A";
        } else {
            this.telefono = telefono;
        }
    }
    public void setWeburl(String weburl) {
        if (weburl == null || weburl.isEmpty()) {
            this.weburl = "N/A";
        } else {
            this.weburl = weburl;
        }
    }*/

    public void addRecensione(int id_recensione) {
        this.recensioni.add(id_recensione);
    }

    @Override
    public String toString() {
        String prz;
        switch (prezzo) {
            case 1:
                prz = "Bassa (€)";
                break;
            case 2:
                prz = "Media (€€)";
                break;
            case 3:
                prz = "Alta (€€€)";
                break;
            case 4:
                prz = "Molto Alta (€€€€)";
                break;
            default:
                prz = "Non specificato";
                break;
        }
        
        return "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Indirizzo: " + indirizzo + "\n" +
                "Nazione: " + nazione + "\n" +
                "Citta: " + citta + "\n" +
                "Prezzo: " + prz + "\n" +
                "Numero Stelle: " + numStelle + "\n" +
                "Cucina: " + cucina + "\n" +
                "Latitudine: " + latitudine + "\n" +
                "Longitudine: " + longitudine + "\n" +
                "Delivery: " + (delivery ? "Si" : "No") + "\n" +
                "Prenotazione: " + (prenotazione ? "Si" : "No") + "\n";
                /*"Telefono: " + telefono + "\n" +
                "Weburl: " + weburl + "\n";   */
    }
    
}

