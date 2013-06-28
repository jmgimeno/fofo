/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.InvalidCompetitionException;
import org.fofo.dao.exception.IncorrectTeamException;
import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.InvalidTeamException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.ClassificationTC;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.CompetitionLeague;
import org.fofo.entity.CompetitionType;
import org.fofo.entity.Team;
import org.jmock.Expectations;
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
    Team team, team2,team3,team4;
    Competition competition;
    Club club;

    @Before
    public void setUp() throws Exception{
        
        competitionDAO = new CompetitionDAOImpl();
        teamDAO = new TeamDAOImpl();

        
        competition = new CompetitionLeague();
        competition.setName("League1");
        
        club = new Club("name");
        club.setEmail("email");
        
        // TEAM CREATION
        team = new Team("name");
        team.setClub(club);

        team2 = new Team("team2");
        team2.setClub(club);

        team3 = new Team("team3");
        team3.setClub(club);

        team4 = new Team("team4");
        team4.setClub(club);
        
        em = getEntityManagerFact();
        
        
        em.getTransaction().begin();
        em.persist(competition);
        em.getTransaction().commit();
        
    //   em2 = getEntityManagerFact();
        
        em.getTransaction().begin();
        em.persist(team);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);
        
        em.persist(club);
        em.getTransaction().commit();
        
        
        
        competitionDAO.setEM(em);
        teamDAO.setEM(em);


        
        
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
        Query query4 = em.createQuery("DELETE FROM ClassificationTC classif");
        
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        deleteRecords = query4.executeUpdate();
        
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
    
    @Test
    public void removeCompetition() throws Exception{
        List<Competition> list = new ArrayList<Competition>();
        competitionDAO.removeCompetition(competition.getName());
        assertEquals(list, competitionDAO.getCompetitionms());
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
    
       /*
     * 
     * TESTS FOR CLASSIFICATIONS
     * 
     * 
     */

    @Test(expected=IncorrectTeamException.class)
    public void classificationOfANonExistingTeam() throws Exception{
        
        final Team team = new Team("team10");
        
                
            
          competitionDAO.addClassificationTC("team10", "League1");
          
          
                
    }

    @Test(expected=InvalidCompetitionException.class)
    public void classificationOfANonExistingComp() throws Exception{
        
        final Competition comp = Competition.create(CompetitionType.LEAGUE);
        comp.setName("comp10");
        comp.setMinTeams(5);
        comp.setMaxTeams(10);
        
                
            
          competitionDAO.addClassificationTC("name", "comp10");  
                
    }

    @Test
    public void classificationInsertion() throws Exception{

        competitionDAO.addTeam(competition, team);
        competitionDAO.addTeam(competition, team2);
        competitionDAO.addTeam(competition, team3);
        competitionDAO.addTeam(competition, team4);
        
        
          Query clq = em.createQuery("SELECT cl FROM ClassificationTC cl");
        
          List<ClassificationTC> lclass = clq.getResultList();
     
          assertEquals("There should be four classification",4,lclass.size());    
          //assertEquals("Should be of team team2", team2,lclass.get(0).getTeam());
          
    }
    
    @Test
    public void classificationInsertionWithCorrectClassificationTC() throws Exception{
        ClassificationTC classTC1 = new ClassificationTC (competition,team);
        classTC1.setPoints(0);
        ClassificationTC classTC2 = new ClassificationTC (competition,team2);
        classTC2.setPoints(0);
        ClassificationTC classTC3 = new ClassificationTC (competition,team3);
        classTC3.setPoints(0);
        ClassificationTC classTC4 = new ClassificationTC (competition,team4);  
        classTC4.setPoints(0); 
        
        List<ClassificationTC> classTC = new ArrayList<ClassificationTC>();
        classTC.add(classTC1);
        classTC.add(classTC2);
        classTC.add(classTC3);
        classTC.add(classTC4);        
        
        competitionDAO.addTeam(competition, team);
        competitionDAO.addTeam(competition, team2);
        competitionDAO.addTeam(competition, team3);
        competitionDAO.addTeam(competition, team4);
        
        
          Query clq = em.createQuery("SELECT cl FROM ClassificationTC cl");
        
          List<ClassificationTC> classTCDB = clq.getResultList();
     
          assertEquals("There should be same classificationTC",classTC,classTCDB);  
   
    }    
    
    
    
    
    
    

    @Test(expected=IncorrectTeamException.class)
    public void classificationInsertionOfATeamNotInCompetition() throws Exception{
       competitionDAO.addTeam(competition, team);
        competitionDAO.addTeam(competition, team2);
        competitionDAO.addTeam(competition, team3);
            
         //Notice that team4 is not assigned to the competition competition.
        

                
            
          competitionDAO.addClassificationTC("team4", "League1");  
                
    }


    
    
    
    @Test(expected=InvalidCompetitionException.class)
    public void getClassificationOfANonExistingComp() throws Exception{
        
        final Competition comp = Competition.create(CompetitionType.LEAGUE);
        comp.setName("comp10");
        comp.setMinTeams(5);
        comp.setMaxTeams(10);
        
                
            
          competitionDAO.findClassificationsTC("comp10");  
                
    }


    
    
    @Test
    public void addPointsToTeam() throws Exception{
        competitionDAO.addTeam(competition, team);
        
        
        competitionDAO.addClassificationTC(team.getName(), competition.getName());  

        Query clq = em.createQuery("SELECT cl FROM ClassificationTC cl");
        
        List<ClassificationTC> lclass = clq.getResultList();
        team.setClassificationsTC(lclass);
        competitionDAO.addPointsToClassificationTC(team.getName(), competition.getName(), 3);
        assertEquals(3, lclass.get(0).getPoints());
    }
    
    @Test
    public void findClassificationsTCTest() throws Exception{
        teamDAO.addTeam(team);
        competition.getTeams().add(team);
        competitionDAO.addCompetition(competition);
        competitionDAO.addClassificationTC(team.getName(),competition.getName());        
        
        List<ClassificationTC> list = competitionDAO.findClassificationsTC(competition.getName());
        ClassificationTC information = new ClassificationTC(competition, team);
                assertTrue(list.contains(information));
    }
    
    @Test(expected=IncorrectTeamException.class)
    public void findIncorrectClassificationsTCTest() throws Exception{
        teamDAO.addTeam(team);
        competitionDAO.addCompetition(competition);
        competitionDAO.addClassificationTC(team.getName(),competition.getName());        
        
        competitionDAO.findClassificationsTC(competition.getName());
    }
    
    @Test(expected=InvalidCompetitionException.class)
    public void findWrongClassificationsTCTest() throws Exception{
        
        competitionDAO.findClassificationsTC("comp1");
    }
}