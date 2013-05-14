/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author Ivan
 */
public class CompetitionDAOImpl implements CompetitionDAO {

    EntityManager em;

    public CompetitionDAOImpl() {
    }

    public void setEM(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEM() {
        return this.em;
    }

    @Override
    public void addCompetition(Competition competition) throws PersistException {
        try {
            em.getTransaction().begin();
            em.persist(competition);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new PersistException();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void removeCompetition(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTeam(Competition competition, Team team) throws PersistException {
        try {
            em.getTransaction().begin();
            
            if (team == null || team.getClub() == null || competition == null) {
                throw new PersistException();
            }
            
            Club club = (Club) em.find(Club.class, team.getClub().getName());
           
            competition.getTeams().add(team);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistException();
        }
    }

    @Override
    public List<Competition> getCompetitionms() {
        return null;
    }

    @Override
    public Competition findCompetitionByName(String name) {
        return null;
    }

    @Override
    public List<Competition> findCompetitionByTeam(String name) {
        return null;
    }
}
