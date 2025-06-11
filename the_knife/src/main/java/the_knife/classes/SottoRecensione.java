package the_knife.classes;
import java.io.Serializable;

public class SottoRecensione implements Serializable {
    private int id;
    private int id_padre;
    private String testo;
    private int id_utente;

    public SottoRecensione(int id, int id_padre, String testo, int id_utente) {
        this.id_padre = id_padre;
        this.id = id;
        this.testo = testo;
        this.id_utente = id_utente;
    }

    /** 
     * @return int
     */
    public int getId() {
        return id;
    }
    /** 
     * @return int
     */
    public int getIdPadre() {
        return id_padre;
    }
    /** 
     * @return String
     */
    public String getTesto() {
        return testo;
    }
    /** 
     * @return int
     */
    public int getIdUtente() {
        return id_utente;
    }

    /** 
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
