package org.fofo.services.management;

import org.fofo.dao.CompetitionDAOImpl;
import org.fofo.dao.TeamDAOImpl;
import org.fofo.dao.ClubDAOImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.dao.PersistException;
import org.fofo.entity.*;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class InscriptionTeamIntegTest {

    ManagementService service;
    private CompetitionDAOImpl compDao;
    private Competition comp, comp2;
    private Team team1, team2, team3, team4, team5;
    private TeamDAOImpl teamDao;
    private Club club;
    private ClubDAOImpl clubDao;
    private EntityManager em = null;
    //final org.jmock.Sequence seq = context.sequence("seq");

    @Before
    public void setup() throws Exception {

        service = new ManagementService();
        compDao = new CompetitionDAOImpl();
        teamDao = new TeamDAOImpl();
        clubDao = new ClubDAOImpl();
        
        em = getEntityManager();
        
        compDao.setEM(em);
        teamDao.setEM(em);
        clubDao.setEM(em);

        service.setcDao(compDao);
        service.setTeamDao(teamDao);

        club = new Club("ClubExemple");
        club.setEmail("exemple@hotmail.com");
        
        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com");

        team2 = new Team("Team2", Category.FEMALE);
        team2.setClub(club);
        team2.setEmail("Team2@hotmail.com");

        team3 = new Team("Team3", Category.FEMALE);
        team3.setClub(club);
        team3.setEmail("Team3@hotmail.com");

        team4 = new Team("Team4", Category.FEMALE);
        team4.setClub(club);
        team4.setEmail("Team4@hotmail.com");

        team5 = new Team("Team5", Category.VETERAN);
        team5.setClub(club);
        team5.setEmail("Team5@hotmail.com");

        comp = Competition.create(CompetitionType.CUP);
        comp.setCategory(Category.FEMALE);
      //  comp.setInici(new Date()); //Conte la data actual
        comp.setInici(new DateTime().minusDays(8).toDate()); //Conte la data actual
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");
        service.setCompetition(comp);

        comp2 = Competition.create(CompetitionType.CUP);
        comp2.setCategory(Category.FEMALE);
        comp2.setInici(new Date());
        comp2.setMaxTeams(4);
        comp2.setMinTeams(2);
        comp2.setName("Barcelona");

        
        club.getTeams().add(team1);
        clubDao.addClub(club);
        service.setClubDao(clubDao);
        
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamName() throws Exception {
        
        //Canviar a TeamKO
        team1.setName("");
        
        teamDao.addTeam(team1);
        
        service.addTeam(comp, team1);
        
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamEmail() throws Exception {

        
        team1.setEmail(null);

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamCompetition() throws Exception {

        team1.setCompetitions(null);

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamCategory() throws Exception {

        team1.setCategory(null);

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamClub() throws Exception {

        team1.setClub(null);
       

        service.addTeam(comp, team1);
    }


    
    @Test(expected = InscriptionTeamException.class)
    public void competitionNotExist() throws Exception {
 
        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com");
        
        team1.getCompetitions().add(comp);

        service.addTeam(comp2, team1);
    }

   @Test(expected = InscriptionTeamException.class)
    public void competitionInscriptionPeriodClosed() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 11);
        comp.setInici(cal.getTime());
        service.addTeam(comp, team1);
    }

    @Test (expected = InscriptionTeamException.class)
    public void notEnoughTeamSpaceInCompetition() throws Exception {

        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com"); 
        
        service.setCompetition(comp2);

        team1.getCompetitions().add(comp2);
        team2.getCompetitions().add(comp2);
        team3.getCompetitions().add(comp2);
        team4.getCompetitions().add(comp2);
        team5.getCompetitions().add(comp2);


        service.addTeam(comp2, team1);
        service.addTeam(comp2, team2);
        service.addTeam(comp2, team3);
        service.addTeam(comp2, team4);
        service.addTeam(comp2, team5);
    }

    /**
     * Team5 and comp, have different category. So, it's not possible assign this team in that competition.
     * @throws PersistException
     * @throws Exception
     */
    @Test(expected = InscriptionTeamException.class)
    public void diffCategTeamAndCategory() throws PersistException, Exception {

        comp.setCategory(Category.FEMALE);
        team5.setCategory(Category.MALE);

        service.addTeam(comp, team5);
    }

   // @Test
    public void testInsertTeamInCompetition() throws Exception {

        team1 = new Team("Team1");
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com"); 
       
        
        teamDao.addTeam(team1);
    
        team1.getCompetitions().add(comp);


        service.addTeam(comp, team1);

        assertTrue(comp.getTeams().contains(team1));
    }

//    @Test
    public void CorrectAddOneTeam() throws Exception {

        team1.getCompetitions().add(comp);

        service.addTeam(comp, team1);
    }

//    @Test
    public void CorrectAddVariousTeam() throws Exception {

        team1.getCompetitions().add(comp);
        team2.getCompetitions().add(comp);
        team3.getCompetitions().add(comp);
        team4.getCompetitions().add(comp);


        service.addTeam(comp, team1);
        service.addTeam(comp, team2);
        service.addTeam(comp, team3);
        service.addTeam(comp, team4);

        assertTrue(comp.getTeams().contains(team1));
        assertTrue(comp.getTeams().contains(team2));
        assertTrue(comp.getTeams().contains(team3));
        assertTrue(comp.getTeams().contains(team4));
    }

//    @Test
    public void correctTeamInsertedIntoTheDB() throws Exception {

        team1.getCompetitions().add(comp);

        service.addTeam(comp, team1);
    }
    
    @After
    public void tearDown() throws Exception{
        
        em = clubDao.getEM();
        if (em.isOpen()) em.close();
        
        em = teamDao.getEM();
        if (em.isOpen()) em.close();
        
        em = compDao.getEM();
        if (em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Team");
        Query query2=em.createQuery("DELETE FROM Club");
        Query query3=em.createQuery("DELETE FROM Competition");
        int deleteRecords=query.executeUpdate();
        deleteRecords=query2.executeUpdate();
        deleteRecords=query3.executeUpdate();
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
         
    }
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }

}
