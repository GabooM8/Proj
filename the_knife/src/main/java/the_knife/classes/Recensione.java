package the_knife.classes;
import java.io.Serializable;

public class Recensione implements Serializable {
    int id;
    int num_stelle;
    String testo;
    int id_utente;

    public Recensione(int id, int num_stelle, String testo, int id_utente) {
        this.id = id;
        this.num_stelle = num_stelle;
        this.testo = testo;
        this.id_utente = id_utente;
    }

    public Recensione(int id, int num_stelle, int id_utente) {
        this.id = id;
        this.num_stelle = num_stelle;
        this.testo = "";
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
    public int getNumStelle() {
        return num_stelle;
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
        return "id:" + id + "\n" +
                "num_stelle:" + num_stelle + "\n" +
                "testo:'" + testo + "'\n";
    }
}
