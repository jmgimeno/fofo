/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.*;
import org.fofo.entity.Club;
import org.fofo.entity.Team;

/**
 *
 * @author josepma
 */
public class TeamDAOImpl implements TeamDAO{

   EntityManager em;
   
   public TeamDAOImpl(){
       

       
   }
   
   public void setEM(EntityManager em){
       this.em = em;
   }
   
   public EntityManager getEM(){
       return this.em;
   }


   @Override
    public void addTeam(Team team) throws PersistException, AlreadyExistingClubOrTeamsException{
    

       try{
          em.getTransaction().begin();
          
          Club club = (Club) em.find(Club.class, team.getClub().getName());
          if (club == null) throw new PersistException();
          
          club.getTeams().add(team);
          em.persist(team);
          em.getTransaction().commit();
          
       }
       catch (PersistenceException e){
	  throw new PersistException();
       }
//       finally{
//          if (em.isOpen()) em.close();
//       }
    }
    
    public void removeTeam(String name){
    
    }
    
   
    public List<Team> getTeams(){
        
        return  null;
        
    }
    
    
   @Override
    public Team findTeamByName(String name) throws PersistException{
       Team team = null; 
       try{
          em.getTransaction().begin();
          
          team = (Team) em.find (Team.class,name);
          em.getTransaction().commit();
          
       }
       catch (PersistenceException e){
	  throw new PersistException();
       }
//       finally{
//          if (em.isOpen()) em.close();
//       }
       return team;        
        
    }
    
    public List<Team> findTeamByClub(String name){
        
        return null;
    }

//CAL TREURE-LA!!!!!
    public boolean findTeam(Team team) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
