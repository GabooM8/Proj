package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL; 

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import the_knife.classes.Utente;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");

       
       

        //prova funzionamento FileMenager
        
        /*String filename = "utenti.bin";

        List<Object> utenti = new ArrayList<>();
        Utente utente1 = new Utente("Mario", "Rossi", "mario.rossi", "password123", new Date(), "Roma", false);
        Utente utente2 = new Utente("Luigi", "Verdi", "luigi.verdi", "password456", new Date(), "Milano", true);

        utenti.add(utente1);
        utenti.add(utente2);

        FileMenager.addToFile(utenti, filename);

        List<Object> utentiRead = FileMenager.readFromFile(filename);
        for (Object obj : utentiRead) {
            Utente utente = (Utente) obj;
            System.out.println("Utente: " + utente.toString());
        }*/
    }
}