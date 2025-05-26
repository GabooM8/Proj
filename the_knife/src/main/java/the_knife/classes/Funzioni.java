package the_knife.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import the_knife.FileMenager;

public class Funzioni {
    // filtri
    boolean delivery = false;
    boolean prenotazione = false;
    int prezzoMin = 0;
    int prezzoMax = Integer.MAX_VALUE; // Default massimo
    int numStelle = 0;
    String cucina = "";

    public List<Ristorante> cercaRistoranti(String input) {
        String filename = "ristoranti.bin";
        List<Ristorante> risultati = new ArrayList<>();

        // Legge i dati dal file binario
        List<Object> oggetti = FileMenager.readFromFile(filename);

        // Converte gli oggetti in istanze di Ristorante e applica i filtri
        for (Object obj : oggetti) {
            if (obj instanceof Ristorante) {
                Ristorante ristorante = (Ristorante) obj;
                boolean match = true;
                
                /*
                if (input == null || input.isEmpty()) {
                    // Se l'input è vuoto, non applicare il filtro di ricerca
                    break;
                }

                // Verifica se il ristorante corrisponde all'input di ricerca
                if (!ristorante.getNome().equalsIgnoreCase(input) ||
                    !ristorante.getIndirizzo().equalsIgnoreCase(input) ||
                    !ristorante.getCitta().equalsIgnoreCase(input) ||
                    !ristorante.getNazione().equalsIgnoreCase(input)) {
                    match = false;
                }
                    */

                // Applica i filtri solo se il ristorante corrisponde all'input
                if (match) {
                    if (delivery && !ristorante.getDelivery()) {
                        match = false;
                    }
                    if (prenotazione && !ristorante.getPrenotazione()) {
                        match = false;
                    }
                    if (ristorante.getPrezzo() < prezzoMin || ristorante.getPrezzo() > prezzoMax) {
                        match = false;
                    }
                    if (numStelle > 0 && ristorante.getNumStelle() < numStelle) {
                        match = false;
                    }
                    if (!cucina.isEmpty() && !ristorante.getCucina().equalsIgnoreCase(cucina)) {
                        match = false;
                    }
                }

                // Aggiungi il ristorante ai risultati se soddisfa tutti i criteri
                if (match) {
                    risultati.add(ristorante);
                }
            }
        }

        return risultati;
    }

    public void registrazione() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Inserisci il nome:");
            String nome = scanner.nextLine();

            System.out.println("Inserisci il cognome:");
            String cognome = scanner.nextLine();

            System.out.println("Inserisci il username:");
            String username = scanner.nextLine();

            System.out.println("Inserisci la password:");
            String password = scanner.nextLine();

            System.out.println("Inserisci la data di nascita (formato: yyyy-MM-dd):");
            Date dataNascita = null;
            while (dataNascita == null) {
                String dataInput = scanner.nextLine();
                try {
                    dataNascita = new SimpleDateFormat("yyyy-MM-dd").parse(dataInput);
                } catch (ParseException e) {
                    System.out.println("Formato data non valido. Riprova:");
                }
            }

            System.out.println("Inserisci il luogo di domicilio:");
            String luogoDomicilio = scanner.nextLine();

            Boolean isRistoratore = null;
            while (isRistoratore == null) {
                System.out.println("Sei un ristoratore? (true/false):");
                String input = scanner.nextLine().trim().toLowerCase();
                switch (input) {
                    case "true":
                        isRistoratore = true;
                        break;
                    case "false":
                        isRistoratore = false;
                        break;
                    default:
                        System.out.println("Input non valido. Inserisci 'true' o 'false'.");
                        break;
                }
            }
            
            String filename = "utenti.bin";
            List<Object> utenti = FileMenager.readFromFile(filename);

            Utente u = (Utente)utenti.getLast();

            int id = u.getId() + 1; // Incrementa l'ID dell'ultimo utente

            utenti.add(new Utente(id,nome, cognome, username, password, dataNascita, luogoDomicilio, isRistoratore));
            FileMenager.addToFile(utenti, filename);
            System.out.println("Registrazione completata con successo!");
        }
    }

    public void addPreferito(int idRistorante, int idUtente) {
        String filename = "utenti.bin";
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getId() == idUtente) {
                utente.addRistorante(idRistorante);
            }
        }
    }

    public List<Ristorante> visualizzaPreferiti(int idUtente) {
        String filename= "utenti.bin";
        Utente utente= null;
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) {
            Utente u = (Utente) obj;
            if (u.getId() == idUtente) {
                utente=u;
                break;
            }
        }
        List<Ristorante> preferiti = new ArrayList<>();
        String ristoranteFile = "ristoranti.bin";
        List<Object> ristoranti = FileMenager.readFromFile(ristoranteFile);
        for (Object obj : ristoranti) {
            Ristorante ristorante = (Ristorante) obj;
            if (utente.getRistoranti().contains(ristorante.getId())) {
                preferiti.add(ristorante);
            }
        }
        return preferiti;
    }

    public void DeletePreferito(int idRistorante, int idUtente) {
        String filename = "utenti.bin";
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getId() == idUtente) {
                List<Integer> ristoranti = utente.getRistoranti();
                if(ristoranti.contains(idRistorante)) {
                    ristoranti.remove(idRistorante);
                }
                break;
            }
        }
        FileMenager.addToFile(utenti, filename);
    }
}
