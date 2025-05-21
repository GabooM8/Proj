package the_knife.classes;

public class Ristorante {
    String nome;
    String indirizzo;
    String nazione;
    String citta;
    int prezzo;
    int numStelle;
    String cucina;
    double latitudine;
    double longitudine;
    String telefono; // per delivery
    String weburl; // per prenotazione online

    public Ristorante(String nome, String indirizzo, String nazione, String citta, int prezzo, int numStelle, String cucina, double latitudine, double longitudine, String telefono, String weburl) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.nazione = nazione;
        this.citta = citta;
        this.prezzo = prezzo;
        this.numStelle = numStelle;
        this.cucina = cucina;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        if (weburl == null || weburl.isEmpty()) {
            this.weburl = "N/A";
        } else {
            this.weburl = weburl;
        }
        if (telefono == null || telefono.isEmpty()) {
            this.telefono = "N/A";
        } else {
            this.telefono = telefono;
        }
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
    public String getTelefono() {
        return telefono;
    }
    public String getWeburl() {
        return weburl;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Indirizzo: " + indirizzo + "\n" +
                "Nazione: " + nazione + "\n" +
                "Citta: " + citta + "\n" +
                "Prezzo: " + prezzo + "\n" +
                "Numero Stelle: " + numStelle + "\n" +
                "Cucina: " + cucina + "\n" +
                "Latitudine: " + latitudine + "\n" +
                "Longitudine: " + longitudine + "\n" +
                "Telefono: " + telefono + "\n" +
                "Weburl: " + weburl + "\n";   
    }
    
}

