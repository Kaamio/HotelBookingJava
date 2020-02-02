/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.util.*;
import java.time.*;
import java.sql.Date;

public class HuoneVaraus {
        Integer id;        
        Varaus varaus;
        Huone huone;
        Date paiva;
        int lisavarusteet;
        int huoneita;
        int yhteensa;
        String nimi;
        String email;
                
        
    public HuoneVaraus(Varaus varaus, Huone huone, Date paiva, Integer lisavarusteet) {
        this.varaus = varaus;
        this.huone = huone;
        this.paiva = paiva;
        this.lisavarusteet = lisavarusteet;
    }   
    
    public HuoneVaraus(Varaus varaus, Huone huone) {        
        this.varaus = varaus;
        this.huone = huone; 
    }   
    
    public HuoneVaraus(Integer yhteensa, String nimi, String email) {
        this.yhteensa = yhteensa;
        this.nimi = nimi;
        this.email = email;
    }
    
    public HuoneVaraus(Integer huoneita,Varaus varaus) {        
        this.varaus = varaus;
        this.huoneita = huoneita;       
        
    }   
    
     public HuoneVaraus(Integer id, Varaus varaus, Huone huone, Date paiva, Integer lisavarusteet) {
        this.id = id;
        this.varaus = varaus;
        this.huone = huone;
        this.paiva = paiva;
        this.lisavarusteet = lisavarusteet;
    }    

    public int getLisavarusteet() {
        return lisavarusteet;
    }

    public void setLisavarusteet(int lisavarusteet) {
        this.lisavarusteet = lisavarusteet;
    }
        
        public HuoneVaraus() {
            
        }

    public Huone getHuone() {
        return huone;
    }

    public void setHuone(Huone huone) {
        this.huone = huone;
    }

    

    public Date getPaiva() {
        return paiva;
    }

    public void setPaiva(Date paiva) {
        this.paiva = paiva;
    }

    public Varaus getVaraus() {
        return varaus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHuoneita() {
        return huoneita;
    }

    public void setHuoneita(int huoneita) {
        this.huoneita = huoneita;
    }

    public int getYhteensa() {
        return yhteensa;
    }

    public void setYhteensa(int yhteensa) {
        this.yhteensa = yhteensa;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public void setVaraus(Varaus varaus) {
        this.varaus = varaus;
    }
    
    
    
        
   
        
}
