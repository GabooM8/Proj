package the_knife;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import the_knife.classes.Ristorante;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login", null), 900, 600); // Modificato per coerenza con loadFXML
        stage.setScene(scene);
        stage.setTitle("The_Knife");

        stage.setResizable(false);
        stage.setMaximized(false);
        stage.centerOnScreen();

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml, null)); // Passa null per i dati
    }

    // Nuovo metodo sovraccaricato per passare dati al controller della nuova scena
    static void setRoot(String fxml, Object data) throws IOException {
        scene.setRoot(loadFXML(fxml, data));
    }

    private static Parent loadFXML(String fxml, Object data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        
        if (data != null) {
            Object controller = fxmlLoader.getController();
            if (controller instanceof RistoranteController && data instanceof Ristorante) {
                ((RistoranteController) controller).initData((Ristorante) data);
            }
        }
        return parent;
    }

    public static void main(String[] args) {
        
        launch();
    }

}