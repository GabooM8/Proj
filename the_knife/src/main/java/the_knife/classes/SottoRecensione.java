package the_knife.classes;
import java.io.Serializable;

/**
 * Classe che rappresenta una sotto-recensione (risposta) di una recensione principale.
 * Implementa Serializable per consentire la serializzazione degli oggetti SottoRecensione.
 */
public class SottoRecensione implements Serializable {
    /**
     * ID univoco della sotto-recensione.
     */
    private int id;
    /**
     * ID della recensione padre a cui questa sotto-recensione risponde.
     */
    private int id_padre;
    /**
     * Testo della sotto-recensione.
     */
    private String testo;
    /**
     * ID dell'utente che ha scritto la sotto-recensione.
     */
    private int id_utente;

    /**
     * Costruttore della classe SottoRecensione.
     * 
     * @param id ID univoco della sotto-recensione.
     * @param id_padre ID della recensione padre a cui questa sotto-recensione risponde.
     * @param testo Testo della sotto-recensione.
     * @param id_utente ID dell'utente che ha scritto la sotto-recensione.
     */
    public SottoRecensione(int id, int id_padre, String testo, int id_utente) {
        this.id_padre = id_padre;
        this.id = id;
        this.testo = testo;
        this.id_utente = id_utente;
    }

    /**
     * Restituisce l'ID della sotto-recensione.
     * 
     * @return int
     */
    public int getId() {
        return id;
    }
    /**
     * Restituisce l'ID della recensione padre a cui questa sotto-recensione risponde. 
     * 
     * @return int
     */
    public int getIdPadre() {
        return id_padre;
    }
    /**
     * Restituisce il testo della sotto-recensione. 
     * 
     * @return String
     */
    public String getTesto() {
        return testo;
    }
    /**
     * Restituisce l'ID dell'utente che ha scritto la sotto-recensione. 
     * 
     * @return int
     */
    public int getIdUtente() {
        return id_utente;
    }

    /**
     * Restituisce una rappresentazione in formato stringa della sotto-recensione. 
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "id=" + getId() + "\n" +
                "testo='" + getTesto() + '\n' +
                "id_utente=" + getIdUtente() + "\n" +
                ", id_recensione_padre=" + id_padre +
                '\n';
    }

}
