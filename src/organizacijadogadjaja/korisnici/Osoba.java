/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package organizacijadogadjaja.korisnici;

import java.io.Serializable;

/**
 *
 * @author Goran
 */
public class Osoba implements Serializable{
    String ime;
    String prezime;
    
    public Osoba(String ime,String prezime){
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    
    @Override
   public String toString() {
       return ime + " " + prezime;
   }
    
}
