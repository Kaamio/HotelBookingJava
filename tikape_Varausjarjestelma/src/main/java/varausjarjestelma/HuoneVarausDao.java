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
public class HuoneVarausDao implements Dao<HuoneVaraus, Integer>{
   
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    HuoneDao huoneDao;
    
    @Autowired
    VarausDao varausDao;
    
    @Autowired
    LisavarusteDao lisavarusteDao;
    
    @Override
    public void create(HuoneVaraus huonevaraus) throws SQLException {        
        
        KeyHolder keyHolder = new GeneratedKeyHolder();   
     jdbcTemplate.update(connection -> {
        PreparedStatement stmt = connection.prepareStatement(
             "INSERT INTO HuoneVaraus"
            + " (varaus_id, huone_id, paiva, lisavarusteet )"
            + " VALUES (?, ?, ?, ?)", new String[]{"id"});
            stmt.setInt(1, huonevaraus.getVaraus().getId());           
            stmt.setInt(2, huonevaraus.getHuone().getHuonenumero());
            stmt.setDate(3, huonevaraus.getPaiva());
            stmt.setInt(4, huonevaraus.getLisavarusteet());
            return stmt;
            }, keyHolder);
     huonevaraus.setId(keyHolder.getKey().intValue());
    }
    
    @Override
    public HuoneVaraus read(Integer key) throws SQLException {        
            
    return null;
    }

    @Override
    public HuoneVaraus update(HuoneVaraus object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<HuoneVaraus> list() throws SQLException {
	     List<HuoneVaraus> huonevaraukset = jdbcTemplate.query (
            "SELECT * FROM HuoneVaraus",// WHERE varaus_id = ? ",           
           (rs, rowNum) -> new HuoneVaraus(varausDao.read(rs.getInt("varaus_id")), 
            huoneDao.read(rs.getInt("huone_id")), rs.getDate("paiva"), rs.getInt("lisavarusteet")));//,
           //varausnumero);            
          
         if(huonevaraukset.isEmpty()) {
            return null;
        }
        return huonevaraukset;    
    }
    
    public List<HuoneVaraus> listaaHuoneet(Integer key) throws SQLException{
    List<HuoneVaraus> huonevaraukset = jdbcTemplate.query (
             
            "SELECT DISTINCT huone_id, varaus_id FROM HuoneVaraus WHERE varaus_id = ? ", 
            (rs, rowNum) -> new HuoneVaraus(varausDao.read(rs.getInt("varaus_id")), 
            huoneDao.read(rs.getInt("huone_id"))),
           key);            
       
         if(huonevaraukset.isEmpty()) {
            return null;
        }
        return huonevaraukset;
    }
    
      public Integer laskehuoneet(Integer key) throws SQLException {
        List<HuoneVaraus> huoneet  = jdbcTemplate.query (
        "SELECT DISTINCT huone_id, varaus_id FROM HuoneVaraus WHERE varaus_id = ?",           
           (rs, rowNum) -> new HuoneVaraus(rs.getInt("huone_id"), varausDao.read(rs.getInt("varaus_id"))),           
           key);
         
         if(huoneet.isEmpty()) {
            return null;
        }
        return huoneet.size();
    }
      
       public List<HuoneVaraus> tulostaParhaatAsiakkaat() throws SQLException {
        List<HuoneVaraus> huoneet  = jdbcTemplate.query (
        "SELECT SUM(hinta) AS yhteensa, nimi,sahkoposti FROM Huone JOIN HuoneVaraus ON huonenumero = huone_id JOIN Varaus ON varaus_id = Varaus.id JOIN Asiakas ON asiakas_id = Asiakas.id GROUP BY nimi DESC LIMIT 10",           
           (rs, rowNum) -> new HuoneVaraus(rs.getInt("yhteensa"), rs.getString("nimi"), rs.getString("sahkoposti")));
        
         if(huoneet.isEmpty()) {
            return null;
        }
        return huoneet;
    }
}

