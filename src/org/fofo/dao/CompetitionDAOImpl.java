/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.fofo.entity.ClassificationTC;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author Ivan, Jordi Niubo i Oriol Capell-
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
        } 

    }

    @Override
    public void removeCompetition(String name) throws Exception{
        em.getTransaction().begin();
        if (name == null) throw new InvalidCompetitionException();
        Query q = em.createQuery("DELETE FROM Competition c WHERE c.name='"+name+"'");
        int delete = q.executeUpdate();
        em.getTransaction().commit();
    }

   @Override
    public void addTeam(Competition competition, Team team) throws Exception {
            em.getTransaction().begin();

            if (team == null) {
                throw new InvalidTeamException();
            }
            if(competition == null) throw new InvalidCompetitionException();
           
            Competition compAux = (Competition)em.find(Competition.class, competition.getName());
            Team teamAux = (Team)em.find(Team.class, team.getName());

            
            if (compAux == null || teamAux == null) {
                throw new PersistException();
            }

            compAux.getTeams().add(teamAux);
            teamAux.getCompetitions().add(compAux);
            em.getTransaction().commit();
    }

    @Override
    public List<Competition> getCompetitionms() {
        List<Competition> list = new ArrayList<Competition>();
        em.getTransaction().begin();
        Query pp = em.createQuery("SELECT c FROM Competition c");
        list = pp.getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public Competition findCompetitionByName(String name) throws Exception{
        em.getTransaction().begin();
        if (name == null) throw new InvalidCompetitionException();
        Competition comp = (Competition)em.find(Competition.class, name);
        if (comp == null) throw new PersistException();
        em.getTransaction().commit();
        return comp;
    }

    @Override
    public List<Competition> findCompetitionByTeam(String name) throws Exception{
        List<Competition> list = new ArrayList<Competition>();
        em.getTransaction().begin();
        if (name == null) throw new InvalidTeamException();
        Team team = (Team)em.find(Team.class, name);
        for (Competition comp : team.getCompetitions()){
             comp = (Competition)em.find(Competition.class, comp.getName());
             list.add(comp);
        }
        em.getTransaction().commit();
        return list;
    }

    @Override
    public List<ClassificationTC> findClassificationsTC(String name) throws Exception {
        em.getTransaction().begin();
        if (name == null) throw new InvalidCompetitionException();
        Competition comp = (Competition)em.find(Competition.class, name);  
        if (comp == null) throw new PersistException();        
        List<ClassificationTC> list = comp.getClassificationsTC();
        em.getTransaction().commit();
        return list; 
    }
}
