/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.fofo.entity.Club;
import org.fofo.entity.Team;

/**
 *
 * @author ruffolution, Anatoli
 */
public class ClubDAOImpl implements ClubDAO {

    private EntityManager em;
    
    public ClubDAOImpl() {
    }

    public void setEM(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEM() {
        return this.em;
    }
    
    @Override
    public void addClub(Club club) throws Exception {
        
         if(clubExist(club))
            throw new AlreadyExistingClubOrTeamsException("This club "
                    +club.getName()+" already exist in DB");
        
        if(teamsExist(club.getTeams()))
            throw new AlreadyExistingClubOrTeamsException("One or more teams "
                    +"of this club "+club.getName()+" already exist in DB");
  
        
        try {
            em.getTransaction().begin();
            em.persist(club);
            em.getTransaction().commit();
            
        } catch (PersistenceException e) {
            throw e;
        } finally {
            if (em.isOpen()) em.close();            
        }
    }

    @Override
    public void removeClub(String name) {
//        Club club = findClubByName(name);
//        em.remove(club);
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public List<Club> getClubs() throws Exception{  
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT c FROM Club c");
            em.getTransaction().commit();
            
        }catch(Exception e){
            throw e;
        }finally{
            if(em.isOpen()) em.close();
        }
        
        return (List<Club>) query.getResultList();
    }

    @Override
    public Club findClubByName(String name) throws PersistenceException{
        Club club = null;
        try {
            em.getTransaction().begin();
            club = (Club) em.find(Club.class, name);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw e;
        } finally {
            if(em.isOpen()) em.close();            
        }
        
        return club;
    }

    @Override
    public Club findClubByTeam(String name) throws PersistenceException{
        Team team = searchTeam(name);              
        
        return findClubByName(team.getClub().getName());
    }
    
    /* PRIVATE OPS */

    private boolean clubExist(Club club) {
        return findClubByName(club.getName()) != null;
    }

    private boolean teamsExist(List<Team> teams) {
        for(Team t : teams){
            Team team = searchTeam(t.getName());
            if(team != null)
                return true;
        }
        
        return false;
    }
    
    private Team searchTeam(String name) throws PersistenceException{
        Team team = null;
        try {
            em.getTransaction().begin();
            team = (Team) em.find(Team.class, name);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw e;
        } finally {
            if(em.isOpen()) em.close();            
        } 
        
        return team;
    }
}
