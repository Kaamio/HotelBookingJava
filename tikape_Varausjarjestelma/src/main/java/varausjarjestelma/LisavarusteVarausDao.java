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
public class LisavarusteVarausDao implements Dao<LisavarusteVaraus, Integer> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public void create(LisavarusteVaraus lisavarustevaraus) throws SQLException {
       
     jdbcTemplate.update("INSERT INTO LisavarusteVaraus"
            + " (id, nimi, sahkoposti)"
            + " VALUES (?, ?, ?)"
            , 1,2,3);
    }
    
    @Override
    public LisavarusteVaraus read(Integer key) throws SQLException {        

    return null;
    }

    @Override
    public LisavarusteVaraus update(LisavarusteVaraus object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<LisavarusteVaraus> list() throws SQLException {
	      // ei toteutettu
	      return null;
    }
}
