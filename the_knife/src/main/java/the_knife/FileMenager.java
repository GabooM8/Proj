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

    private static final String CARTELLA_FILE = "the_knife/src/main/java/the_knife/files/";

    public static void addToFile(List<Object> list, String fileName) {
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

    public static List<Object> readFromFile(String fileName) {
        String filePath = CARTELLA_FILE + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File non trovato: ");
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
