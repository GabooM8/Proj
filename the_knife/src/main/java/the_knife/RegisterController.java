package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.net.URL; 

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import the_knife.classes.Utente;
import the_knife.classes.Ristorante;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RegisterController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Login");

       
       

        //prova funzionamento FileMenager e funzioni per trasformare CSV in oggetti Ristorante e file.bin
        
        String filename = "ristoranti.bin";

        List<Object> ristoranti = new ArrayList<>();

        String csvFile = "the_knife/src/main/java/the_knife/files/michelin_my_maps.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int cont = 0;
            while ((line = br.readLine()) != null) { 
                if(cont == 0) {
                    cont++;
                    continue; // Salta l'intestazione del CSV
                }

                String[] values = parseCSVLine(line); //fa split (considerando le virgole tra virgolette)          

                //creo variabili

                String[] location = values[2].split(",");

                String nazione = (location[0]);
                String citta;
                if(location.length < 2)
                    citta=location[0];
                else
                    citta = (location[1]);

                
                int numero_st = 0;
                boolean delivery;
                boolean prenotazione;

                if(values[7]==null || values[7].isEmpty()) {
                    delivery = false;
                } else {
                    delivery = true;
                }
                if(values[9]==null || values[9].isEmpty()) {
                    prenotazione = false;
                } else {
                    prenotazione = true;
                }

                Ristorante ristorante = new Ristorante(cont,values[0],values[1],nazione,citta,values[3].length(),numero_st,values[4],Double.parseDouble(values[6]),Double.parseDouble(values[5]), delivery, prenotazione);
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
    }

    public static String[] parseCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home");
    }
}