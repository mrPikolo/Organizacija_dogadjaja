package organizacijadogadjaja.korisnici;

/**
 *
 * @author Goran
 */
public class Ucesnik extends Osoba{
    
    String organizacija;
    
    public Ucesnik(String ime,String prezime,String organizacija){
        super(ime,prezime);
        this.organizacija = organizacija;
    }

    public String getOrganizacija() {
        return organizacija;
    }

    public void setOrganizacija(String organizacija) {
        this.organizacija = organizacija;
    }
    
    public String formatZaCsv(){
        String ucesnik;
        ucesnik = this.getIme() + "," + this.getPrezime() + "," + this.getOrganizacija();
        return ucesnik;
    }
    
}
