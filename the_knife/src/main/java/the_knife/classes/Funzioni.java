package the_knife.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import the_knife.FileMenager;

public class Funzioni {
    public List<Ristorante> cercaRistorante(String inputSearchBar, String inputLocationFilter, int fasciaPrezzoFiltro, int numStelleFiltro, String cucinaFiltro, boolean deliveryFiltro, boolean prenotazioneFiltro) {
        String filename = "ristoranti.bin";
        List<Ristorante> risultati = new ArrayList<>();

        // Legge i dati dal file binario
        List<Object> oggetti = FileMenager.readFromFile(filename);

        // Converte gli oggetti in istanze di Ristorante e applica i filtri
        for (Object obj : oggetti) {
            if (obj instanceof Ristorante) {
                Ristorante ristorante = (Ristorante) obj;
                boolean match = true;

                // Filtra per input (searchBar) testuale se fornito
                if (inputSearchBar != null && !inputSearchBar.isEmpty()) {
                    boolean textMatch = ristorante.getNome().toLowerCase().contains(inputSearchBar.toLowerCase()) ||
                                        ristorante.getCucina().toLowerCase().contains(inputSearchBar.toLowerCase());
                    if (!textMatch) {
                        match = false;
                    }
                } else {
                    // Se l'input è vuoto, consideriamo che il "filtro testuale" sia superato
                    // e procediamo con gli altri filtri.
                }

                // Filtra per input (locationFilter) testuale se fornito
                if (inputLocationFilter != null && !inputLocationFilter.isEmpty()) {
                    boolean textMatch = ristorante.getIndirizzo().toLowerCase().contains(inputLocationFilter.toLowerCase()) ||
                                        ristorante.getCitta().toLowerCase().contains(inputLocationFilter.toLowerCase()) ||
                                        ristorante.getNazione().toLowerCase().contains(inputLocationFilter.toLowerCase());
                    if (!textMatch) {
                        match = false;
                    }
                } else {
                    // Se l'input è vuoto, consideriamo che il "filtro testuale" sia superato
                    // e procediamo con gli altri filtri.
                }

                // Applica gli altri filtri se il match è ancora valido
                if (match) {
                    if (deliveryFiltro && !ristorante.getDelivery()) {
                        match = false;
                    }
                    if (prenotazioneFiltro && !ristorante.getPrenotazione()) {
                        match = false;
                    }
                    if (fasciaPrezzoFiltro > 0 && ristorante.getPrezzo() != fasciaPrezzoFiltro) {
                        match = false;
                    }
                    if (numStelleFiltro > 0 && ristorante.getNumStelle() < numStelleFiltro) {
                        match = false;
                    }
                    if (cucinaFiltro != null && !cucinaFiltro.isEmpty() && !ristorante.getCucina().equalsIgnoreCase(cucinaFiltro)) {
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
            LocalDate dataNascita = null;
            while (dataNascita == null) {
                String dataInput = scanner.nextLine();
                try {
                    dataNascita = LocalDate.parse(dataInput, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e) {
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
            
            String filename = "Utenti.bin";
            List<Object> utenti = FileMenager.readFromFile(filename);

            Utente u = (Utente)utenti.getLast();

            int id = u.getId() + 1; // Incrementa l'ID dell'ultimo utente

            utenti.add(new Utente(id,nome, cognome, username, password, dataNascita, luogoDomicilio, isRistoratore));
            FileMenager.addToFile(utenti, filename);
            System.out.println("Registrazione completata con successo!");
        }
    }

    public void addPreferito(int idRistorante, int idUtente) {
        String filename = "Utenti.bin";
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getId() == idUtente) {
                utente.addRistorante(idRistorante);
            }
        }
    }

    public List<Ristorante> visualizzaPreferiti(int idUtente) {
        String filename= "Utenti.bin";
        Utente utente= null;
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) {
            Utente u = (Utente) obj;
            System.out.println(u.getId() + " " + idUtente);
            if (u.getId() == idUtente) {
                utente=u;
                break;
            }
        }
        if (utente == null) {
            System.out.println("Utente non trovato.");
            return new ArrayList<>();
        }
        System.out.println("Ristoranti preferiti per l'utente " + utente.getNome() + ":");
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
        String filename = "Utenti.bin";
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

    public List<Utente> getUtenti() {
        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }
        return utenti;
    }

    public List<Recensione> getRecensioni() {
        List<?> objects = (List<?>) FileMenager.readFromFile("recensioni.bin");
        List<Recensione> recensioni = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Recensione) {
                recensioni.add((Recensione) obj);
            }
        }
        return recensioni;
    }

    /**
     * Cerca la città dell'utente tra i ristoranti e restituisce latitudine e longitudine se trova una corrispondenza.
     * @param utente L'utente di cui cercare la città.
     * @return Un array double[2] con latitudine e longitudine, oppure null se non trovata.
     */
    public double[] trovaCoordinateUtente(Utente utente) {
        if (utente == null || utente.getLuogoDomicilio() == null) return null;
        String cittaUtente = utente.getLuogoDomicilio().trim().toLowerCase();
        List<Object> ristoranti = FileMenager.readFromFile("ristoranti.bin");
        for (Object obj : ristoranti) {
            if (obj instanceof Ristorante) {
                Ristorante r = (Ristorante) obj;
                if (r.getCitta() != null && r.getCitta().trim().toLowerCase().equals(cittaUtente)) {
                    return new double[] { r.getLatitudine(), r.getLongitudine() };
                }
            }
        }
        return null;
    }
    
    /**
     * Calcola la distanza (in km) tra due coordinate geografiche usando la formula dell'Haversine.
     */
    public double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio della Terra in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
