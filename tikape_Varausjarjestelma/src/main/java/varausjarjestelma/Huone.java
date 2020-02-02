/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.time.LocalDate;
import java.util.*;


public class Huone implements Comparable<Huone> {
    
    Integer huonenumero;
    String huonetyyppi;
    Integer hinta; 
    List<HuoneVaraus> huonevaraukset;
    
    public Huone(int huonenumero, String huonetyyppi, int hinta) {
        this.huonenumero = huonenumero;
        this.huonetyyppi = huonetyyppi;
        this.hinta = hinta;
        //this.huonevaraukset = new ArrayList<>();
    }

    public Integer getHuonenumero() {
        return huonenumero;
    }

    public void setHuonenumero(Integer huonenumero) {
        this.huonenumero = huonenumero;
    }

    public String getHuonetyyppi() {
        return huonetyyppi;
    }

    public void setHuonetyyppi(String huonetyyppi) {
        this.huonetyyppi = huonetyyppi;
    }

    public Integer getHinta() {
        return hinta;
    }

    public void setHinta(Integer hinta) {
        this.hinta = hinta;
    }
    
    public int compare(Huone eka, Huone toka) {
        return(Integer.compare(eka.getHinta(), toka.getHinta()));
    }
    
    public String toString() {
        return (this.huonetyyppi + ", "+ this.huonenumero +", "+this.hinta + " euroa");
    }
    
    @Override
    public int compareTo(Huone o) {
        return this.getHinta().compareTo(o.getHinta());
    }
  
    
}
