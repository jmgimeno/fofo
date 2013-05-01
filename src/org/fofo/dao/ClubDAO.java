/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Club;

/**
 *
 * @author RogerTorra
 */
public interface ClubDAO {
    
    void addClub(Club club){      
    }
    
    void removeClub(String name){
    }
    
    List<Team> getClubs(){
    }
    
    Team findClubByName(String name){
    }
    
    List<Team> findClubByTeam(String name){
    }
    
}
