/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

/**
 *
 * @author Make
 */
public class Asiakas {
    Integer id;
    String nimi;
    String sahkoposti;
    
    public Asiakas(Integer id, String nimi, String sahkoposti) {
        this.id = id;
        this.nimi = nimi;
        this.sahkoposti = sahkoposti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }
}
