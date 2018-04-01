package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import organizacijadogadjaja.Dogadjaj;
import organizacijadogadjaja.OrganizacijaDogadjaja;
import organizacijadogadjaja.korisnici.KorisnikApp;
import static organizacijadogadjaja.korisnici.KorisnikApp.out;
import static organizacijadogadjaja.korisnici.KorisnikApp.dogadjaji;
import static organizacijadogadjaja.korisnici.KorisnikApp.in;
import organizacijadogadjaja.korisnici.Organizator;
import organizacijadogadjaja.korisnici.Predavac;
import organizacijadogadjaja.korisnici.Ucesnik;
import server.Server;

/**
 * FXML Controller class
 *
 * @author Goran
 */
public class PocetnaController implements Initializable {

    @FXML // fx:id="Marketing_btn"
    private Button Marketing_btn; // Value injected by FXMLLoader

    @FXML // fx:id="NoviDogadjaj_btn"
    private Button NoviDogadjaj_btn; // Value injected by FXMLLoader

    @FXML // fx:id="Obrisi_btn"
    private Button Obrisi_btn; // Value injected by FXMLLoader

    @FXML
    private TableView<Dogadjaj> tabelaDogadjaja;

    @FXML
    private TableColumn<Dogadjaj, String> kolonaNaziv;

    @FXML
    private TableColumn<Dogadjaj, String> kolonaVrijemePocetka;

    @FXML
    private TableColumn<Dogadjaj, String> kolonaDatumPocetka;

    @FXML
    private TableColumn<Dogadjaj, String> kolonaVrijemeZavrsetka;

    @FXML
    private TableColumn<Dogadjaj, String> kolonaDatumZavrsetka;

    @FXML
    private MenuButton Preuzimanja_mbtn;

    @FXML
    private MenuItem PreuzmiDogadjaj_mi;

    @FXML
    private MenuItem PreuzmiUcesnike_mi;

    @FXML
    private MenuItem PreuzmiOrganizatore_mi;

    @FXML
    private Button Zavrsi_btn;

    @FXML
    private Button Korisnici_btn;
    
    @FXML
    public static TextField marketing_textF;

    public static List<String> vrsteDogadjaja = new ArrayList<String>();
    public static List<Organizator> organizatoriDogadjaja = new ArrayList<Organizator>();
    public static List<Ucesnik> ucesnici = new ArrayList<Ucesnik>();
    public static List<Predavac> predavaci = new ArrayList<Predavac>();
    //public KorisnikApp korisnik;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        popuniVrsteDogadjajaListu();
        marketing_textF = new TextField();
        marketing_textF.setText("false");      
        
       /* marketing_textF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(marketing_textF.getText().equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Završena je marketinška kampanja!", ButtonType.OK);
                alert.showAndWait();
                marketing_textF.setText("false");
                }
            }
        });
       */
        Korisnici_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrganizacijaDogadjaja.korisnik.prikaziGUI("Korisnici", false);
            }
        });

        NoviDogadjaj_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrganizacijaDogadjaja.korisnik.prikaziGUI("NoviDogadjaj", false);
            }
        });

        Marketing_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                out.println("MARKETING");
                try {
                    if (in.readLine().equals("MARKETING_ZAVRSEN")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "Završena je marketinška kampanja!", ButtonType.OK);
                        alert.showAndWait();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NoviDogadjajController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        Obrisi_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Dogadjaj selectedItem = tabelaDogadjaja.getSelectionModel().getSelectedItem();
                //obrisemo na serveru dogadjaj
                out.println("OBRISI_DOGADJAJ#" + selectedItem.getNaziv());
                //obrisemo iz tabele dogadjaja
                tabelaDogadjaja.getItems().remove(selectedItem);

            }
        });

        Zavrsi_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrganizacijaDogadjaja.korisnik.closeIO();
                OrganizacijaDogadjaja.korisnik.zatvoriGUI("Pocetna", true);
            }
        });

        PreuzmiDogadjaj_mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                out.println("PREUZIMANJE_DOGADJAJA");
                primiFajl(new File("src/organizacijadogadjaja/korisnici/preuzimanja/dogadjaji.csv"));
            }
        });

        PreuzmiUcesnike_mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                out.println("PREUZIMANJE_UCESNIKA");
                primiFajl(new File("src/organizacijadogadjaja/korisnici/preuzimanja/ucesnici.csv"));
            }
        });

        PreuzmiOrganizatore_mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                out.println("PREUZIMANJE_ORGANIZATORA");
                primiFajl(new File("src/organizacijadogadjaja/korisnici/preuzimanja/organizatori.csv"));
            }
        });

        Preuzimanja_mbtn = new MenuButton("Preuzimanja", Obrisi_btn, new MenuItem("Događaj"), new MenuItem("Organizator"), new MenuItem("Ucesnike"));

        // kolona datum pocetka sortirana po opadajucem redosledu
        kolonaDatumPocetka.setSortType(TableColumn.SortType.DESCENDING);

        kolonaNaziv.setCellValueFactory(new PropertyValueFactory<Dogadjaj, String>("naziv"));
        kolonaDatumPocetka.setCellValueFactory(new PropertyValueFactory<Dogadjaj, String>("datumPocetka"));
        kolonaVrijemePocetka.setCellValueFactory(new PropertyValueFactory<Dogadjaj, String>("vrijemePocetka"));
        kolonaVrijemeZavrsetka.setCellValueFactory(new PropertyValueFactory<Dogadjaj, String>("vrijemeZavrsetka"));
        // popunimo tabelu sa listom dogadjaja
        tabelaDogadjaja.getItems().setAll(popuniTabeluDogadjaja());

    }

    public static ObservableList popuniTabeluDogadjaja() {

        // sortiramo listu dogadjaja po datumu pocetka
        Collections.sort(dogadjaji, COMPARATOR);
        ObservableList allData = FXCollections.observableArrayList(dogadjaji);
        return allData;
    }

    // poredjenje dva dogadjaja po datumu pocetka
    private static Comparator<Dogadjaj> COMPARATOR = new Comparator<Dogadjaj>() {
        public int compare(Dogadjaj d1, Dogadjaj d2) {
            return d1.getPocetak().compareTo(d2.getPocetak());
        }
    };

    private void popuniVrsteDogadjajaListu() {
        vrsteDogadjaja.add("Promocija");
        vrsteDogadjaja.add("Predavanje");
        vrsteDogadjaja.add("Koncert");
        vrsteDogadjaja.add("Izlozba");
    }

    public void primiFajl(File putanjaFajla) {
        try {
            long duzinaLong = (long) OrganizacijaDogadjaja.korisnik.ois.readObject();
            int duzina = (int) duzinaLong;
            int kontrolnaDuzina = 0, flag = 0;
            byte[] buffer = new byte[2 * 1024 * 1024];
            OutputStream fajl = new FileOutputStream(putanjaFajla);
            while ((kontrolnaDuzina = OrganizacijaDogadjaja.korisnik.ois.read(buffer)) > 0) {
                fajl.write(buffer, 0, kontrolnaDuzina);
                flag += kontrolnaDuzina;
                if (duzina <= flag) {
                    break;
                }
            }
            System.out.println("Preuzimanje zavrseno...");
            fajl.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
}
