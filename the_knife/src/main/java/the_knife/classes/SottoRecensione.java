package the_knife.classes;

public class SottoRecensione {
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

    public int getId() {
        return id;
    }
    public int getIdPadre() {
        return id_padre;
    }
    public String getTesto() {
        return testo;
    }
    public int getIdUtente() {
        return id_utente;
    }

    @Override
    public String toString() {
        return "id=" + getId() + "\n" +
                "testo='" + getTesto() + '\n' +
                "id_utente=" + getIdUtente() + "\n" +
                ", id_recensione_padre=" + id_padre +
                '\n';
    }

}
