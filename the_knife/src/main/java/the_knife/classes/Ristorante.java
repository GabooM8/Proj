package the_knife.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un ristorante.
 * Implementa Serializable per consentire la serializzazione degli oggetti Ristorante.
 */
public class Ristorante implements Serializable {
    /**
     * ID univoco del ristorante.
     */
    int id;
    /**
     * Nome del ristorante.
     */
    String nome;
    /**
     * Indirizzo del ristorante.
     */
    String indirizzo;
    /**
     * Nazione in cui si trova il ristorante.
     */
    String nazione;
    /**
     * Città in cui si trova il ristorante.
     */
    String citta;
    /**
     * Prezzo medio del ristorante, rappresentato da un intero che può assumere valori da 1 a 4.
     * 1: Bassa (€), 2: Media (€€), 3: Alta (€€€), 4: Molto Alta (€€€€).
     */
    int prezzo;
    /**
     * Numero di stelle del ristorante, da 1 a 5.
     */
    int numStelle;
    /**
     * Tipo di cucina offerta dal ristorante.
     */
    String cucina;
    /**
     * Latitudine del ristorante, rappresentata come un double.
     */
    double latitudine;
    /**
     * Longitudine del ristorante, rappresentata come un double.
     */
    double longitudine;
    /**
     * Indica se il ristorante offre un servizio di delivery.
     */
    boolean delivery;
    /**
     * Indica se il ristorante accetta prenotazioni.
     */
    boolean prenotazione;
    /**
     * Lista di ID delle recensioni associate al ristorante.
     */
    ArrayList<Integer> recensioni;
    /**
     * Servizi offerti dal ristorante, come Wi-Fi, parcheggio, ecc.
     */
    String servizi;
    //String telefono; // per delivery
    //String weburl; // per prenotazione online

    /**
     * Costruttore della classe Ristorante senza parametri.
     * Inizializza la lista delle recensioni come una nuova ArrayList.
     */
    public Ristorante() {
    }

    /**
     * Costruttore della classe Ristorante con parametri.
     * Inizializza tutti i campi del ristorante e la lista delle recensioni come una nuova ArrayList.
     *
     * @param id ID univoco del ristorante.
     * @param nome Nome del ristorante.
     * @param indirizzo Indirizzo del ristorante.
     * @param nazione Nazione in cui si trova il ristorante.
     * @param citta Città in cui si trova il ristorante.
     * @param prezzo Prezzo medio del ristorante (1-4).
     * @param numStelle Numero di stelle del ristorante (1-5).
     * @param cucina Tipo di cucina offerta dal ristorante.
     * @param latitudine Latitudine del ristorante.
     * @param longitudine Longitudine del ristorante.
     * @param delivery Indica se il ristorante offre un servizio di delivery.
     * @param prenotazione Indica se il ristorante accetta prenotazioni.
     * @param servizi Servizi offerti dal ristorante.
     */
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
     * Restituisce l'ID del ristorante. 
     * 
     * @return int
     */
    public int getId() {
        return id;
    }
    /**
     * Restituisce il nome del ristorante. 
     * 
     * @return String
     */
    public String getNome() {
        return nome;
    }
    /**
     * Restituisce l'indirizzo del ristorante. 
     * 
     * @return String
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    /**
     * Restituisce la nazione in cui si trova il ristorante. 
     * 
     * @return String
     */
    public String getNazione() {
        return nazione;
    }
    /** 
     * Restituisce la città in cui si trova il ristorante.
     * 
     * @return String
     */
    public String getCitta() {
        return citta;
    }
    /**
     * Restituisce il prezzo medio del ristorante. 
     * 
     * @return int
     */
    public int getPrezzo() {
        return prezzo;
    }
    /**
     * Restituisce il numero di stelle del ristorante. 
     * 
     * @return int
     */
    public int getNumStelle() {
        return numStelle;
    }
    /**
     * Restituisce il tipo di cucina offerta dal ristorante. 
     * 
     * @return String
     */
    public String getCucina() {
        return cucina;
    }
    /**
     * Restituisce la latitudine del ristorante. 
     * 
     * @return double
     */
    public double getLatitudine() {
        return latitudine;
    }
    /**
     * Restituisce la longitudine del ristorante. 
     * 
     * @return double
     */
    public double getLongitudine() {
        return longitudine;
    }
    /**
     * Restituisce true se il ristorante offre un servizio di delivery, false altrimenti. 
     * 
     * @return boolean
     */
    public boolean getDelivery() {
        return delivery;
    }
    /**
     * Restituisce true se il ristorante accetta prenotazioni, false altrimenti. 
     * 
     * @return boolean
     */
    public boolean getPrenotazione() {
        return prenotazione;
    }
    /**
     * Restituisce la lista degli ID delle recensioni associate al ristorante. 
     * 
     * @return List<Integer>
     */
    public List<Integer> getRecensioni() {
        return recensioni;
    }
    /**
     * Restituisce i servizi offerti dal ristorante. 
     * 
     * @return String
     */
    public String getServizi(){
        return servizi;
    }
    /*public String getTelefono() {
        return telefono;
    }
    public String getWeburl() {
        return weburl;
    }*/

    /** 
     * Imposta l'ID del ristorante.
     * 
     * @param id ID univoco del ristorante.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Imposta il nome del ristorante.
     *  
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Imposta l'indirizzo del ristorante. 
     * 
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /**
     * Imposta la nazione in cui si trova il ristorante. 
     * 
     * @param nazione
     */
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    /**
     * Imposta la città in cui si trova il ristorante. 
     * 
     * @param citta
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }
    /**
     * Imposta il prezzo medio del ristorante. 
     * 
     * @param prezzo
     */
    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Imposta il numero di stelle del ristorante. 
     * 
     * @param numStelle
     */
    public void setNumStelle(int numStelle) {
        this.numStelle = numStelle;
    }
    /**
     * Imposta il tipo di cucina offerta dal ristorante. 
     * 
     * @param cucina
     */
    public void setCucina(String cucina) {
        this.cucina = cucina;
    }
    /**
     * Imposta la latitudine del ristorante. 
     * 
     * @param longitudine
     */
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
    /**
     * Imposta la latitudine del ristorante. 
     * 
     * @param latitudine
     */
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }
    /**
     * Imposta se il ristorante offre un servizio di delivery. 
     * 
     * @param delivery
     */
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
    /**
     * Imposta se il ristorante accetta prenotazioni. 
     * 
     * @param prenotazione
     */
    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }
    /**
     * Imposta i servizi offerti dal ristorante. 
     * 
     * @param servizi
     */
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

    /**
     * Aggiunge una recensione al ristorante e aggiorna il numero di stelle medie.
     * 
     * @param id_recensione ID della recensione da aggiungere.
     */
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
     * Restituisce una rappresentazione testuale del ristorante.
     *  
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

