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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


@Component
public class LisavarusteDao implements Dao<Lisavaruste , Integer> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    HuoneDao huoneDao;
    
    @Autowired
    AsiakasDao asiakasDao;
    
    @Autowired
    VarausDao varausDao;
    
    @Override
    public void create(Lisavaruste lisavaruste) throws SQLException {
    KeyHolder keyHolder = new GeneratedKeyHolder();   
     
   jdbcTemplate.update(connection -> {
        PreparedStatement stmt = connection.prepareStatement(
        "INSERT INTO Lisavaruste"
            + " (nimi, varaus_id)"
            + " VALUES (?,?)", new String[]{"id"});
        stmt.setString(1, lisavaruste.getNimi());
        stmt.setInt(2, lisavaruste.getVaraus().getId());    
       return stmt;
    }, keyHolder);
     
   lisavaruste.setId(keyHolder.getKey().intValue());
    
    }
    
    @Override
    public Lisavaruste read(Integer key) throws SQLException {
        List<Lisavaruste> lisavarusteet = jdbcTemplate.query("SELECT * from Lisavaruste WHERE id=?",
        (rs,rowNum) -> new Lisavaruste(rs.getString("nimi"),  varausDao.read(rs.getInt("varaus_id"))),
        key);
        
        if(lisavarusteet.isEmpty()) {
            return null;
        }
        return lisavarusteet.get(0);
    }

    @Override
    public Lisavaruste update(Lisavaruste object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    @Override
    public List<Lisavaruste> list() throws SQLException {
	  List<Lisavaruste> lisavarusteet  = jdbcTemplate.query (
            "SELECT * FROM Lisavaruste ",           
           (rs, rowNum) -> new Lisavaruste(rs.getString("nimi"), varausDao.read(rs.getInt("varaus_id")))           
           );
         
         if(lisavarusteet.isEmpty()) {
            return null;
        }
        return lisavarusteet;
    }
    
    public Integer laskevarusteet(Integer key) throws SQLException {
        List<Lisavaruste> lisavarusteet  = jdbcTemplate.query (
        "SELECT nimi, varaus_id FROM Lisavaruste WHERE varaus_id = ?",           
           (rs, rowNum) -> new Lisavaruste(rs.getString("nimi"), varausDao.read(rs.getInt("varaus_id"))),           
           key);
         
         if(lisavarusteet.isEmpty()) {
            return null;
        }
        return lisavarusteet.size();
    }
    
    public List<Lisavaruste> etsivarusteet() throws SQLException {
        List<Lisavaruste> lisavarusteet  = jdbcTemplate.query (
        "SELECT nimi, COUNT(nimi) as maara FROM Lisavaruste GROUP BY nimi ORDER BY maara DESC LIMIT 5",           
           (rs, rowNum) -> new Lisavaruste(rs.getString("nimi"), rs.getInt("maara")));           
           
         
         if(lisavarusteet.isEmpty()) {
            return null;
        }
        return lisavarusteet;
    }
    
    
}