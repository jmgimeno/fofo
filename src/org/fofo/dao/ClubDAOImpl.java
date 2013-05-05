/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.fofo.entity.Club;

/**
 *
 * @author ruffolution
 */
public class ClubDAOImpl implements ClubDAO{

   EntityManager em;
   
   public ClubDAOImpl(){       
   }
   
   public void setEM(EntityManager em){
       this.em = em;
   }
   
   public EntityManager getEM(){
       return this.em;
   }


    public void addClub(Club club) throws PersistException{
        
       try{
          em.getTransaction().begin();
          em.persist(club);
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
    public void removeClub(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Club> getClubs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club findClubByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Club> findClubByTeam(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
