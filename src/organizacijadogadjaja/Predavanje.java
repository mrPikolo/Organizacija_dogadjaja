package organizacijadogadjaja;

/**
 *
 * @author Goran
 */
public class Predavanje extends Dogadjaj {

    private String tema;
    private String predavac;
    private String sadrzaj;

    public Predavanje(String naziv, String datumPocetka, String vrijemePocetka,
            String vrijemeZavrsetka, String organizator, String tema, String predavac) {
        super(naziv, datumPocetka, vrijemePocetka, vrijemeZavrsetka, organizator);
        this.tema = tema;
        this.predavac = predavac;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getPredavac() {
        return predavac;
    }

    public void setPredavac(String predavac) {
        this.predavac = predavac;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

}
