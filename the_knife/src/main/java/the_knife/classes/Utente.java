package the_knife.classes;
import java.io.Serializable;
import java.util.Date;

public class Utente implements Serializable {
    String nome;
    String cognome;
    String username;
    String password;
    Date dataNascita;
    String luogoDomicilio;
    Boolean IsRistoratore;

    public String getNome() {
    return nome;
  }
  public void setNome(String newNome) {
    this.nome = newNome;
  }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String newCognome) {
        this.cognome = newCognome;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public Date getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(Date newDataNascita) {
        this.dataNascita = newDataNascita;
    }
    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }
    public void setLuogoDomicilio(String newLuogoDomicilio) {
        this.luogoDomicilio = newLuogoDomicilio;
    }
    public Boolean getIsRistoratore() {
        return IsRistoratore;
    }

    public Utente(String nome, String cognome, String username, String password, Date dataNascita, String luogoDomicilio, Boolean isRistoratore) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataNascita = dataNascita;
        this.luogoDomicilio = luogoDomicilio;
        this.IsRistoratore = isRistoratore;
    }

     @Override
    public String toString(){
        return "Nome: " + nome + "\n" +
                "Cognome: " + cognome + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "Data di Nascita: " + dataNascita.toString() + "\n" +
                "Luogo di Domicilio: " + luogoDomicilio + "\n" +
                "Is Admin: " + IsRistoratore + "\n";
    }
    
}
