package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectMatchException;
import org.fofo.entity.Club;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.Category;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
import org.fofo.entity.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class MatchDAOImplIntegTest {
    
    private EntityManager em;
    private MatchDAOImpl matchDAO;
    private RefereeDAOImpl refereeDAO;
    private TeamDAOImpl teamDAO;
    private Match match, matchNew;
    private Referee referee;
    private Team team1, team2;
    private Club club;
    private ClubDAOImpl clubDAO;
    
    @Before
    public void setUp() throws Exception {
        em = getEntityManager();
        
        referee = new Referee("12345678A", "Pepito");
        refereeDAO = new RefereeDAOImpl();
        refereeDAO.setEM(em);
        
        
        club = new Club("name");
        club.setEmail("email");
        clubDAO = new ClubDAOImpl();
        clubDAO.setEM(em);
        
        clubDAO.addClub(club);
        team1 = new Team("Team1", Category.FEMALE);
        team2 = new Team("Team2", Category.FEMALE);
        team1.setClub(club);
        team2.setClub(club);
        teamDAO = new TeamDAOImpl();
        teamDAO.setEM(em);
//        
        teamDAO.addTeam(team1);
        teamDAO.addTeam(team2);
        

         //***AIXO NO CAL, HO FA addTeam(team)         
//        club.getTeams().add(team1);
//        club.getTeams().add(team2);
        
        //***LES INSTRUCCIONS SEGUENTS SON CORRECTES, PERO JA LES HEM FET AMB EL 
        //***teamDAO.addTEam(team1);
//        em.getTransaction().begin();
//        em.persist(team1);
//        em.persist(team2);
//        em.getTransaction().commit();
        
        match = new Match(team1, team2);
        matchDAO = new MatchDAOImpl();
        matchDAO.setEm(em);
        matchDAO.setRefereedb(refereeDAO);
    }

    @Test
    public void test1() throws Exception {
        
    }
    
    /*
     * addRefereeToMatch function TEST
     */
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_NoMatchsInBBDD() throws Exception {
        refereeDAO.addReferee(referee);
        matchDAO.addRefereeToMatch("AAA", referee.getNif());
    }
    
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId() throws Exception {
        
        matchDAO.addRefereeToMatch("AAA", referee.getNif());
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId2() throws Exception {
        matchDAO.insertMatch(match);
        refereeDAO.addReferee(referee);
        matchDAO.addRefereeToMatch("AAA", referee.getNif());
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_NoRefereesInBBDD() throws Exception {
        matchDAO.insertMatch(match);
        matchDAO.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }
    
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif() throws Exception {
        
        matchDAO.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif2() throws Exception {
        matchDAO.insertMatch(match);
        refereeDAO.addReferee(referee);
        matchDAO.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }

    @Test
    public void addRefereeToMatch_correct() throws Exception {
       matchDAO.insertMatch(match);
        refereeDAO.addReferee(referee);
         matchDAO.addRefereeToMatch(match.getIdMatch(), referee.getNif());
        assertEquals(referee, match.getReferee());
    }

    /*
     * findMatchById function TEST
     */
    @Test(expected = IncorrectMatchException.class)
    public void findMatch_IncorrectId() throws Exception {
        matchDAO.findMatchById("AAA");
    }

    @Test
    public void findMatch_CorrectId() throws Exception {
        matchDAO.insertMatch(match);
        assertEquals(match, matchDAO.findMatchById(match.getIdMatch()));
    }
    
    private EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }
    
    @After
    public void tearDown() throws Exception {
        
        //***NO SE PER QUÈ FEIEU TOT AIXO??? 
        //em = refereeDAO.getEM();
        //if (em.isOpen()) {
        //    em.close();
       // }
        //
        //em = matchDAO.getEm();
        //if (em.isOpen()) {
        //    em.close();
       // }
        
//        em = teamDAO.getEM();
//        if (em.isOpen()) {
//            em.close();
//        }
//        
//        em = clubDAO.getEM();
//        if (em.isOpen()) {
//            em.close();
//        }
        
       // em = getEntityManager();  ****em JA LA TENIM, NO CAL OBTENIR-LA NOVAMENT.
        em.getTransaction().begin();
        
        Query query1 = em.createQuery("DELETE FROM Referee");
        Query query2 = em.createQuery("DELETE FROM Match");
        Query query3 = em.createQuery("DELETE FROM Team");
        Query query4 = em.createQuery("DELETE FROM Club");
        int deleteRecords = query1.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        deleteRecords = query4.executeUpdate();
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
         
    }
}
