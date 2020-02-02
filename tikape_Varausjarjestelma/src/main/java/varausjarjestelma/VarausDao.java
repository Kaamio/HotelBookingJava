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
import org.springframework.jdbc.support.*;

@Component
public class VarausDao implements Dao<Varaus , Integer>{
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    HuoneDao huoneDao;
    
    @Autowired
    AsiakasDao asiakasDao;
    
    @Override
    public void create(Varaus varaus) throws SQLException {
    KeyHolder keyHolder = new GeneratedKeyHolder();   
     
    jdbcTemplate.update(connection -> {
        PreparedStatement stmt = connection.prepareStatement(
        "INSERT INTO Varaus"
            + " (alkupvm, loppupvm, asiakas_id)"
            + " VALUES (?,?,?)", new String[]{"id"});
        stmt.setDate(1, varaus.getAlkupvm());
        stmt.setDate(2, varaus.getLoppupvm());
        stmt.setInt(3, varaus.getAsiakas().getId());
        //stmt.setInt(4,varaus.getHuone().getHuonenumero());
        return stmt;
    }, keyHolder);
     
    
    varaus.setId(keyHolder.getKey().intValue());
    }
    
     public Varaus read(Integer key) throws SQLException {
        List<Varaus> varaukset = jdbcTemplate.query(
                "SELECT * from Varaus WHERE id = ?",
        (rs,rowNum) -> new Varaus(rs.getDate("alkupvm"), rs.getDate("loppupvm"), asiakasDao.read(rs.getInt("asiakas_id"))),
        key);
        
        if(varaukset.isEmpty()) {
            return null;
        }
        return varaukset.get(0);
        }       

    @Override
     public Varaus update(Varaus object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<Varaus> list() throws SQLException {
	  List<Varaus> varaukset = jdbcTemplate.query (
            "SELECT * FROM Varaus ",           
           (rs, rowNum) -> new Varaus(rs.getInt("id"), rs.getDate("alkupvm"), 
            rs.getDate("loppupvm"), asiakasDao.read(rs.getInt("asiakas_id"))
                
           // huoneDao.read(rs.getInt("huone_id"))
           ));
                    
	     
         if(varaukset.isEmpty()) {
            return null;
        }
        return varaukset;
    }
    
  
}