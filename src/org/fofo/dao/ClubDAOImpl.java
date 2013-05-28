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
 * @author ruffolution, Anatoli, Mohamed
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
        
        
        try {
            em.getTransaction().begin();
            
            checkExistingClubOrTeams(club);

            em.persist(club);
            em.getTransaction().commit();
            
        } catch (PersistenceException e) {
            throw e;
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
        List<Club> clubs = null;
        Query query;
        try{
            em.getTransaction().begin();
            query = em.createQuery("SELECT c FROM Club c");
            em.getTransaction().commit();
            clubs = (List<Club>) query.getResultList();
        }catch(Exception e){
            throw e;
        }
        
        return clubs;
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
        }
        
        return club;
    }

    @Override
    public Club findClubByTeam(String name) throws PersistenceException{
        Team team = null;
        try{
            em.getTransaction().begin();
            team = (Team) em.find(Team.class, name);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            throw e;
        }
                      
        
        return findClubByName(team.getClub().getName());
    }
    
    /* PRIVATE OPS */

    private boolean clubExist(Club club) {
        return em.find(Club.class, club.getName())!=null;
    }

    private boolean teamsExist(List<Team> teams) {
        for(Team t : teams){
            Team team = (Team) em.find(Team.class, t.getName());
            if(team != null)
                return true;
        }
        
        return false;
    }
    

    private void checkExistingClubOrTeams(Club club) throws AlreadyExistingClubOrTeamsException {
        if(clubExist(club))
            throw new AlreadyExistingClubOrTeamsException("This club "
                    +club.getName()+" already exist in DB");
        
        if(teamsExist(club.getTeams()))
            throw new AlreadyExistingClubOrTeamsException("One or more teams "
                    +"of this club "+club.getName()+" already exist in DB");
            
    }
}
