/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.entity.Competition;


/**
 *
 * @author RogerTorra
 */
public interface CompetitionDAO {
    
    void addCompetition(Competition competition){      
    }
    
    void removeCompetition(String name){
    }
    
    List<Team> getCompetitionms(){
    }
    
    Team findCompetitionByName(String name){
    }
    
    List<Team> findCompetitionByTeam(String name){
    }
    
    
}
