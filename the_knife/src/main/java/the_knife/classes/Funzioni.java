package the_knife.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import the_knife.FileMenager;

public class Funzioni {
    /** 
     * @param inputSearchBar
     * @param inputLocationFilter
     * @param fasciaPrezzoFiltro
     * @param numStelleFiltro
     * @param cucinaFiltro
     * @param deliveryFiltro
     * @param prenotazioneFiltro
     * @return List<Ristorante>
     */
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

    /** 
     * @param idRistorante
     * @param idUtente
     */
    public void addPreferito(int idRistorante, int idUtente) {
        /*
         * Funzione che permette di aggiungere un ristorante ai preferiti di un utente.
        */
        String filename = "Utenti.bin";
        List<Object> utenti = FileMenager.readFromFile(filename); // Legge gli utenti dal file binario
        for(Object obj : utenti) {
            //prende ò'utente e controlla se l'id corrisponde a quello passato come parametro
            Utente utente = (Utente) obj;
            if (utente.getId() == idUtente) {
                utente.addRistorante(idRistorante); // Aggiunge il ristorante alla lista dei preferiti dell'utente
            }
        }
    }

    /**
     * Visualizza i ristoranti preferiti di un utente.
     * @param idUtente L'ID dell'utente di cui visualizzare i preferiti.
     * @return Una lista di ristoranti preferiti dall'utente.
     */
    public List<Ristorante> visualizzaPreferiti(int idUtente) {
        String filename= "Utenti.bin";
        Utente utente= null;
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) { // Itera attraverso gli utenti per trovare quello con l'ID specificato
            Utente u = (Utente) obj;
            System.out.println(u.getId() + " " + idUtente);
            if (u.getId() == idUtente) { // Controlla se l'ID dell'utente corrisponde a quello passato come parametro
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
        for (Object obj : ristoranti) { // Itera attraverso i ristoranti per trovare quelli preferiti dall'utente
            Ristorante ristorante = (Ristorante) obj;
            if (utente.getRistoranti().contains(ristorante.getId())) { // Controlla se l'ID del ristorante è nella lista dei preferiti dell'utente
                preferiti.add(ristorante);
            }
        }
        return preferiti;
    }

    /**
     * Elimina un ristorante dai preferiti di un utente.
     * @param idRistorante L'ID del ristorante da eliminare dai preferiti.
     * @param idUtente L'ID dell'utente da cui eliminare il ristorante.
     */
    public void DeletePreferito(int idRistorante, int idUtente) {
        String filename = "Utenti.bin";
        List<Object> utenti = FileMenager.readFromFile(filename);
        for(Object obj : utenti) { // Itera attraverso gli utenti per trovare quello con l'ID specificato
            Utente utente = (Utente) obj;
            if (utente.getId() == idUtente) { // Controlla se l'ID dell'utente corrisponde a quello passato come parametro
                List<Integer> ristoranti = utente.getRistoranti();
                if(ristoranti.contains(idRistorante)) { // Controlla se l'ID del ristorante è nella lista dei preferiti dell'utente
                    ristoranti.remove(idRistorante);
                }
                break;
            }
        }
        FileMenager.addToFile(utenti, filename);
    }

    /** 
     * @return List<Utente>
     */
    public List<Utente> getUtenti() {
        // Legge tutti gli utenti dal file binario e li restituisce come lista di Utente
        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }
        return utenti;
    }

    /** 
     * @return List<Recensione>
     */
    public List<Recensione> getRecensioni() {
        // Legge tutte le recensioni dal file binario e le restituisce come lista di Recensione
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

    /**
     * Elimina una recensione e tutte le sue risposte collegate.
     * 
     * @param idRecensione L'ID della recensione da eliminare.
     */
    public void eliminaRecensione(int idRecensione) {
        // 1. Rimuovi la recensione dal file recensioni.bin
        List<?> objects = FileMenager.readFromFile("recensioni.bin");
        List<Recensione> recensioni = new ArrayList<>();
        Recensione recensioneDaEliminare = null;
        
        for (Object obj : objects) {
            if (obj instanceof Recensione) {
                Recensione rec = (Recensione) obj;
                if (rec.getId() == idRecensione) {
                    recensioneDaEliminare = rec;
                } else {
                    recensioni.add(rec);
                }
            }
        }
        
        if (recensioneDaEliminare == null) {
            System.out.println("Recensione con ID " + idRecensione + " non trovata.");
            return;
        }
        
        // Salva il file recensioni aggiornato
        List<Object> recensioniObj = new ArrayList<>(recensioni);
        FileMenager.addToFile(recensioniObj, "recensioni.bin");
        
        // 2. Rimuovi il collegamento dalla lista recensioni dell'utente
        List<?> utentiObjects = FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : utentiObjects) {
            if (obj instanceof Utente) {
                Utente utente = (Utente) obj;
                if (utente.getId() == recensioneDaEliminare.getIdUtente()) {
                    utente.getRecensioni().remove(Integer.valueOf(idRecensione));
                }
                utenti.add(utente);
            }
        }
        
        // Salva il file utenti aggiornato
        List<Object> utentiObj = new ArrayList<>(utenti);
        FileMenager.addToFile(utentiObj, "Utenti.bin");
        
        // 3. Rimuovi il collegamento dalla lista recensioni del ristorante e ricalcola le stelle
        List<?> ristorantiObjects = FileMenager.readFromFile("ristoranti.bin");
        List<Ristorante> ristoranti = new ArrayList<>();
        for (Object obj : ristorantiObjects) {
            if (obj instanceof Ristorante) {
                Ristorante ristorante = (Ristorante) obj;
                if (ristorante.getRecensioni().contains(idRecensione)) {
                    ristorante.getRecensioni().remove(Integer.valueOf(idRecensione));
                    
                    // Ricalcola le stelle del ristorante
                    ricalcolaStelle(ristorante);
                }
                ristoranti.add(ristorante);
            }
        }
        
        // Salva il file ristoranti aggiornato
        List<Object> ristorantiObj = new ArrayList<>(ristoranti);
        FileMenager.addToFile(ristorantiObj, "ristoranti.bin");
        
        // 4. Elimina tutte le sottorecensioni (risposte) collegate a questa recensione
        List<?> risposteObjects = FileMenager.readFromFile("risposte.bin");
        List<SottoRecensione> risposte = new ArrayList<>();
        for (Object obj : risposteObjects) {
            if (obj instanceof SottoRecensione) {
                SottoRecensione risposta = (SottoRecensione) obj;
                // Mantieni solo le risposte che NON sono collegate alla recensione eliminata
                if (risposta.getIdPadre() != idRecensione) {
                    risposte.add(risposta);
                }
            }
        }
        
        // Salva il file risposte aggiornato
        List<Object> risposteObj = new ArrayList<>(risposte);
        FileMenager.addToFile(risposteObj, "risposte.bin");
        
        System.out.println("Recensione con ID " + idRecensione + " eliminata con successo insieme a tutte le sue risposte.");
    }

    /**
     * Metodo di supporto per ricalcolare le stelle di un ristorante
     */
    private void ricalcolaStelle(Ristorante ristorante) {
        if (ristorante.getRecensioni().isEmpty()) {
            ristorante.setNumStelle(0);
            return;
        }
        
        List<Recensione> recensioniRistorante = new ArrayList<>();
        List<Recensione> tutteRecensioni = getRecensioni();
        
        for (Recensione r : tutteRecensioni) {
            if (ristorante.getRecensioni().contains(r.getId())) {
                recensioniRistorante.add(r);
            }
        }
        
        if (recensioniRistorante.isEmpty()) {
            ristorante.setNumStelle(0);
        } else {
            int sum = 0;
            for (Recensione r : recensioniRistorante) {
                sum += r.getNumStelle();
            }
            ristorante.setNumStelle(sum / recensioniRistorante.size());
        }
    }

    /**
     * Modifica una recensione esistente e ricalcola le stelle del ristorante associato.
     * 
     * @param idRecensione L'ID della recensione da modificare.
     * @param nuovoTesto Il nuovo testo della recensione.
     * @param nuoveStelle Il nuovo numero di stelle della recensione.
     */
    public void modificaRecensione(int idRecensione, String nuovoTesto, int nuoveStelle) {
        // 1. Modifica la recensione nel file recensioni.bin
        List<?> objects = FileMenager.readFromFile("recensioni.bin");
        List<Recensione> recensioni = new ArrayList<>();
        boolean recensioneTrovata = false;
        
        for (Object obj : objects) {
            if (obj instanceof Recensione) {
                Recensione rec = (Recensione) obj;
                if (rec.getId() == idRecensione) {
                    // Crea una nuova recensione con i dati modificati
                    Recensione recensioneModificata = new Recensione(rec.getId(), nuoveStelle, nuovoTesto, rec.getIdUtente());
                    recensioni.add(recensioneModificata);
                    recensioneTrovata = true;
                } else {
                    recensioni.add(rec);
                }
            }
        }
        
        if (!recensioneTrovata) {
            System.out.println("Recensione con ID " + idRecensione + " non trovata.");
            return;
        }
        
        // Salva il file recensioni aggiornato
        List<Object> recensioniObj = new ArrayList<>(recensioni);
        FileMenager.addToFile(recensioniObj, "recensioni.bin");
        
        // 2. Ricalcola le stelle di tutti i ristoranti che hanno questa recensione
        List<?> ristorantiObjects = FileMenager.readFromFile("ristoranti.bin");
        List<Ristorante> ristoranti = new ArrayList<>();
        for (Object obj : ristorantiObjects) {
            if (obj instanceof Ristorante) {
                Ristorante ristorante = (Ristorante) obj;
                if (ristorante.getRecensioni().contains(idRecensione)) {
                    // Ricalcola le stelle del ristorante
                    ricalcolaStelle(ristorante);
                }
                ristoranti.add(ristorante);
            }
        }
        
        // Salva il file ristoranti aggiornato
        List<Object> ristorantiObj = new ArrayList<>(ristoranti);
        FileMenager.addToFile(ristorantiObj, "ristoranti.bin");
        
        System.out.println("Recensione con ID " + idRecensione + " modificata con successo.");
    }
}
