package organizacijadogadjaja;

/**
 *
 * @author Goran
 */
public class Izlozba extends Dogadjaj {

    private String tema;
    private String autor;

    public Izlozba(String naziv, String datumPocetka, String vrijemePocetka, String vrijemeZavrsetka, String organizator, String tema, String autor) {
        super(naziv, datumPocetka, vrijemePocetka, vrijemeZavrsetka, organizator);
        this.tema = tema;
        this.autor = autor;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

}
