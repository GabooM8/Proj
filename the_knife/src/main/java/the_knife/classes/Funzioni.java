package the_knife.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Funzioni {
    // filtri
    boolean delivery = false;
    boolean prenotazione = false;
    int prezzoMin = 0;
    int prezzoMax = Integer.MAX_VALUE; // Default massimo
    int numStelle = 0;
    String cucina = "";

    public List<Ristorante> cercaRistorante(List<Ristorante> ristoranti, String input) {
        List<Ristorante> risultati = new ArrayList<>();
        for (Ristorante ristorante : ristoranti) {
            boolean match = true;

            // Verifica se il ristorante corrisponde all'input di ricerca
            if (input != null && !input.isEmpty() &&
                !ristorante.getNome().equalsIgnoreCase(input) ||
                !ristorante.getIndirizzo().equalsIgnoreCase(input)||
                !ristorante.getCitta().equalsIgnoreCase(input) ||
                !ristorante.getNazione().equalsIgnoreCase(input)) {
                match = false;
            }

            // Applica i filtri solo se il ristorante corrisponde all'input
            if (match) {
                if (delivery && ristorante.getTelefono().equals("N/A")) {
                    match = false;
                }
                if (prenotazione && ristorante.getWeburl().equals("N/A")) {
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
        return risultati;
    }

    public Utente registrazione() {
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

            Utente nuovoUtente = new Utente(nome, cognome, username, password, dataNascita, luogoDomicilio, isRistoratore);
            System.out.println("Registrazione completata con successo!");
            System.out.println(nuovoUtente);

            return nuovoUtente;
        }
    }
}
