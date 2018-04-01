package organizacijadogadjaja.korisnici;

/**
 *
 * @author Goran
 */
public class Organizator extends Osoba{   
    
    protected String telefon;
    protected String email;
    protected Napomena [] napomene;
    
    public Organizator(String ime,String prezime,String telefon,String email,Napomena[] nepomene){
        super(ime,prezime);
        this.telefon = telefon;
        this .email = email;
        this.napomene = napomene;
    }
    
    public Organizator(String ime,String prezime,String telefon,String email){
        super(ime,prezime);
        this.telefon = telefon;
        this .email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Napomena[] getNapomene() {
        return napomene;
    }

    public void setNapomene(Napomena[] napomene) {
        this.napomene = napomene;
    }
    
    public String formatZaCsv(){
        String ucesnik;
        ucesnik = this.getIme() + "," + this.getPrezime() + "," + this.getTelefon()+ "," + this.getEmail();
        return ucesnik;
    }
    
}
