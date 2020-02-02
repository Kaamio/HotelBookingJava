package varausjarjestelma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VarausjarjestelmaSovellus implements CommandLineRunner {

    public static void main(String[] args) {
        //alustaTietokanta();
        SpringApplication.run(VarausjarjestelmaSovellus.class);
        
    }

    @Autowired
    Tekstikayttoliittyma tekstikayttoliittyma;

    @Override
    public void run(String... args) throws Exception {
        Scanner lukija = new Scanner(System.in);
        tekstikayttoliittyma.kaynnista(lukija);
    }
    
     private static void alustaTietokanta() {
        // mikäli poistat vahingossa tietokannan voit ajaa tämän metodin jolloin 
        // tietokantataulu luodaan uudestaan

        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
            conn.prepareStatement("DROP TABLE Huone IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Huone(huonenumero INTEGER PRIMARY KEY, huonetyyppi varchar(30), hinta integer);").executeUpdate();
            conn.prepareStatement("DROP TABLE Asiakas IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Asiakas(id INTEGER PRIMARY KEY, nimi varchar(50), sahkoposti varchar(50));").executeUpdate();
            conn.prepareStatement("DROP TABLE Varaus IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Varaus(id INTEGER AUTO_INCREMENT PRIMARY KEY, alkupvm date, loppupvm date, asiakas_id integer, FOREIGN KEY(asiakas_id) REFERENCES Asiakas(id));").executeUpdate();
            conn.prepareStatement("DROP TABLE HuoneVaraus IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE HuoneVaraus(id INTEGER AUTO_INCREMENT PRIMARY KEY, varaus_id INTEGER, huone_id INTEGER, paiva date, lisavarusteet INTEGER, FOREIGN KEY(varaus_id) REFERENCES Varaus(id), FOREIGN KEY(huone_id) REFERENCES Huone(huonenumero));").executeUpdate();
            conn.prepareStatement("DROP TABLE Lisavaruste IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Lisavaruste(id INTEGER AUTO_INCREMENT PRIMARY KEY, nimi VARCHAR(50), varaus_id INTEGER, FOREIGN KEY(varaus_id) REFERENCES Varaus(id));").executeUpdate();                        
           
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
