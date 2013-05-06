/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author Ivan
 */
public class CompetitionDAOImpl implements CompetitionDAO{
    
    EntityManager em;

    @Override
    public void addCompetition(Competition competition) throws PersistException{
       try{
          em.getTransaction().begin();
          em.persist(competition);
          em.getTransaction().commit();
          
       }
       catch (PersistenceException e){
	  throw new PersistException();
       }
       finally{
          if (em.isOpen()) em.close();
       }
    }

    @Override
    public void removeCompetition(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTeam(Competition competition, Team team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Competition> getCompetitionms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Competition findCompetitionByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Competition> findCompetitionByTeam(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
