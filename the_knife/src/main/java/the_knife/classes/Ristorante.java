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
    String servizi;
    //String telefono; // per delivery
    //String weburl; // per prenotazione online

    public Ristorante() {
    }

    public Ristorante(int id,String nome, String indirizzo, String nazione, String citta, int prezzo, int numStelle, String cucina, double latitudine, double longitudine, boolean delivery, boolean prenotazione, String servizi) {
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
        this.servizi = servizi;
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

    /** 
     * @return int
     */
    public int getId() {
        return id;
    }
    /** 
     * @return String
     */
    public String getNome() {
        return nome;
    }
    /** 
     * @return String
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    /** 
     * @return String
     */
    public String getNazione() {
        return nazione;
    }
    /** 
     * @return String
     */
    public String getCitta() {
        return citta;
    }
    /** 
     * @return int
     */
    public int getPrezzo() {
        return prezzo;
    }
    /** 
     * @return int
     */
    public int getNumStelle() {
        return numStelle;
    }
    /** 
     * @return String
     */
    public String getCucina() {
        return cucina;
    }
    /** 
     * @return double
     */
    public double getLatitudine() {
        return latitudine;
    }
    /** 
     * @return double
     */
    public double getLongitudine() {
        return longitudine;
    }
    /** 
     * @return boolean
     */
    public boolean getDelivery() {
        return delivery;
    }
    /** 
     * @return boolean
     */
    public boolean getPrenotazione() {
        return prenotazione;
    }
    /** 
     * @return List<Integer>
     */
    public List<Integer> getRecensioni() {
        return recensioni;
    }
    public String getServizi(){
        return servizi;
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
    /** 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /** 
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /** 
     * @param nazione
     */
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    /** 
     * @param citta
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }
    /** 
     * @param prezzo
     */
    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    /** 
     * @param numStelle
     */
    public void setNumStelle(int numStelle) {
        this.numStelle = numStelle;
    }
    /** 
     * @param cucina
     */
    public void setCucina(String cucina) {
        this.cucina = cucina;
    }
    /** 
     * @param longitudine
     */
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
    /** 
     * @param latitudine
     */
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }
    /** 
     * @param delivery
     */
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
    /** 
     * @param prenotazione
     */
    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }
    public void setServizi(String servizi){
        this.servizi=servizi;
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

        Funzioni f = new Funzioni();
        List<Recensione> recensionis = f.getRecensioni();
        List<Recensione> ristoranteRecensioni = new ArrayList<>();
        for (Recensione r : recensionis) {
            if (recensioni.contains(r.getId())) {
                ristoranteRecensioni.add(r);
            }
        }
        int sum = 0;
        for (Recensione r : ristoranteRecensioni) {
            sum += r.getNumStelle();
        }
        numStelle = (sum)/(ristoranteRecensioni.size());
    }

    /** 
     * @return String
     */
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
                "Prenotazione: " + (prenotazione ? "Si" : "No") + "\n" +
                "Servizi: " + servizi + "\n";
                /*"Telefono: " + telefono + "\n" +
                "Weburl: " + weburl + "\n";   */
    }
    
}

