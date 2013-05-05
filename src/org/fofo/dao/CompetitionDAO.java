/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Competition;


/**
 *
 * @author RogerTorra
 */
public interface CompetitionDAO {
    
    void addCompetition(Competition competition);
    
    void removeCompetition(String name);
    
    List<Competition> getCompetitionms();
    
    Competition findCompetitionByName(String name);
    
    List<Competition> findCompetitionByTeam(String name);

    public boolean findCompetition(Competition competition);
    
    
}
