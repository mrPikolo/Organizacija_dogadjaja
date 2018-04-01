package organizacijadogadjaja.korisnici;

/**
 *
 * @author Goran
 */
public class Predavac extends Ucesnik{
    
    public Predavac(String ime, String prezime, String organizacija) {
        super(ime, prezime, organizacija);
    }
    
    @Override
    public String toString(){
        return super.toString() + ", " + getOrganizacija();
    }
    
}
