/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import javax.persistence.*;
import org.fofo.entity.Team;

/**
 *
 * @author josepma
 */
public class TeamDAOImpl {

   

    private EntityManager getEntityManagerFact() {

     try{

         EntityManagerFactory emf = 
           Persistence.createEntityManagerFactory("fofo");
         return emf.createEntityManager();  

     }
     catch(Exception e){
	 e.printStackTrace();
	 return null;
     }

    }


    public void addTeam(Team team){
    
       EntityManager em = getEntityManagerFact();
       try{
          em.getTransaction().begin();
          em.persist(team);
          em.getTransaction().commit();
          
       }
       catch (PersistenceException e){
	    e.printStackTrace();
       }
       finally{
         if (em.isOpen()) em.close();
       }
    }
    
}
