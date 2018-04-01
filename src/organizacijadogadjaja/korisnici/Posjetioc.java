/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package organizacijadogadjaja.korisnici;

/**
 *
 * @author Goran
 */
public class Posjetioc extends Osoba{
    
    String adresa;
    String telefon;
    String email;
    
    public Posjetioc(String ime,String prezime,String adresa,String telefon,String email){
        super(ime,prezime);
        this.adresa = adresa;
        this.telefon = telefon;
        this .email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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
    
}
