package the_knife.classes;

public class Recensione {
    int id;
    int num_stelle;
    String testo;
    int id_utente; // Aggiunto per riferimento all'utente che ha scritto la recensione

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

    public int getId() {
        return id;
    }
    public int getNumStelle() {
        return num_stelle;
    }
    public String getTesto() {
        return testo;
    }
    public int getIdUtente() {
        return id_utente;
    }

    @Override
    public String toString() {
        return "id:" + id + "\n" +
                "num_stelle:" + num_stelle + "\n" +
                "testo:'" + testo + "'\n";
    }
}
