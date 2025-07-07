/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

/**
 * Classe Launcher per avviare l'applicazione JavaFX "The Knife".
 * Questa classe contiene il metodo main che richiama il metodo main della classe principale JavaFX.
 */
public class Launcher {

    /**
     * Costruttore privato per evitare l'istanza della classe.
     * Questa classe è una utility e non dovrebbe essere istanziata.
     */
    private Launcher() {}

    /**
     * Metodo main per avviare l'applicazione JavaFX.
     * 
     * @param args Argomenti della riga di comando (non utilizzati in questo caso).
     */
    public static void main(String[] args) {
        // Chiama il metodo main della classe principale JavaFX (App.java)
        App.main(args);
    }
}
