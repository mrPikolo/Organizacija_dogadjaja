package organizacijadogadjaja;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import organizacijadogadjaja.korisnici.KorisnikApp;

/**
 *
 * @author Goran
 */
public class OrganizacijaDogadjaja extends Application {
    
    public static KorisnikApp korisnik;
    
    @Override
    public void start(Stage primaryStage) {
        
        korisnik = new KorisnikApp();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
