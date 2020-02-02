/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.util.*;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AsiakasDao implements Dao<Asiakas, Integer> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public void create(Asiakas asiakas) throws SQLException {
       
     jdbcTemplate.update("INSERT INTO Asiakas"
            + " (id, nimi, sahkoposti)"
            + " VALUES (?, ?, ?)"
            , asiakas.getId(), asiakas.getNimi(),
            asiakas.getSahkoposti());
    }
    
     @Override
    public Asiakas read(Integer key) throws SQLException {
        List<Asiakas> asiakkaat = jdbcTemplate.query("SELECT * from Asiakas WHERE id=?",
        (rs,rowNum) -> new Asiakas(rs.getInt("id"), rs.getString("nimi"), rs.getString("sahkoposti")),
        key);
        
        if(asiakkaat.isEmpty()) {
            return null;
        }
        return asiakkaat.get(0);
    }

    @Override
    public Asiakas update(Asiakas object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<Asiakas> list() throws SQLException {
	      // ei toteutettu
	      return null;
    }
}
