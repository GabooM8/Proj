/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Utente;


/**
 * Controller per la schermata di registrazione.
 * Gestisce l'interazione dell'utente con i campi di input e la navigazione tra le schermate.
 * Permette agli utenti di registrarsi inserendo i propri dati personali e di accesso.
 * Dopo la registrazione, l'utente viene reindirizzato alla schermata "Home".
 */
public class RegisterController {

    /**
     * Costruttore privato per evitare l'istanza della classe.
     */
    private RegisterController() {}

    /**
     * Metodo per passare alla schermata di login. 
     * 
     * @throws IOException errore durante il caricamento della schermata di login.
     */
    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("Login");

       
       

        //prova funzionamento FileMenager e funzioni per trasformare CSV in oggetti Ristorante e file.bin
        /*
        String filename = "ristoranti.bin";

        List<Object> ristoranti = new ArrayList<>();

        String csvFile = "the_knife/src/main/java/the_knife/files/michelin_my_maps.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String line;
        int cont = 0;
        while ((line = br.readLine()) != null) { 
            if (cont == 0) {
                cont++;
                continue; // Salta l'intestazione del CSV
            }

            String[] values = parseCSVLine(line); // Fa split (considerando le virgole tra virgolette)

            // Rimuove le virgolette da ogni valore
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim().replace("\"", "");
            }

            // Creo variabili
            String[] location = values[2].split(",");

            String citta = location[0].trim();
            String nazione;
            if (location.length < 2) {
                nazione = location[0].trim();
            } else {
                nazione = location[1].trim();
            }

            int numero_st = 0;
            boolean delivery = values[7] != null && !values[7].isEmpty();
            boolean prenotazione = values[9] != null && !values[9].isEmpty();

            Ristorante ristorante = new Ristorante(
                cont,
                values[0],
                values[1],
                nazione,
                citta,
                values[3].length(),
                numero_st,
                values[4],
                Double.parseDouble(values[6]),
                Double.parseDouble(values[5]),
                delivery,
                prenotazione,
                values[12]
            );
            ristoranti.add(ristorante);
            cont++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    

        FileMenager.addToFile(ristoranti, filename);

        List<Object> ristorantiRead = FileMenager.readFromFile(filename);
        for (Object obj : ristorantiRead) {
            Ristorante ris = (Ristorante) obj;
            System.out.println("Ristorante: " + ris.toString());
        }
        */
    }

    /**
     * Metodo per fare lo split di una riga CSV considerando le virgole tra virgolette. 
     * 
     * @param line La riga CSV da analizzare.
     * @return String[] Un array di stringhe che rappresentano i valori della riga CSV.
     */
    public static String[] parseCSVLine(String line) {
        // Metodo per fare lo split di una riga CSV considerando le virgole tra virgolette
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    // inizializza i campi FXML

    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private DatePicker datan;
    @FXML
    private TextField domicilio;
    @FXML
    private ComboBox<String> cmbx_rl;

    /** 
     * Metodo che viene chiamato quando l'utente preme il pulsante di registrazione.
     * 
     * @throws IOException Se si verifica un errore durante il caricamento della schermata "Home".
     */
    @FXML
    public void switchToHome() throws IOException {
        // Recupera i valori dai campi di input
        String U_nome=nome.getText();
        String U_cognome=cognome.getText();
        String U_username=username.getText();
        String U_password=password.getText();
        LocalDate U_datan=datan.getValue();
        String U_domicilio=domicilio.getText();
        String U_rls = cmbx_rl.getValue();

        // Controlla se i campi sono vuoti
        if(U_nome.isEmpty() || U_cognome.isEmpty() || U_username.isEmpty() || U_password.isEmpty() || U_datan == null || U_domicilio.isEmpty() || U_rls == null) 
        {
            // Mostra un alert se i campi non sono compilati
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi");
            alert.showAndWait();
            return;
        }
        Boolean U_rl = (U_rls.equals("Ristoratore")) ? true : false;

        Funzioni funzioni = new Funzioni();
        List<Utente> utenti = new ArrayList<>();
        utenti = funzioni.getUtenti();
        int id = utenti.size() + 1;

     
        for(Utente u : utenti)
        {
            if(u.getUsername().equals(U_username)) {
                // Mostra un alert se l'username è già esistente
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Username già esistente");
                alert.showAndWait();    
                return;
            }
        }       

        // Crea un nuovo oggetto Utente con i valori dei campi di input
        Utente u = new Utente(id, U_nome, U_cognome, U_username, U_password, U_datan, U_domicilio, U_rl);
        utenti.add(u);
        List<Object> utentiObj = new ArrayList<>(utenti);
        FileMenager.addToFile(utentiObj, "Utenti.bin");

        // Mostra un alert di successo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Registrazione avvenuta con successo");
        alert.showAndWait();

        // Imposta la root dell'applicazione su "Home" con l'utente appena registrato
        App.setRoot("Home",u);
    }
}