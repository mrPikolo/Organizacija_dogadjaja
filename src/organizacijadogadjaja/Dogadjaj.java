package organizacijadogadjaja;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Goran
 */
public class Dogadjaj implements Serializable {

    protected String naziv;
    protected String datumPocetka;

    protected String vrijemePocetka;
    protected String vrijemeZavrsetka;
    protected String opis;
    protected String organizator;
    protected String ucesnik;
    private Date pocetak;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. hh:mm");

    public Dogadjaj(String naziv, String datumPocetka, String vrijemePocetka,
            String vrijemeZavrsetka, String organizator) {
        try {
            this.naziv = naziv;
            this.datumPocetka = datumPocetka;
            this.pocetak = sdf.parse(datumPocetka + " " + vrijemePocetka);
            // this.pocetak = sdf.parse(null);
            this.vrijemePocetka = vrijemePocetka;
            this.vrijemeZavrsetka = vrijemeZavrsetka;
            this.organizator = organizator;
        } catch (ParseException ex) {
            Logger.getLogger(Dogadjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(String datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOrganizator() {
        return organizator;
    }

    public void setOrganizator(String organizator) {
        this.organizator = organizator;
    }

    public String getUcesnik() {
        return ucesnik;
    }

    public void setUcesnik(String ucesnik) {
        this.ucesnik = ucesnik;
    }

    public String getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(String vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public String getVrijemeZavrsetka() {
        return vrijemeZavrsetka;
    }

    public void setVrijemeZavrsetka(String vrijemeZavrsetka) {
        this.vrijemeZavrsetka = vrijemeZavrsetka;
    }

    public Date getPocetak() {
        return pocetak;
    }

    public void setPocetak(Date pocetak) {
        this.pocetak = pocetak;
    }

    @Override
    public String toString() {
        return naziv + "," + datumPocetka + "," + vrijemePocetka + "," + vrijemeZavrsetka + "," + organizator;
    }

    public String formatZaCsv() {
        String dogadjaj;
        dogadjaj = this.getNaziv() + "," + this.getDatumPocetka() + "," + this.getVrijemePocetka() + ","
                + this.getVrijemeZavrsetka() + "," + this.getOrganizator();
        return dogadjaj;
    }

}
