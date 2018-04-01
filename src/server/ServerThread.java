package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import organizacijadogadjaja.Dogadjaj;
import organizacijadogadjaja.Koncert;
import organizacijadogadjaja.Promocija;
import organizacijadogadjaja.korisnici.Organizator;
import organizacijadogadjaja.korisnici.Posjetioc;
import organizacijadogadjaja.korisnici.Predavac;
import organizacijadogadjaja.korisnici.Ucesnik;

/**
 *
 * @author Goran
 */
public class ServerThread extends Thread {

    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public boolean primajZahtjeve = true;

    public ServerThread(Socket sock) {
        this.sock = sock;
        try {
            // inicijalizuj ulazni stream
            in = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));

            // inicijalizuj izlazni stream
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    sock.getOutputStream())), true);
            oos = new ObjectOutputStream(sock.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        start();
    }

    public void run() {
        try {
            while (primajZahtjeve) {

                String request = in.readLine();
                System.out.println("Primljen je zahtjev od korisnika: " + request);

                if (request.startsWith("SVI_DOGADJAJI")) {
                    oos.writeObject(Server.dogadjaji);
                }
                
                if (request.startsWith("PREUZIMANJE_DOGADJAJA")) {
                    Server.kreirajDogadjajeCsv();
                    posaljiFajlKlijentu(new File(".\\src\\server\\podaci\\listaDogadjaja.csv"));
                }
                
                if (request.startsWith("PREUZIMANJE_UCESNIKA")) {
                    Server.kreirajUcesnikeCsv();
                    posaljiFajlKlijentu(new File(".\\src\\server\\podaci\\listaUcesnika.csv"));
                }
                
                if (request.startsWith("PREUZIMANJE_ORGANIZATORA")) {
                    Server.kreirajOrganizatoreCsv();
                    posaljiFajlKlijentu(new File(".\\src\\server\\podaci\\listaOrganizatora.csv"));
                }
                
                if (request.startsWith("MARKETING")) {
                    int i=0;
                    for(Posjetioc p : Server.posjetioci){
                        Dogadjaj dogadjaj = Server.dogadjaji.get(i);
                        if(dogadjaj instanceof Koncert || dogadjaj instanceof Promocija){
                        Server.kreirajTxtFajl(p,Server.dogadjaji.get(i));
                        i=i+1;
                    }
                    }
                    out.println("MARKETING_ZAVRSEN");
                }
                
                if (request.startsWith("DODAJ_ORGANIZATORA")) {
                    String ime = request.split("#")[1];
                    String prezime = request.split("#")[2];
                    String telefon = request.split("#")[3];
                    String email = request.split("#")[4];
                    Organizator o = new Organizator(ime,prezime,telefon,email);
                    Server.organizatoriDogadjaja.add(o);
                }
                
                if (request.startsWith("DODAJ_UCESNIKA")) {
                    String ime = request.split("#")[1];
                    String prezime = request.split("#")[2];
                    String organizacija = request.split("#")[3];
                    Ucesnik u = new Ucesnik(ime,prezime,organizacija);
                    Server.ucesniciNaDogadjaju.add(u);
                }
                
                if (request.startsWith("DODAJ_PREDAVACA")) {
                    String ime = request.split("#")[1];
                    String prezime = request.split("#")[2];
                    String organizacija = request.split("#")[3];
                    Predavac p = new Predavac(ime,prezime,organizacija);
                    Server.predavaciNaDogadjaju.add(p);
                }

                if (request.startsWith("NOVI_DOGADJAJ")) {
                    String naziv = request.split("#")[1];
                    String datumPocetka = request.split("#")[2];;
                    String vrijemePocetka = request.split("#")[3];
                    String vrijemeZavrsetka = request.split("#")[4];
                    String organizator = request.split("#")[5];
                    Dogadjaj d = new Dogadjaj(naziv,datumPocetka,vrijemePocetka,vrijemeZavrsetka,organizator);
                    Server.dogadjaji.add(d);
                    out.println("NOVI_DOGADJAJ_OK");
                }

                if (request.startsWith("ORGANIZATORI")) {
                    oos.writeObject(Server.organizatoriDogadjaja);
                }

                if (request.startsWith("PREDAVACI")) {
                    oos.writeObject(Server.predavaciNaDogadjaju);
                }

                if (request.startsWith("UCESNICI")) {
                    oos.writeObject(Server.ucesniciNaDogadjaju);
                }

                if (request.startsWith("OBRISI_DOGADJAJ")) {
                    String dogadjajZaBrisanje = request.split("#")[1];
                    List<Dogadjaj> toRemove = new ArrayList<Dogadjaj>();
                    for (Dogadjaj d : Server.dogadjaji) {
                        if (d.getNaziv().equals(dogadjajZaBrisanje)) {
                            toRemove.add(d);
                        }
                    }
                    Server.dogadjaji.removeAll(toRemove);
                    // System.out.println(Server.dogadjaji);
                }

                if (request.startsWith("KRAJ")) {
                    System.out.println("Korisnik se odjavio!");
                    primajZahtjeve = false;
                }

            }
            // zatvori konekciju
            System.out.println("Zatvaram konekciju");
            in.close();
            out.close();
            sock.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void posaljiFajlKlijentu(File putanjaDoFajla) {
        try {
            long duzina = putanjaDoFajla.length();
            System.out.println("DUZINA FAJLA: " + duzina);
            oos.writeObject(duzina);
            byte[] buffer = new byte[2 * 1024 * 1024];
            InputStream fajl = new FileInputStream(putanjaDoFajla);
            int length = 0;
            while ((length = fajl.read(buffer)) > 0) {
                oos.write(buffer, 0, length);
            }
            System.out.println("Poslan fajl:  ");
            fajl.close();
            oos.reset();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
