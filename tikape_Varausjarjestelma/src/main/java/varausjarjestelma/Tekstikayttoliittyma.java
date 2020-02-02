package varausjarjestelma;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Date;
import java.time.ZoneId;


@Component
public class Tekstikayttoliittyma {    
 

    @Autowired
    HuoneDao huoneDao;
    
    @Autowired
    AsiakasDao asiakasDao;
    
    @Autowired
    VarausDao varausDao;

    @Autowired
    HuoneVarausDao huoneVarausDao;
    
    @Autowired
    LisavarusteDao lisavarusteDao;
    
    @Autowired
    JdbcTemplate jdbcTemplate;


    public void kaynnista(Scanner lukija) throws SQLException {
        while (true) {
            System.out.println("Komennot: ");
            System.out.println(" x - lopeta");
            System.out.println(" 1 - lisaa huone");
            System.out.println(" 2 - listaa huoneet");
            System.out.println(" 3 - hae huoneita");
            System.out.println(" 4 - lisaa varaus");
            System.out.println(" 5 - listaa varaukset");
            System.out.println(" 6 - tilastoja");
            System.out.println("");

            String komento = lukija.nextLine();
            if (komento.equals("x")) {
                break;
            }

            if (komento.equals("1")) {
                lisaaHuone(lukija);
            } else if (komento.equals("2")) {
                listaaHuoneet();
            } else if (komento.equals("3")) {
                haeHuoneita(lukija);
            } else if (komento.equals("4")) {
                lisaaVaraus(lukija);
            } else if (komento.equals("5")) {
                listaaVaraukset();
            } else if (komento.equals("6")) {
                tilastoja(lukija);
            }
        }
    }

    private void lisaaHuone(Scanner s) throws SQLException {
        System.out.println("Lisätään huone");
        System.out.println("");

        System.out.println("Minkä tyyppinen huone on?");
        String tyyppi = s.nextLine();
        System.out.println("Mikä huoneen numeroksi asetetaan?");
        int numero = Integer.valueOf(s.nextLine());
        System.out.println("Kuinka monta euroa huone maksaa yöltä?");
        int hinta = Integer.valueOf(s.nextLine());
        
        Huone huone = new Huone(numero, tyyppi, hinta);
       
        huoneDao.create(huone) ;
    }

    private void listaaHuoneet() throws SQLException {
        System.out.println("Listataan huoneet");
        System.out.println("");
        
        for(Huone i : huoneDao.list()) {
            System.out.println(i);
        };

    }

    private void haeHuoneita(Scanner s) throws SQLException {
        System.out.println("Haetaan huoneita");
        System.out.println("");

        System.out.println("Milloin varaus alkaisi (yyyy-MM-dd)?");;
        LocalDateTime alku = LocalDateTime.parse(s.nextLine() + " " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Milloin varaus loppuisi (yyyy-MM-dd)?");
        LocalDateTime loppu = LocalDateTime.parse(s.nextLine() + " " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Minkä tyyppinen huone? (tyhjä = ei rajausta)");
        String tyyppi = s.nextLine();
        System.out.println("Minkä hintainen korkeintaan? (tyhjä = ei rajausta)");
        int maksimihinta = Integer.valueOf(s.nextLine());
        
        List<Huone> vapaatHuoneet = huoneDao.etsiVapaat(localTimetoDate(alku), localTimetoDate(loppu), tyyppi, maksimihinta);
        if (vapaatHuoneet.size()>0) {
        for (Huone i : huoneDao.etsiVapaat(localTimetoDate(alku), localTimetoDate(loppu), tyyppi, maksimihinta)) {
            System.out.println(i);
        };
    } else {
        
        System.out.println("Ei vapaita huoneita.");
        }
    }

    private void lisaaVaraus(Scanner s) throws SQLException {
        System.out.println("Haetaan huoneita");
        System.out.println("");

        System.out.println("Milloin varaus alkaisi (yyyy-MM-dd)?");;
        LocalDateTime alku = LocalDateTime.parse(s.nextLine() + " " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Milloin varaus loppuisi (yyyy-MM-dd)?");
        LocalDateTime loppu = LocalDateTime.parse(s.nextLine() + " " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Minkä tyyppinen huone? (tyhjä = ei rajausta)");
        String tyyppi = s.nextLine();
        System.out.println("Minkä hintainen korkeintaan? (tyhjä = ei rajausta)");
        int maksimihinta = Integer.valueOf(s.nextLine());
        
        List<Huone> vapaatHuoneet = huoneDao.etsiVapaat(localTimetoDate(alku), localTimetoDate(loppu), tyyppi, maksimihinta);
        
      
        // mikäli huoneita ei ole vapaana, ohjelma tulostaa seuraavan viestin
        // ja varauksen lisääminen loppuu
        if(vapaatHuoneet.size() < 1) {
        System.out.println("Ei vapaita huoneita.");         
        } else {
        
        // muulloin, ohjelma kertoo vapaiden huoneiden lukumäärän. 
        System.out.println("Huoneita vapaana: " + vapaatHuoneet.size());
        System.out.println("");
        
        Collections.sort(vapaatHuoneet);
        Collections.reverse(vapaatHuoneet);
        // tämän jälkeen kysytään varattavien huoneiden lukumäärää
        // luvuksi tulee hyväksyä vain sopiva luku, esimerkissä 3 ei esim
        // kävisi, sillä vapaita huoneita vain 2
        int huoneita = -1;
        while (true) {
            System.out.println("Montako huonetta varataan?");
            huoneita = Integer.valueOf(s.nextLine());
            if (huoneita >= 1 && huoneita <=  vapaatHuoneet.size()) {
                break;
            }
            System.out.println("Epäkelpo huoneiden lukumäärä.");
        }
        
        //List<Huone> kalleimmat = huoneDao.etsiKalleimmat(huoneita,vapaatHuoneet);
        
            
        // tämän jälkeen kysytään lisävarusteet
        List<Lisavaruste> lisavarusteet = new ArrayList<>();
        while (true) {
            System.out.println("Syötä lisävaruste, tyhjä lopettaa");
            String lisavarusteenNimi = s.nextLine();
            if (lisavarusteenNimi.isEmpty()) {
                break;
            }
            Lisavaruste lisavaruste = new Lisavaruste(lisavarusteenNimi);
            lisavarusteet.add(lisavaruste);
        }
            
        // ja lopuksi varaajan tiedot
        System.out.println("Syötä varaajan nimi:");
        String nimi = s.nextLine();
        System.out.println("Syötä varaajan id:");
        int id = Integer.valueOf(s.nextLine());
        System.out.println("Syötä varaajan sähköpostiosoite:");
        String sahkoposti = s.nextLine();
        
        
        //System.out.println(vapaatHuoneet.size());
        
        Asiakas asiakas = new Asiakas(id, nimi, sahkoposti);   
        
        if(asiakasDao.read(id) == null) {
            asiakasDao.create(asiakas);        
        }
        
        Varaus varaus = new Varaus(localTimetoDate(alku) , localTimetoDate(loppu) ,asiakas);
        varausDao.create(varaus);
        
        for(int i = 0; i<lisavarusteet.size(); i++) {
            lisavarusteet.get(i).setVaraus(varaus);
            lisavarusteDao.create(lisavarusteet.get(i));
        }
        
        int j = 0;
        LocalDateTime vertailtavaAika = alku;
        LocalDateTime vertailtavaLoppu = loppu;
        
        
        while(j<huoneita) {        
         Huone huonetest = new Huone(vapaatHuoneet.get(j).getHuonenumero(),vapaatHuoneet.get(j).getHuonetyyppi(),vapaatHuoneet.get(j).getHinta());
         vertailtavaAika = alku;   
            while(vertailtavaAika.compareTo(vertailtavaLoppu)<=0) {
                HuoneVaraus huonevaraus = new HuoneVaraus(varaus,huonetest,localTimetoDate(vertailtavaAika),0);                
                huoneVarausDao.create(huonevaraus);
                vertailtavaAika = vertailtavaAika.plusDays(1);
                
            }
                varaus.lisaaHuone(huonetest);
                j++;
        }        

        // kun kaikki tiedot on kerätty, ohjelma lisää varauksen tietokantaan
        // -- varaukseen tulee lisätä kalleimmat vapaat huoneet!
    }
    }
    
    public static java.sql.Date localTimetoDate(LocalDateTime lt) {
        return new java.sql.Date(lt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());        
    }
    
    private void listaaVaraukset() throws SQLException { 
        System.out.println("Listataan varaukset");
        System.out.println("");     
        System.out.println("Lisavarusteet:");
        
        
        for(Varaus i : varausDao.list()) {
            System.out.println(i + " Huoneita: " +  huoneVarausDao.laskehuoneet(i.getId()) +  " Lisavarusteet: " + lisavarusteDao.laskevarusteet(i.getId()));
                for(HuoneVaraus j : huoneVarausDao.listaaHuoneet(i.getId())) {
                    System.out.println("Huone: " + j.getHuone().getHuonenumero() + " "+ j.getHuone().getHuonetyyppi() + " hinta: " + j.getHuone().getHinta());
                }
        };
        

        // alla olevassa esimerkissä oletetaan, että tietokannassa on 
        // kolme varausta
        /*
        System.out.println("Essi Esimerkki, essi@esimerkki.net, 2019-02-14, 2019-02-15, 1 päivä, 2 lisävarustetta, 1 huone. Huoneet:");
        System.out.println("\tCommodore, 128, 229 euroa");
        System.out.println("\tYhteensä: 229 euroa");
        System.out.println("");
        System.out.println("Anssi Asiakas, anssi@asiakas.net, 2019-02-14, 2019-02-15, 1 päivä, 0 lisävarustetta, 1 huone. Huoneet:");
        System.out.println("\tSuperior, 705, 159 euroa");
        System.out.println("\tYhteensä: 159 euroa");
        System.out.println("");
        System.out.println("Anssi Asiakas, anssi@asiakas.net, 2020-03-18, 2020-03-21, 3 päivää, 6 lisävarustetta, 2 huonetta. Huoneet:");
        System.out.println("\tSuperior, 705, 159 euroa");
        System.out.println("\tCommodore, 128, 229 euroa");
        System.out.println("\tYhteensä: 1164 euroa");
        */
    }

    private void tilastoja(Scanner lukija) throws SQLException {
        System.out.println("Mitä tilastoja tulostetaan?");
        System.out.println("");

        // tilastoja pyydettäessä käyttäjältä kysytään tilasto
        System.out.println(" 1 - Suosituimmat lisävarusteet");
        System.out.println(" 2 - Parhaat asiakkaat");
        System.out.println(" 3 - Varausprosentti huoneittain");
        System.out.println(" 4 - Varausprosentti huonetyypeittäin");

        System.out.println("Syötä komento: ");
        int komento = Integer.valueOf(lukija.nextLine());

        if (komento == 1) {
            suosituimmatLisavarusteet();
        } else if (komento == 2) {
            parhaatAsiakkaat();
        } else if (komento == 3) {
            varausprosenttiHuoneittain(lukija);
        } else if (komento == 4) {
            varausprosenttiHuonetyypeittain(lukija);
        }
    }

    private void suosituimmatLisavarusteet() throws SQLException{
        System.out.println("Tulostetaan suosituimmat lisävarusteet");
        System.out.println("");
            for(Lisavaruste i : lisavarusteDao.etsivarusteet()) {
            System.out.println("Lisavaruste: " +  i.getNimi() + " määrä: " + i.getMaara());
            }
        
        }

    private void parhaatAsiakkaat() throws SQLException {
        System.out.println("Tulostetaan parhaat asiakkaat");
        System.out.println("");
        for(HuoneVaraus i : huoneVarausDao.tulostaParhaatAsiakkaat()) {
            System.out.println("Nimi: " +i.getNimi() + " Yhteensä: " + i.getYhteensa());
        }
       
    }

    private void varausprosenttiHuoneittain(Scanner lukija) {
        System.out.println("Tulostetaan varausprosentti huoneittain");
        System.out.println("");

        System.out.println("Mistä lähtien tarkastellaan?");
        LocalDateTime alku = LocalDateTime.parse(lukija.nextLine() + "-01 " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Mihin asti tarkastellaan?");
        LocalDateTime loppu = LocalDateTime.parse(lukija.nextLine() + "-01 " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // alla esimerkkitulostus
        System.out.println("Tulostetaan varausprosentti huoneittain");
        System.out.println("Excelsior, 604, 119 euroa, 0.0%");
        System.out.println("Excelsior, 605, 119 euroa, 0.0%");
        System.out.println("Superior, 705, 159 euroa, 22.8%");
        System.out.println("Commodore, 128, 229 euroa, 62.8%");
    }

    private void varausprosenttiHuonetyypeittain(Scanner lukija) {
        System.out.println("Tulostetaan varausprosentti huonetyypeittäin");
        System.out.println("");

        System.out.println("Mistä lähtien tarkastellaan?");
        LocalDateTime alku = LocalDateTime.parse(lukija.nextLine() + "-01 " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Mihin asti tarkastellaan?");
        LocalDateTime loppu = LocalDateTime.parse(lukija.nextLine() + "-01 " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // alla esimerkkitulostus
        System.out.println("Tulostetaan varausprosentti huonetyypeittän");
        System.out.println("Excelsior, 0.0%");
        System.out.println("Superior, 22.8%");
        System.out.println("Commodore, 62.8%");
    }    
    

}
