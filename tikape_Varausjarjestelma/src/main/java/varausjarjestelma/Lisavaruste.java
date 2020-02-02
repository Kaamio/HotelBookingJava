/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;


public class Lisavaruste {
    Integer id;
    String nimi;
    Varaus varaus;
    Integer maara;
    
    public Lisavaruste() {        
    
}
    
    public Lisavaruste(String nimi, Varaus varaus) {
        this.nimi = nimi;       
        this.varaus = varaus;
    }
    
    public Lisavaruste(String nimi, int maara) {
        this.nimi = nimi;    
        this.maara = maara;
    }

    public Integer getMaara() {
        return maara;
    }
    
      public Lisavaruste(String nimi) {
        this.nimi = nimi;             
    }      

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Varaus getVaraus() {
        return varaus;
    }

    public void setVaraus(Varaus varaus) {
        this.varaus = varaus;
    }
    
    public String toString() {
        return this.nimi;
    }
  
    
    
    
}
