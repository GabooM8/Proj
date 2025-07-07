/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione dei file dell'applicazione "The Knife".
 * Questa classe fornisce metodi per leggere e scrivere liste di oggetti su file,
 * sia nella directory esterna dell'utente che all'interno del JAR.
 */
public class FileMenager {

    /**
     * Percorso base per le risorse all'interno del JAR
     */
    private static final String RESOURCE_DIRECTORY_IN_JAR = "/the_knife/files/";
    /**
     * Directory per l'archiviazione esterna scrivibile dei dati
     */
    private static final File EXTERNAL_DATA_DIR = new File(System.getProperty("user.home") + File.separator + ".the_knife_app_data");

    static {
        // Assicura che la directory esterna esista
        if (!EXTERNAL_DATA_DIR.exists()) {
            if (!EXTERNAL_DATA_DIR.mkdirs()) {
                System.err.println("Impossibile creare la directory esterna dei dati: " + EXTERNAL_DATA_DIR.getAbsolutePath());
            }
        }
    }

    /**
     * @param fileName Il nome del file da cercare nella directory esterna.
     * @return Un oggetto File che rappresenta il file nella directory esterna.
     */
    public static File getExternalFile(String fileName) {
        return new File(EXTERNAL_DATA_DIR, fileName);
    }

    /** 
     * Scrive una lista di oggetti su un file nella directory esterna.
     * Se il file non esiste, lo crea. Se esiste, sovrascrive il contenuto.
     * 
     * @param list La lista di oggetti da scrivere.
     * @param fileName Il nome del file.
     */
    public static void addToFile(List<Object> list, String fileName) {
        File externalFile = getExternalFile(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(externalFile))) {
            out.writeObject(list);
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura sul file esterno: " + externalFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    /** 
     * Legge una lista di oggetti da un file.
     * Cerca prima nella directory esterna. Se non trovato o illeggibile, 
     * tenta di caricarlo dalle risorse del JAR come dato iniziale, 
     * copiandolo poi nella directory esterna per usi futuri.
     * 
     * @param fileName Il nome del file da leggere.
     * @return La lista di oggetti letta dal file, o una lista vuota in caso di errore o file non trovato.
     */
    public static List<Object> readFromFile(String fileName) {
        File externalFile = getExternalFile(fileName);
        System.out.println("DEBUG: Cerco il file esterno: " + externalFile.getAbsolutePath());

        if (externalFile.exists()) {
            System.out.println("DEBUG: File esterno trovato, leggo da: " + externalFile.getAbsolutePath());
            try (ObjectInputStream inp = new ObjectInputStream(new FileInputStream(externalFile))) {
                List<Object> data = (List<Object>) inp.readObject();
                System.out.println("DEBUG: Letti " + data.size() + " elementi dal file esterno");
                return data;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Errore durante la lettura dal file esterno: " + externalFile.getAbsolutePath() + 
                                   ". Tentativo di caricamento dal JAR. Errore: " + e.getMessage());
                // Opzionale: eliminare o rinominare il file esterno corrotto qui.
                // externalFile.delete(); 
            }
        } else {
            System.out.println("DEBUG: File esterno non trovato, cerco nel JAR");
        }

        // Se non trovato nell'archivio esterno o errore di lettura, tenta il caricamento dalle risorse JAR
        String resourcePath = RESOURCE_DIRECTORY_IN_JAR + fileName;
        System.out.println("DEBUG: Cerco risorsa nel JAR: " + resourcePath);
        try (InputStream resourceStream = FileMenager.class.getResourceAsStream(resourcePath)) {
            if (resourceStream != null) {
                System.out.println("DEBUG: Risorsa trovata nel JAR, carico dati iniziali");
                try (ObjectInputStream ois = new ObjectInputStream(resourceStream)) {
                    List<Object> dataFromJar = (List<Object>) ois.readObject();
                    System.out.println("DEBUG: Caricati " + dataFromJar.size() + " elementi dal JAR");
                    
                    // Tenta di salvare questi dati iniziali nel file esterno per modifiche future
                    addToFile(dataFromJar, fileName); 
                    System.out.println("DEBUG: Dati salvati nel file esterno: " + externalFile.getAbsolutePath());
                    return dataFromJar;
                }
            } else {
                System.err.println("DEBUG: Risorsa non trovata nel JAR: " + resourcePath + 
                                   " e file esterno non trovato/leggibile: " + externalFile.getAbsolutePath());
                return new ArrayList<>(); // File non trovato
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la lettura dalla risorsa JAR: " + resourcePath + 
                               " o durante il salvataggio su file esterno. Errore: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
