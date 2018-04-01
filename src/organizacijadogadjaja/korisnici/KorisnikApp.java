package organizacijadogadjaja.korisnici;

import static controller.NoviDogadjajController.predavaci;
import controller.PocetnaController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import organizacijadogadjaja.Dogadjaj;
import organizacijadogadjaja.OrganizacijaDogadjaja;

/**
 *
 * @author Goran
 */
public class KorisnikApp {

    public static final int PORT = 9000;
    private static InetAddress addr;
    public static Socket sock;
    public static BufferedReader in;
    public static PrintWriter out;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;

    private Stage pocetnaStage = new Stage();
    private Stage noviDogadjajStage = new Stage();
    private Stage korisniciStage = new Stage();

    public  List<Organizator> organizatori = new ArrayList<Organizator>();
    public  static List<Dogadjaj> dogadjaji = new ArrayList<Dogadjaj>();
    public  List<Predavac> predavaci = new ArrayList<Predavac>();
    public  List<Ucesnik> ucesnici = new ArrayList<Ucesnik>();

    public KorisnikApp() {
        try {
            // odredi adresu racunara (servera) sa kojim se povezujemo
            addr = InetAddress.getByName("127.0.0.1");

            // otvori socket prema drugom racunaru, tj. povezi se na server
            sock = new Socket(addr, PORT);

            // inicijalizuj ulazni stream
            in = new BufferedReader(
                    new InputStreamReader(
                            sock.getInputStream()));

            // inicijalizuj izlazni stream
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    sock.getOutputStream())), true);
            oos = new ObjectOutputStream(sock.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());

            sviDogadjajiSaServera();
            predavaciSaServera();
            ucesniciSaServera();
            organizatoriSaServera();
            

            prikaziGUI("Pocetna", false);

        } catch (UnknownHostException ex) {
            Logger.getLogger(KorisnikApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KorisnikApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sviDogadjajiSaServera() {
        try {
            out.println("SVI_DOGADJAJI");
            dogadjaji = (ArrayList<Dogadjaj>) ois.readObject();  
            

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private static Comparator<Dogadjaj> COMPARATOR = new Comparator<Dogadjaj>() {
        public int compare(Dogadjaj d1, Dogadjaj d2) {
            return d1.getPocetak().compareTo(d2.getPocetak());
        }
    };
    
    public void predavaciSaServera() {
        try {
            out.println("PREDAVACI");
            predavaci = (ArrayList<Predavac>) ois.readObject();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void organizatoriSaServera() {
        try {
            out.println("ORGANIZATORI");
            organizatori = (ArrayList<Organizator>) ois.readObject();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void ucesniciSaServera() {
        try {
            out.println("UCESNICI");
            ucesnici = (ArrayList<Ucesnik>) ois.readObject();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void prikaziGUI(final String naziv, boolean wait) {
        try {
            Stage stage = vratiStage(naziv);
            stage.setResizable(false);
            String resurs = "/fxml/" + naziv + ".fxml";
            Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(resurs));
            Scene myScene = new Scene(myPane);

            stage.setScene(myScene);
            stage.setTitle(naziv);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(final WindowEvent windowEvent) {
                    if (naziv.equals("Pocetna")) {
                        zatvoriGUI(naziv, true);
                    } else if (naziv.equals("NoviDogadjaj")) {
                        zatvoriGUI(naziv, true);
                    }
                }
            });
            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage vratiStage(String naziv) {
        if (naziv.equals("Pocetna")) {
            return pocetnaStage;
        } else if (naziv.equals("NoviDogadjaj")) {
            return noviDogadjajStage;
        } else if (naziv.equals("Korisnici")) {
            return korisniciStage;
        }
        return null;
    }

    public void zatvoriGUI(String naziv, boolean kraj) {
        Stage stage = vratiStage(naziv);
        stage.close();
    }

    public void closeIO() {

        try {
            out.println("KRAJ");
            out.close();
            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
