/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.util.*;
import java.time.*;
import java.sql.Date;

public class Varaus {
  Integer id;
  Date alkupvm;
  Date loppupvm;
  Asiakas asiakas;  
  List<Huone> huoneet;
  

    public Varaus() {
       
        
    }
    
    public Varaus (Date alku, Date loppu, Asiakas asiakas) {        
        //this.id = id;
        this.alkupvm = alku;
        this.loppupvm = loppu;
        this.asiakas = asiakas;        
        this.huoneet = new ArrayList<Huone>();
        
    }
    
    public Varaus (Integer id, Date alku, Date loppu, Asiakas asiakas) {        
        this.id = id;
        this.alkupvm = alku;
        this.loppupvm = loppu;
        this.asiakas = asiakas;        
        this.huoneet = new ArrayList<Huone>();
        
    }
  
    

    public Date getAlkupvm() {
        return alkupvm;
    }

    public void setAlkupvm(Date alkupvm) {
        this.alkupvm = alkupvm;
    }

    public Date getLoppupvm() {
        return loppupvm;
    }

    public void setLoppupvm(Date loppupvm) {
        this.loppupvm = loppupvm;
    }

    public Asiakas getAsiakas() {
        return asiakas;
    }

    public void setAsiakas(Asiakas asiakas) {
        this.asiakas = asiakas;
    }

    public List<Huone> getHuoneet() {
        return huoneet;
    }

    public void setHuoneet(List<Huone> huoneet) {
        this.huoneet = huoneet;
    }    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void lisaaHuone(Huone huone) {
        this.huoneet.add(huone);
        System.out.println(this.huoneet.size());
    }
    
    
    public String toString() {
     return "Varaaja: " + asiakas.getId() + " " + asiakas.getNimi() +  " alkupvm: " + this.alkupvm + " loppupvm: " + this.loppupvm; 
               
    }

    
    

   
  
   
    
}
