package organizacijadogadjaja;

/**
 *
 * @author Goran
 */
public class Promocija extends Dogadjaj implements MarketinskaKampanja {

    public Promocija(String naziv, String datumPocetka, String vrijemePocetka,
            String vrijemeZavrsetka, String organizator) {
        super(naziv, datumPocetka, vrijemePocetka, vrijemeZavrsetka, organizator);
    }

    @Override
    public void poasaljiPromotivniMaterijal() {

    }
}
