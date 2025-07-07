/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife.classes;
import java.io.Serializable;

/**
 * Classe che rappresenta una recensione di un ristorante.
 * Implementa Serializable per consentire la serializzazione degli oggetti Recensione.
 */
public class Recensione implements Serializable {
    /**
     * ID univoco della recensione.
     */
    int id;
    /**
     * Numero di stelle assegnate nella recensione.
     */
    int num_stelle;
    /**
     * Testo della recensione.
     */
    String testo;
    /**
     * ID dell'utente che ha scritto la recensione.
     */
    int id_utente;

    /**
     * Costruttore della classe Recensione.
     * 
     * @param id ID univoco della recensione.
     * @param num_stelle Numero di stelle assegnate nella recensione.
     * @param testo Testo della recensione.
     * @param id_utente ID dell'utente che ha scritto la recensione.
     */
    public Recensione(int id, int num_stelle, String testo, int id_utente) {
        this.id = id;
        this.num_stelle = num_stelle;
        this.testo = testo;
        this.id_utente = id_utente;
    }

    /**
     * Costruttore della classe Recensione senza testo.
     * 
     * @param id ID univoco della recensione.
     * @param num_stelle Numero di stelle assegnate nella recensione.
     * @param id_utente ID dell'utente che ha scritto la recensione.
     */
    public Recensione(int id, int num_stelle, int id_utente) {
        this.id = id;
        this.num_stelle = num_stelle;
        this.testo = "";
        this.id_utente = id_utente;
    }

    /** 
     * Restituisce l'ID della recensione.
     * 
     * @return int
     */
    public int getId() {
        return id;
    }
    /** 
     * Restituisce il numero di stelle assegnate nella recensione.
     * 
     * @return int
     */
    public int getNumStelle() {
        return num_stelle;
    }
    /**
     * Restituisce il testo della recensione. 
     * 
     * @return String
     */
    public String getTesto() {
        return testo;
    }
    /**
     * Restituisce l'ID dell'utente che ha scritto la recensione. 
     * 
     * @return int
     */
    public int getIdUtente() {
        return id_utente;
    }

    /**
     * Restituisce una rappresentazione testuale della recensione. 
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "id:" + id + "\n" +
                "num_stelle:" + num_stelle + "\n" +
                "testo:'" + testo + "'\n";
    }
}
