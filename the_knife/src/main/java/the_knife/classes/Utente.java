package the_knife.classes;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utente implements Serializable {
    int id;
    String nome;
    String cognome;
    String username;
    String password;
    LocalDate dataNascita;
    String luogoDomicilio;
    Boolean IsRistoratore;
    List<Integer> ristoranti; //preferiti per utente e i propri per ristoratore
    List<Integer> recensioni;

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
   * @param newNome
   */
  public void setNome(String newNome) {
    this.nome = newNome;
  }
    /** 
     * @return String
     */
    public String getCognome() {
        return cognome;
    }
    /** 
     * @param newCognome
     */
    public void setCognome(String newCognome) {
        this.cognome = newCognome;
    }
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }
    /** 
     * @param newUsername
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }
    /** 
     * @param newPassword
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    /** 
     * @return LocalDate
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    /** 
     * @param newDataNascita
     */
    public void setDataNascita(LocalDate newDataNascita) {
        this.dataNascita = newDataNascita;
    }
    /** 
     * @return String
     */
    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }
    /** 
     * @param newLuogoDomicilio
     */
    public void setLuogoDomicilio(String newLuogoDomicilio) {
        this.luogoDomicilio = newLuogoDomicilio;
    }
    /** 
     * @return Boolean
     */
    public Boolean getIsRistoratore() {
        return IsRistoratore;
    }
    /** 
     * @return List<Integer>
     */
    public List<Integer> getRistoranti() {
        return ristoranti;
    }
    /** 
     * @return List<Integer>
     */
    public List<Integer> getRecensioni() {
        return recensioni;
    }

    public Utente() {
        
    }
    public Utente(String nome, String luogo)
    {
        this.nome = nome;
        this.luogoDomicilio = luogo;
        this.IsRistoratore = null;
    }
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
     * @param id_ristorante
     */
    public void addRistorante(int id_ristorante) {
        ristoranti.add(id_ristorante);
    }

     /** 
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
