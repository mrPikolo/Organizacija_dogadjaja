package organizacijadogadjaja;

/**
 *
 * @author Goran
 */
public class Koncert extends Dogadjaj implements MarketinskaKampanja {

    private String izvodjac;
    private String trajanje;

    public Koncert(String naziv, String datumPocetka, String vrijemePocetka,
            String vrijemeZavrsetka, String organizator, String izvodjac, String trajanje) {
        super(naziv, datumPocetka, vrijemePocetka, vrijemeZavrsetka, organizator);
        this.izvodjac = izvodjac;
        this.trajanje = trajanje;
    }

    public String getIzvodjac() {
        return izvodjac;
    }

    public void setIzvodjac(String izvodjac) {
        this.izvodjac = izvodjac;
    }

    public String getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(String trajanje) {
        this.trajanje = trajanje;
    }

    @Override
    public void poasaljiPromotivniMaterijal() {

    }

}
