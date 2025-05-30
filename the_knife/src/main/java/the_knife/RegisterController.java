package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import the_knife.classes.Utente;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;

public class RegisterController {

    @FXML
    private void switchToPrimary() throws IOException {
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
                prenotazione
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

    public static String[] parseCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }


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

    @FXML
    private void switchToHome() throws IOException {
        String U_nome=nome.getText();
        String U_cognome=cognome.getText();
        String U_username=username.getText();
        String U_password=password.getText();
        LocalDate U_datan=datan.getValue();
        String U_domicilio=domicilio.getText();
        String U_rls = cmbx_rl.getValue();

        if(U_nome.isEmpty() || U_cognome.isEmpty() || U_username.isEmpty() || U_password.isEmpty() || U_datan == null || U_domicilio.isEmpty() || U_rls == null) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi");
            alert.showAndWait();
            return;
        }
        Boolean U_rl = (U_rls.equals("Ristoratore")) ? true : false;

        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }
        int id = utenti.size() + 1;

        
        for(Utente u : utenti)
        {
            if(u.getUsername().equals(U_username) || u.getNome().equals(U_nome) && u.getCognome().equals(U_cognome) || u.getDataNascita().equals(U_datan) || u.getLuogoDomicilio().equals(U_domicilio) || u.getIsRistoratore().equals(U_rl) || u.getPassword().equals(U_password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Utente già esistente");
                alert.showAndWait();                
                return;
            }
        }       

        Utente u = new Utente(id, U_nome, U_cognome, U_username, U_password, U_datan, U_domicilio, U_rl);
        utenti.add(u);
        List<Object> utentiObj = new ArrayList<>(utenti);
        FileMenager.addToFile(utentiObj, "Utenti.bin");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Registrazione avvenuta con successo");
        alert.showAndWait();
        App.setRoot("Home",u);
    }
}