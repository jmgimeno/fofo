/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Team;

/**
 *
 * @author josepma
 * @author RogerTorra
 * 
 * 
 */


public interface TeamDAO {
    
    void addTeam(Team team){      
    }
    
    void removeTeam(String name){
    }
    
    List<Team> getTeams(){
    }
    
    Team findTeamByName(String name){
    }
    
    List<Team> findTeamByClub(String name){
    }
}
