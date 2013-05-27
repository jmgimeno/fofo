/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.CompetitionLeague;
import org.fofo.entity.Team;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 *
 * @author Alejandro
 */
public class CompetitionDAOImplIntegTest {

    EntityManager em = null;
    EntityManager em2 = null;
    CompetitionDAOImpl competitionDAO;
    TeamDAOImpl teamDAO;
    Team team;
    Competition competition;
    Club club;

    @Before
    public void setUp() throws Exception{
        
        competitionDAO = new CompetitionDAOImpl();
        teamDAO = new TeamDAOImpl();

        team = new Team("name");
        competition = new CompetitionLeague();
        competition.setName("League1");
        club = new Club("name");
        club.setEmail("email");
        team.setClub(club);
        em = getEntityManagerFact();
        
        em.getTransaction().begin();
        em.persist(competition);
        em.getTransaction().commit();
        
        em2 = getEntityManagerFact();
        
        em2.getTransaction().begin();
        em2.persist(team);
        em2.persist(club);
        em2.getTransaction().commit();
        
        competitionDAO.setEM(em);
        teamDAO.setEM(em2);

    }
    
    @After
    public void tearDown() throws Exception{
        if(competitionDAO.getEM() != null){
            EntityManager em = competitionDAO.getEM();
        
            if(em.isOpen()) em.close();
        }
        
        em = getEntityManagerFact();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Competition c");
        Query query2 = em.createQuery("DELETE FROM Team st");
        Query query3 = em.createQuery("DELETE FROM Club cl");
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void addCompetition() throws PersistException, Exception {

        competitionDAO.addCompetition(competition);
        Competition comp = getCompFromDB("League1");
        assertEquals(competition, comp);
    }
    
    private EntityManager getEntityManagerFact() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }
    
    private Competition getCompFromDB(String name) throws Exception{
        em = getEntityManagerFact();
        em.getTransaction().begin();
        Competition compDB = em.find(CompetitionLeague.class, name);
        em.getTransaction().commit();
        em.close();
        return compDB;
    }

    @Test
    public void addTeam_To_Competition() throws PersistException, Exception {

        List<Team> list = Arrays.asList(team);
        competitionDAO.addTeam(competition, team);
        assertEquals(list, competition.getTeams());
        assertEquals(competition.getTeams(), 
                competitionDAO.findCompetitionByName("League1").getTeams());

    }
    
    @Test
    public void findCompetitionsByTeam() throws Exception{
        List<Competition> list = Arrays.asList(competition);
        competitionDAO.addTeam(competition, team);
        assertEquals(list, competitionDAO.findCompetitionByTeam(team.getName()));
    }
    
    @Test
    public void getCompetitions() throws Exception{
        List<Competition> comps = Arrays.asList(competition);
        assertEquals(comps, competitionDAO.getCompetitionms());
    }

    @Test(expected = InvalidTeamException.class)
    public void add_incorrect_team_to_competition() throws PersistException, Exception{
        competitionDAO.addTeam(competition, null);
        
    }
    
    @Test(expected=InvalidCompetitionException.class)
    public void addTeamtoIncorrectCompetition() throws Exception{
        competitionDAO.addTeam(null, team);
    }
    
    @Test (expected=PersistException.class)
    public void findInvalidCompetitionInBD() throws Exception{
        Competition comp = competitionDAO.findCompetitionByName("liga");
    }
    
}