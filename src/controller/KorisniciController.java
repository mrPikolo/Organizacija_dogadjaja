package controller;

import static controller.NoviDogadjajController.predavaci;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import organizacijadogadjaja.Dogadjaj;
import organizacijadogadjaja.OrganizacijaDogadjaja;
import static organizacijadogadjaja.korisnici.KorisnikApp.out;
import organizacijadogadjaja.korisnici.Organizator;
import organizacijadogadjaja.korisnici.Osoba;
import organizacijadogadjaja.korisnici.Predavac;
import organizacijadogadjaja.korisnici.Ucesnik;

/**
 * FXML Controller class
 *
 * @author Goran
 */
public class KorisniciController implements Initializable {

    @FXML
    private ComboBox<?> vrstaKorisnika_ComboBox;

    @FXML
    private ListView<?> pregledKorisnika_ListView;

    @FXML
    private Button zavrsi_btn;

    @FXML
    private Button dodaj_btn;

    @FXML
    private Button obrisi_btn;

    @FXML
    private TextField ime_textF;

    @FXML
    private Label labela1;

    @FXML
    private Label vrstaKorisnika_labela;

    @FXML
    private TextArea napomena_textA;

    @FXML
    private TextField prezime_textF;

    @FXML
    private Label labela3;

    @FXML
    private Label labela2;

    @FXML
    private TextField textField_3;

    @FXML
    private TextField textField_2;

    @FXML
    private TextField textField_1;

    @FXML
    private Button dodajNapomenu_btn;

    public static List<String> vrstaKorisnika = new ArrayList<String>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vrstaKorisnika.add("Organizator");
        vrstaKorisnika.add("Učesnik");
        vrstaKorisnika.add("Predavač");

        vrstaKorisnika_ComboBox.setItems(popuniVrsteKorisnika());
        vrstaKorisnika_ComboBox.getSelectionModel().selectFirst();
        popuniZaOrganizatora();

        vrstaKorisnika_ComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (vrstaKorisnika_ComboBox.getValue().toString().equals("Organizator")) {
                    popuniZaOrganizatora();
                } else if (vrstaKorisnika_ComboBox.getValue().equals("Učesnik")) {
                    popuniZaUcesnika();
                } else if (vrstaKorisnika_ComboBox.getValue().equals("Predavač")) {
                    popuniZaPredavaca();
                }
            }

        });
        
        zavrsi_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrganizacijaDogadjaja.korisnik.zatvoriGUI("Korisnici", true);
            }
        });
        
        dodaj_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = pregledKorisnika_ListView.getEditingIndex();
                if (vrstaKorisnika_ComboBox.getValue().toString().equals("Organizator")) {
                    String ime = ime_textF.getText();
                    String prezime = prezime_textF.getText();
                    String telefon = textField_1.getText();
                    String email = textField_2.getText();
                    Organizator o = new Organizator(ime,prezime,telefon,email);
                    OrganizacijaDogadjaja.korisnik.organizatori.add(o);
                    //dodamo na serveru 
                    out.println("DODAJ_" + "ORGANIZATORA" +"#" + o.getIme()+"#" + o.getPrezime()+"#" + o.getTelefon()+"#" + o.getEmail());
                } else if (vrstaKorisnika_ComboBox.getValue().equals("Učesnik")) {
                    String ime = ime_textF.getText();
                    String prezime = prezime_textF.getText();
                    String organizacija = textField_1.getText();
                    Ucesnik u = new Ucesnik(ime,prezime,organizacija);
                    OrganizacijaDogadjaja.korisnik.ucesnici.add(u);
                    //dodamo na serveru 
                    out.println("DODAJ_" + "UCESNIKA" +"#" + u.getIme()+"#" + u.getPrezime()+"#" + u.getOrganizacija());
                } else if (vrstaKorisnika_ComboBox.getValue().equals("Predavač")) {
                    String ime = ime_textF.getText();
                    String prezime = prezime_textF.getText();
                    String organizacija = textField_1.getText();
                    Predavac p = new Predavac(ime,prezime,organizacija);
                    OrganizacijaDogadjaja.korisnik.predavaci.add(p);
                    //dodamo na serveru 
                    out.println("DODAJ_" + "PREDAVACA" +"#" + p.getIme()+"#" + p.getPrezime()+"#" + p.getOrganizacija());
                }
                OrganizacijaDogadjaja.korisnik.zatvoriGUI("Korisnici", true);
            }
        });
        
        obrisi_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Osoba selectedItem = null;
                String vrstaKorisnika = "";
                if(pregledKorisnika_ListView.getSelectionModel().getSelectedItem() instanceof Organizator){
                    selectedItem = (Organizator) pregledKorisnika_ListView.getSelectionModel().getSelectedItem();
                    vrstaKorisnika = "Organizator";
                } else if(pregledKorisnika_ListView.getSelectionModel().getSelectedItem() instanceof Ucesnik){
                    selectedItem = (Ucesnik) pregledKorisnika_ListView.getSelectionModel().getSelectedItem();
                    vrstaKorisnika = "Ucesnik";
                } else if(pregledKorisnika_ListView.getSelectionModel().getSelectedItem() instanceof Predavac){
                    selectedItem = (Predavac) pregledKorisnika_ListView.getSelectionModel().getSelectedItem();
                    vrstaKorisnika = "Predavac";
                }
                

                 
                //obrisemo na serveru dogadjaj
                out.println("OBRISI#" + vrstaKorisnika +"#" + selectedItem.getIme()+"#" + selectedItem.getPrezime());
                //obrisemo iz tabele dogadjaja
                pregledKorisnika_ListView.getItems().remove(selectedItem);

            }
        });

    }

    public static ObservableList popuniVrsteKorisnika() {
        ObservableList allData = FXCollections.observableArrayList(vrstaKorisnika);
        return allData;
    }

    private void popuniZaOrganizatora() {
        labela1.setVisible(true);
        labela2.setVisible(true);
        labela3.setVisible(true);
        labela1.setText("Telefon");
        labela2.setText("Email");
        labela3.setText("Napomena");
        vrstaKorisnika_labela.setText(" organizatora");
        textField_2.setVisible(true);
        napomena_textA.setVisible(true);
        dodajNapomenu_btn.setVisible(true);
        pregledKorisnika_ListView.setItems(NoviDogadjajController.popuniTabeluOrganizatora());
    }

    private void popuniZaUcesnika() {
        labela1.setText("Organizacija");
        labela2.setVisible(false);
        labela3.setVisible(false);
        vrstaKorisnika_labela.setText(" učesnika");
        textField_2.setVisible(false);
        napomena_textA.setVisible(false);
        dodajNapomenu_btn.setVisible(false);
        pregledKorisnika_ListView.setItems(NoviDogadjajController.popuniTabeluUcesnika());
    }

    private void popuniZaPredavaca() {
        labela1.setText("Organizacija");
        labela2.setVisible(false);
        labela3.setVisible(false);
        vrstaKorisnika_labela.setText(" predavača");
        textField_2.setVisible(false);
        napomena_textA.setVisible(false);
        dodajNapomenu_btn.setVisible(false);
        pregledKorisnika_ListView.setItems(NoviDogadjajController.popuniTabeluPredavaca());
    }
}
