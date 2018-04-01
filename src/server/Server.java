package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import organizacijadogadjaja.Dogadjaj;
import organizacijadogadjaja.Izlozba;
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
public class Server {

    private static final int PORT = 9000;
    public static List<Dogadjaj> dogadjaji = new ArrayList<Dogadjaj>();
    public static List<Organizator> organizatoriDogadjaja = new ArrayList<Organizator>();
    public static List<Predavac> predavaciNaDogadjaju = new ArrayList<Predavac>();
    public static List<Ucesnik> ucesniciNaDogadjaju = new ArrayList<Ucesnik>();
    public static List<Posjetioc> posjetioci = new ArrayList<Posjetioc>();

    public static void main(String[] args) {
        try {

            popuniListuDogadjaja();
            serijalizacija(dogadjaji);
            popuniListuOrganizatora();
            serijalizacija(organizatoriDogadjaja);
            popuniListuPredavaca();
            serijalizacija(predavaciNaDogadjaju);
            popuniListuUcesnika();
            serijalizacija(ucesniciNaDogadjaju);
            popuniListuPosjetilaca();
            serijalizacija(posjetioci);

            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("Server running...");
            while (true) {
                // cekaj konekciju...
                Socket sock = ss.accept();
                System.out.println("Client address: " + (sock.getInetAddress().getHostAddress()));
                // kreiraj thread...
                ServerThread st = new ServerThread(sock);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void popuniListuDogadjaja() {
        dogadjaji.add(new Koncert("Koncert grupe 'Leb i so'", "21.02.2017.", "20:00", "01:00",
                "Marko Marić","Leb i so","3 sata"));
        dogadjaji.add(new Koncert("Koncert Beogradske grupe 'Orthodox Celts'", "11.03.2017.", "20:00", "01:00",
                "Dejan Lalić","Orthodox Celts","3 sata"));
        dogadjaji.add(new Dogadjaj("'Kosmos'", "25.02.2017.", "21:00", "22:30", "Mladen Jokić"));
        dogadjaji.add(new Izlozba("Izložba Van Goga", "17.02.2017.", "20:00", "22:00", "Borko Jelić", "Van Goga","Borko Jelić"));
        dogadjaji.add(new Izlozba("Izložba novog Van Goga", "21.02.2017.", "19:00", "22:00", "Borko Jelić", "Van Goga","Borko Jelić"));
        dogadjaji.add(new Dogadjaj("'Kosmos 2'", "09.05.2017.", "21:00", "22:30", "Mladen Jokić"));
        dogadjaji.add(new Dogadjaj("'Kosmos 3'", "09.04.2017.", "21:00", "22:30", "Mladen Jokić"));
        dogadjaji.add(new Dogadjaj("'Kosmos 4'", "11.03.2017.", "21:00", "22:30", "Mladen Jokić"));
        dogadjaji.add(new Dogadjaj("'Kosmos 5'", "12.06.2017.", "21:00", "22:30", "Mladen Jokić"));
        dogadjaji.add(new Promocija("Promocija knjige 'Saborni hram Hrista Spasitelja'", "11.03.2017.", "19:00", "21:30", 
                "Srpska pravoslavna crkvena opština Banjaluka"));
        dogadjaji.add(new Promocija("Promocija knjige autora Vinka Pandurevića", "09.04.2017.", "20:00", "22:00", 
                "Srpsko prosvjetno i kulturno društvo 'Prosvjeta' B.Luka"));
    }

    private static void popuniListuOrganizatora() {
        organizatoriDogadjaja.add(new Organizator("Marko", "Marić", "065/123-456", "marko.m@gmail.com"));
        organizatoriDogadjaja.add(new Organizator("Mladen", "Jokić", "066/768-356", "jokara@gmail.com"));
        organizatoriDogadjaja.add(new Organizator("Borko", "Jelić", "065/167-000", "borko.j@gmail.com"));
        organizatoriDogadjaja.add(new Organizator("Vedran", "Bunić", "065/935-463", "vedran@gmail.com"));
    }

    private static void popuniListuPredavaca() {
        predavaciNaDogadjaju.add(new Predavac("Nenad", "Nedić", "Pokret Zelenih"));
        predavaciNaDogadjaju.add(new Predavac("Dragan", "Jokić", "JAVA MD"));
        predavaciNaDogadjaju.add(new Predavac("Borko", "Dragić", "Tehnoplast"));
        predavaciNaDogadjaju.add(new Predavac("Zlatko", "Mikić", "Terc Trade"));
    }

    private static void popuniListuUcesnika() {
        ucesniciNaDogadjaju.add(new Predavac("Ognjen", "Nedić", "Pokret Zelenih"));
        ucesniciNaDogadjaju.add(new Predavac("Dragana", "Jokić", "JAVA MD"));
        ucesniciNaDogadjaju.add(new Predavac("Milena", "Dragić", "Tehnoplast"));
        ucesniciNaDogadjaju.add(new Predavac("Ljubomir", "Mikić", "Terc Trade"));
    }

    private static void popuniListuPosjetilaca() {
        posjetioci.add(new Posjetioc("Ognjen", "Nedić", "V.Sinđelića 45., Prnjavor", "051/655-123", "ogi@gmail.com"));
        posjetioci.add(new Posjetioc("Nikolina", "Nikolić", "Kralja Petra I 15., Gradiška", "055/655-123", "nina@gmail.com"));
        posjetioci.add(new Posjetioc("Dragana", "Marić", "S.Simića 23., Modriča", "054/655-123", "dm@gmail.com"));
        posjetioci.add(new Posjetioc("Nenad", "Jevđenić", "Lenjinova bb , Brčko", "050/655-123", "nj@gmail.com"));
        posjetioci.add(new Posjetioc("Marko", "Simič", "V.Sinđelića 45., Doboj", "053/655-123", "ms@gmail.com"));
    }

    public static void kreirajTxtFajl(Posjetioc p, Dogadjaj d) {

        FileWriter fw = null;
        try {
            File file = new File(".\\src\\server\\marketing\\" + p.getIme() + "_" + p.getPrezime() + ".txt");
            String sadrzaj = p.getIme() + " " + p.getPrezime() + "," + d.getDatumPocetka()
                    + "," + d.getNaziv() + "," + "Opis dogadjaja";
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sadrzaj);
            bw.close();
            System.out.println("Done");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void kreirajUcesnikeCsv() {
        try {
            String ucesniciZaUpis;
            PrintWriter upis = new PrintWriter(new BufferedWriter(new FileWriter(".\\src\\server\\podaci\\listaUcesnika.csv")), true);
            for (int i = 0; i < ucesniciNaDogadjaju.size(); i++) {
                ucesniciZaUpis = ucesniciNaDogadjaju.get(i).formatZaCsv();
                System.out.println("Ucesnik za upis " + ucesniciZaUpis);
                upis.println(ucesniciZaUpis);
            }
            upis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void kreirajDogadjajeCsv() {
        try {
            String dogadjajiZaUpis;
            PrintWriter upis = new PrintWriter(new BufferedWriter(new FileWriter(".\\src\\server\\podaci\\listaDogadjaja.csv")), true);
            for (int i = 0; i < dogadjaji.size(); i++) {
                dogadjajiZaUpis = dogadjaji.get(i).formatZaCsv();
                System.out.println("Ucesnik za upis " + dogadjajiZaUpis);
                upis.println(dogadjajiZaUpis);
            }
            upis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void kreirajOrganizatoreCsv() {
        try {
            String oragnizatoriZaUpis;
            PrintWriter upis = new PrintWriter(new BufferedWriter(new FileWriter(".\\src\\server\\podaci\\listaOrganizatora.csv")), true);
            for (int i = 0; i < organizatoriDogadjaja.size(); i++) {
                oragnizatoriZaUpis = organizatoriDogadjaja.get(i).formatZaCsv();
                System.out.println("Ucesnik za upis " + oragnizatoriZaUpis);
                upis.println(oragnizatoriZaUpis);
            }
            upis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <T> void serijalizacija(List<T> lista) {
        try {

            if (lista.get(0) instanceof Organizator) {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".\\src\\server\\podaci\\organizatori.ser")));
                oos.writeObject(lista);
                oos.close();
            } else if (lista.get(0) instanceof Posjetioc) {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".\\src\\server\\podaci\\posjetioci.ser")));
                oos.writeObject(lista);
                oos.close();
            } else if (lista.get(0) instanceof Predavac) {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".\\src\\server\\podaci\\predavaci.ser")));
                oos.writeObject(lista);
                oos.close();
            } else if (lista.get(0) instanceof Ucesnik) {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".\\src\\server\\podaci\\ucesnici.ser")));
                oos.writeObject(lista);
                oos.close();
            } else if (lista.get(0) instanceof Dogadjaj) {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".\\src\\server\\podaci\\dogadjaji.ser")));
                oos.writeObject(lista);
                oos.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("SERIJALIZACIJA JE ZAVRSENA");
    }

    public static <T> ArrayList<T> deSerijalizacija(String vrstaObjekta) {
        ArrayList<T> Lista = null;
        try {
            if (vrstaObjekta.equals("dogadjaj")) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(".\\src\\server\\fajlovi\\listaDogadjaja.ser")));
                Lista = (ArrayList<T>) ois.readObject();
                ois.close();
            } else if (vrstaObjekta.equals("ucesnik")) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(".\\src\\server\\fajlovi\\ucesnici.ser")));
                Lista = (ArrayList<T>) ois.readObject();
                ois.close();
            } else if (vrstaObjekta.equals("predavac")) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(".\\src\\server\\fajlovi\\predavaci.ser")));
                Lista = (ArrayList<T>) ois.readObject();
                ois.close();
            }
            if (vrstaObjekta.equals("posjetioc")) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(".\\src\\server\\fajlovi\\posjetioci.ser")));
                Lista = (ArrayList<T>) ois.readObject();
                ois.close();
            } else if (vrstaObjekta.equals("organizator")) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(".\\src\\server\\fajlovi\\organizatori.ser")));
                Lista = (ArrayList<T>) ois.readObject();
                ois.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Lista;
    }
}
