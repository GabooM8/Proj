package the_knife;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileMenager {

    /*
     * Classe per la gestione dei file.
     * Permette di aggiungere e leggere liste di oggetti da file.
     */

    private static final String CARTELLA_FILE = "the_knife/src/main/java/the_knife/files/"; // Percorso della cartella dei file

    /** 
     * @param list
     * @param fileName
     */
    public static void addToFile(List<Object> list, String fileName) {
        /*
         * Aggiunge una lista di oggetti a un file.
         * Se il file non esiste, lo crea.
         * Se esiste, sovrascrive il contenuto.
         */
        String filePath = CARTELLA_FILE + fileName;
        File dir = new File(CARTELLA_FILE);
        if (!dir.exists()) {
            dir.mkdirs(); 
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 
     * @param fileName
     * @return List<Object>
     */
    public static List<Object> readFromFile(String fileName) {
        /*
         * Legge una lista di oggetti da un file.
         * Se il file non esiste, restituisce una lista vuota.
         */
        String filePath = CARTELLA_FILE + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Object>) inp.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
