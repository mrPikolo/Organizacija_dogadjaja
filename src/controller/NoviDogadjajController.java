package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import organizacijadogadjaja.Dogadjaj;
import organizacijadogadjaja.OrganizacijaDogadjaja;
import static organizacijadogadjaja.korisnici.KorisnikApp.dogadjaji;
import static organizacijadogadjaja.korisnici.KorisnikApp.out;
import static organizacijadogadjaja.korisnici.KorisnikApp.in;
import organizacijadogadjaja.korisnici.Organizator;
import organizacijadogadjaja.korisnici.Predavac;
import organizacijadogadjaja.korisnici.Ucesnik;

/**
 * FXML Controller class
 *
 * @author Goran
 */
public class NoviDogadjajController implements Initializable {

    @FXML
    private DatePicker datumPocetka;

    @FXML
    private ComboBox<?> predavaci_ComboBox;

    @FXML
    private ComboBox<?> ucesnici_ComboBox;

    @FXML
    private ComboBox<?> organizatori_ComboBox;

    @FXML
    private ComboBox<String> vrstaDogadjaja_ComboBox;

    @FXML
    private Button Kreiraj_btn;

    @FXML
    private TextField vrijemeZavrsetka;

    @FXML
    private TextArea opis_textArea;

    @FXML
    private TextField nazivDogadjaja;

    @FXML
    private TextField vrijemePocetka;

    public static List<Ucesnik> ucesnici = new ArrayList<Ucesnik>();
    public static List<Predavac> predavaci = new ArrayList<Predavac>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        vrstaDogadjaja_ComboBox.getItems().clear();
        vrstaDogadjaja_ComboBox.setItems(popuniTabeluDogadjaja());
        vrstaDogadjaja_ComboBox.getSelectionModel().selectFirst();

        organizatori_ComboBox.setItems(popuniTabeluOrganizatora());
        organizatori_ComboBox.getSelectionModel().selectFirst();
        predavaci_ComboBox.setItems(popuniTabeluPredavaca());
        predavaci_ComboBox.getSelectionModel().selectFirst();
        ucesnici_ComboBox.setItems(popuniTabeluUcesnika());
        ucesnici_ComboBox.getSelectionModel().selectFirst();

        Kreiraj_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String naziv = nazivDogadjaja.getText();
                String datum = datumPocetka.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                String pocetak = vrijemePocetka.getText();
                String zavrsetak = vrijemeZavrsetka.getText();
                String organizator = organizatori_ComboBox.getValue().toString();
                
                Dogadjaj d = new Dogadjaj(naziv, datum, pocetak, zavrsetak, organizator);
                dogadjaji.add(d);

                posaljiDogadjajServeru(d);
                try {
                    if(in.readLine().equals("NOVI_DOGADJAJ_OK")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Uspjesno ste se kreirali novi događaj!", ButtonType.OK);
                alert.showAndWait();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NoviDogadjajController.class.getName()).log(Level.SEVERE, null, ex);
                }

                

                OrganizacijaDogadjaja.korisnik.zatvoriGUI("NoviDogadjaj", true);

            }

        });

    }
    
    public void posaljiDogadjajServeru(Dogadjaj d) {
        out.println("NOVI_DOGADJAJ#" + d.getNaziv()+"#"+d.getDatumPocetka()+"#"+d.getVrijemePocetka()
                +"#"+d.getVrijemeZavrsetka()+"#"+d.getOrganizator());
    }

    public static ObservableList popuniTabeluDogadjaja() {
        ObservableList allData = FXCollections.observableArrayList(PocetnaController.vrsteDogadjaja);
        return allData;
    }

    public static ObservableList popuniTabeluPredavaca() {
        ObservableList allData = FXCollections.observableArrayList(OrganizacijaDogadjaja.korisnik.predavaci);
        return allData;
    }

    public static ObservableList popuniTabeluOrganizatora() {
        ObservableList allData = FXCollections.observableArrayList(OrganizacijaDogadjaja.korisnik.organizatori);
        return allData;
    }

    public static ObservableList popuniTabeluUcesnika() {
        ObservableList allData = FXCollections.observableArrayList(OrganizacijaDogadjaja.korisnik.ucesnici);
        return allData;
    }

}
