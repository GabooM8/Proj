/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import the_knife.classes.Funzioni;
import the_knife.classes.Ristorante;
import the_knife.classes.Utente;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * Costruttore privato per evitare l'istanza della classe.
     */
    private App() {}

    /**
     * La scena principale dell'applicazione.
     * Viene utilizzata per cambiare il contenuto della finestra principale.
     */
    private static Scene scene;

    /**
     * Metodo che viene chiamato all'avvio dell'applicazione.
     * Inizializza la scena principale e carica il file FXML per la schermata di login.
     *
     * @param stage Il palcoscenico principale dell'applicazione.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login", null), 900, 600); // Modificato per coerenza con loadFXML
        stage.setScene(scene);
        stage.setTitle("The_Knife");

        stage.setResizable(false);
        stage.setMaximized(false);
        stage.centerOnScreen();

        stage.show();
    }

    /**
     * Metodo per caricare un file FXML e restituire il nodo radice.
     *
     * @param fxml Il nome del file FXML da caricare (senza estensione).
     * @return Il nodo radice della scena.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml, null)); // Passa null per i dati
    }

    /** 
     * Metodo sovraccaricato per passare dati al controller della nuova scena.
     * 
     * @param fxml
     * @param data
     * @throws IOException
     */
    static void setRoot(String fxml, Object data) throws IOException {
        scene.setRoot(loadFXML(fxml, data));
    }
    /**
     * Metodo sovraccaricato per passare due set di dati al controller della nuova scena. 
     * 
     * @param fxml
     * @param data
     * @param data2
     * @throws IOException
     */
    static void setRoot(String fxml, Object data, Object data2) throws IOException {
        scene.setRoot(loadFXML(fxml, data, data2));
    }

    /**
     * Metodo per ottenere il nodo radice della scena corrente.
     *
     * @param fxml Il nome del file FXML da caricare (senza estensione).
     * @param data Dati da passare al controller della nuova scena.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     * @return Il nodo radice della scena corrente.
     */
    public static Parent loadFXML(String fxml, Object data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        
        if (data != null) {
            Object controller = fxmlLoader.getController();
            
            if (controller instanceof HomeController && data instanceof Utente) {
                ((HomeController) controller).initData((Utente) data);
            }
            if(controller instanceof ProfiloUtControlle && data instanceof Utente) {
                ((ProfiloUtControlle) controller).initData((Utente) data);
            }
            if(controller instanceof RistoratoreController && data instanceof Utente) {
                ((RistoratoreController) controller).initData((Utente) data);
            }
        }
        return parent;
    }

    /**
     * Metodo per caricare un file FXML e restituire il nodo radice, con dati aggiuntivi.
     *
     * @param fxml Il nome del file FXML da caricare (senza estensione).
     * @param data Dati da passare al controller della nuova scena.
     * @param data2 Dati aggiuntivi da passare al controller della nuova scena.
     * @return Il nodo radice della scena.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    public static Parent loadFXML(String fxml, Object data, Object data2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        
        if (data != null && data2 != null) {
            Object controller = fxmlLoader.getController();
            
            if (controller instanceof RistoranteController && data instanceof Ristorante) {
                ((RistoranteController) controller).initData((Ristorante) data, (Utente)data2);
            }
            if (controller instanceof RistoratoreController && data instanceof Utente) {
                ((RistoratoreController) controller).initData((Utente) data);
            }
        }
        return parent;
    }

    /**
     * Metodo principale per avviare l'applicazione JavaFX.
     *
     * @param args Gli argomenti della riga di comando (non utilizzati in questo caso).
     */
    public static void main(String[] args) {
        Funzioni f = new Funzioni();
        f.addfileRist();
        launch();
    }

}