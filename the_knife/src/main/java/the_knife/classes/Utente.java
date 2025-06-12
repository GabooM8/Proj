package the_knife.classes;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un utente del sistema.
 * Implementa Serializable per consentire la serializzazione degli oggetti Utente.
 */
public class Utente implements Serializable {
    /**
     * ID univoco dell'utente.
     */
    int id;
    /**
     * Nome dell'utente.
     */
    String nome;
    /**
     * Cognome dell'utente.
     */
    String cognome;
    /**
     * Username dell'utente.
     */
    String username;
    /**
     * Password dell'utente.
     */
    String password;
    /**
     * Data di nascita dell'utente.
     */
    LocalDate dataNascita;
    /**
     * Luogo di domicilio dell'utente.
     */
    String luogoDomicilio;
    /**
     * Indica se l'utente è un ristoratore.
     */
    Boolean IsRistoratore;
    /**
     * Lista degli ID dei ristoranti preferiti dall'utente e dei ristoranti gestiti dal ristoratore.
     */
    List<Integer> ristoranti; //preferiti per utente e i propri per ristoratore
    /**
     * Lista degli ID delle recensioni scritte dall'utente.
     */
    List<Integer> recensioni;

    /**
     * Restituisce l'ID univoco dell'utente. 
     * 
     * @return int
     */
    public int getId() {
        return id;
    }
    /**
     * Imposta l'ID univoco dell'utente. 
     * 
     * @return String
     */
    public String getNome() {
    return nome;
    }
    /**
     * Imposta il nome dell'utente. 
     * 
     * @param newNome
     */
    public void setNome(String newNome) {
        this.nome = newNome;
    }
    /**
     * Restituisce il cognome dell'utente. 
     * 
     * @return String
     */
    public String getCognome() {
        return cognome;
    }
    /**
     * Imposta il cognome dell'utente. 
     * 
     * @param newCognome
     */
    public void setCognome(String newCognome) {
        this.cognome = newCognome;
    }
    /**
     * Restituisce lo username dell'utente. 
     * 
     * @return String
     */
    public String getUsername() {
        return username;
    }
    /**
     * Imposta lo username dell'utente. 
     * 
     * @param newUsername
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    /**
     * Restituisce la password dell'utente. 
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }
    /**
     * Imposta la password dell'utente. 
     * 
     * @param newPassword
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    /**
     * Restituisce la data di nascita dell'utente. 
     * 
     * @return LocalDate
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    /**
     * Imposta la data di nascita dell'utente. 
     * 
     * @param newDataNascita
     */
    public void setDataNascita(LocalDate newDataNascita) {
        this.dataNascita = newDataNascita;
    }
    /**
     * Restituisce il luogo di domicilio dell'utente. 
     * 
     * @return String
     */
    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }
    /**
     * Imposta il luogo di domicilio dell'utente. 
     * 
     * @param newLuogoDomicilio
     */
    public void setLuogoDomicilio(String newLuogoDomicilio) {
        this.luogoDomicilio = newLuogoDomicilio;
    }
    /**
     * Restituisce un valore booleano che indica se l'utente è un ristoratore. 
     * 
     * @return Boolean
     */
    public Boolean getIsRistoratore() {
        return IsRistoratore;
    }
    /**
     * Imposta il valore booleano che indica se l'utente è un ristoratore. 
     * 
     * @return List<Integer>
     */
    public List<Integer> getRistoranti() {
        return ristoranti;
    }
    /**
     * Restituisce la lista degli ID delle recensioni scritte dall'utente. 
     * 
     * @return List<Integer>
     */
    public List<Integer> getRecensioni() {
        return recensioni;
    }

    /**
     * Costruttore della classe Utente senza parametri.
     * Inizializza le liste degli ID dei ristoranti e delle recensioni.
     */
    public Utente() {
        
    }

    /**
     * Costruttore della classe Utente con nome e luogo di domicilio.
     * Inizializza le liste degli ID dei ristoranti e delle recensioni.
     * 
     * @param nome Il nome dell'utente.
     * @param luogo Il luogo di domicilio dell'utente.
     */
    public Utente(String nome, String luogo)
    {
        this.nome = nome;
        this.luogoDomicilio = luogo;
        this.IsRistoratore = null;
    }

    /**
     * Costruttore della classe Utente con tutti i parametri.
     * Inizializza le liste degli ID dei ristoranti e delle recensioni.
     * 
     * @param id L'ID univoco dell'utente.
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param username Lo username dell'utente.
     * @param password La password dell'utente.
     * @param dataNascita La data di nascita dell'utente.
     * @param luogoDomicilio Il luogo di domicilio dell'utente.
     * @param isRistoratore Indica se l'utente è un ristoratore.
     */
    public Utente(int id,String nome, String cognome, String username, String password, LocalDate dataNascita, String luogoDomicilio, Boolean isRistoratore) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataNascita = dataNascita;
        this.luogoDomicilio = luogoDomicilio;
        this.IsRistoratore = isRistoratore;
        this.ristoranti = new ArrayList<>();
        this.recensioni = new ArrayList<>();
    }

    /**
     * Aggiunge un ID di recensione alla lista delle recensioni dell'utente.
     *  
     * @param id_recensione
     */
    public void addRecensione(int id_recensione) {
        if (this.recensioni == null) { // Controllo di sicurezza, anche se dovrebbe essere inizializzata nel costruttore
            this.recensioni = new ArrayList<>();
        }
        if (!this.recensioni.contains(id_recensione)) { // Aggiungi l'ID solo se non è già presente
            this.recensioni.add(id_recensione);
        }
    }
    /**
     * Aggiunge un ID di ristorante alla lista dei ristoranti preferiti o gestiti dall'utente. 
     * 
     * @param id_ristorante
     */
    public void addRistorante(int id_ristorante) {
        ristoranti.add(id_ristorante);
    }

     /**
      * Restituisce una rappresentazione in formato stringa dell'oggetto Utente. 
      * 
      * @return String
      */
     @Override
    public String toString(){
        return "id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Cognome: " + cognome + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "Data di Nascita: " + dataNascita.toString() + "\n" +
                "Luogo di Domicilio: " + luogoDomicilio + "\n" +
                "Is Admin: " + IsRistoratore + "\n";
    }
    
}
