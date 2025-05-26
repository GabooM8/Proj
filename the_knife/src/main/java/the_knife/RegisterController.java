package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

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
    private void switchToHome() throws IOException {
        App.setRoot("Home");
    }
}