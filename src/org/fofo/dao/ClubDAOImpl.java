/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
    public void addClub(Club club) throws PersistException {

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
    public List<Club> getClubs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Club findClubByName(String name) {
        Club club = null;
        try {
            em.getTransaction().begin();
            club = em.find(Club.class, name);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw e;
        } finally {
            if(em.isOpen()) em.close();            
        }
        
        return club;
    }

    @Override
    public Club findClubByTeam(String name) {
        Team  team = null;
        try {
            em.getTransaction().begin();
            team = em.find(Team.class, name);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw e;
        } finally {
            if(em.isOpen()) em.close();            
        }

        return findClubByName(team.getClub().getName());
    }
}
