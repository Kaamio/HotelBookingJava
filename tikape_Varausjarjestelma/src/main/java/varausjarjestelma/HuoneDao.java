/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.sql.*;
//import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class HuoneDao implements Dao<Huone, Integer> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public void create(Huone huone) throws SQLException {
       
     jdbcTemplate.update("INSERT INTO Huone"
            + " (huonenumero, huonetyyppi, hinta)"
            + " VALUES (?, ?, ?)"
            , huone.getHuonenumero(), huone.getHuonetyyppi(),
            huone.getHinta());
    }

    @Override
    public Huone read(Integer key) throws SQLException {
        List<Huone> huoneet = jdbcTemplate.query("SELECT * from Huone WHERE huonenumero=?",
        (rs,rowNum) -> new Huone(rs.getInt("huonenumero"), rs.getString("huonetyyppi"), rs.getInt("hinta")),
        key);
        
        if(huoneet.isEmpty()) {
            return null;
        }
        return huoneet.get(0);
    }

    @Override
    public Huone update(Huone object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<Huone> list() throws SQLException {
	List<Huone> huoneet = jdbcTemplate.query (
        "SELECT * FROM Huone",
            (rs, rowNum) -> new Huone(rs.getInt("huonenumero"), rs.getString("huonetyyppi"),
            rs.getInt("hinta")));
	     
         if(huoneet.isEmpty()) {
            return null;
        }
        return huoneet;
    }
    
       public List<Huone> etsiVapaat(Date alku, Date loppu, String huone, int maxhinta) {
            
           if (huone == null) {
           List<Huone> huoneet = jdbcTemplate.query (               
            "SELECT * FROM Huone WHERE huonenumero NOT IN (SELECT huone_id FROM HuoneVaraus WHERE paiva > ? AND paiva < ?) AND hinta < ? AND huonetyyppi = ?",           
           (rs, rowNum) -> new Huone(rs.getInt("huonenumero"), rs.getString("huonetyyppi"),
            rs.getInt("hinta")),alku,loppu,maxhinta,huone); 
           return huoneet;
        } else {
            List<Huone> huoneet = jdbcTemplate.query (               
            "SELECT * FROM Huone WHERE huonenumero NOT IN (SELECT huone_id FROM HuoneVaraus WHERE paiva > ? AND paiva < ?) AND hinta < ?",           
           (rs, rowNum) -> new Huone(rs.getInt("huonenumero"), rs.getString("huonetyyppi"),
            rs.getInt("hinta")),alku,loppu,maxhinta);
            return huoneet;
        }           
       
    }
       
       
       public List<Huone> etsiKalleimmat(int maara, List<Huone> huoneita) {        
       ArrayList<Huone> huoneet = new ArrayList<>();   
       
        huoneita.stream().sorted(Comparator.comparing(Huone::getHinta));        
        for(int i=0; i<=maara; i++) {
          
        }  
        
        return huoneet;
    }
       
     
}
