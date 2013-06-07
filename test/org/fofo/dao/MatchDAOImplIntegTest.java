package org.fofo.dao;

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
    private RefereeDAO refereeDAO;
    private Match match, matchNew;
    private Referee referee;
    private Team team1, team2;
    private TeamDAO teamDAO;

    @Before
    public void setUp() throws Exception {

        em = getEntityManager();

        referee = new Referee("12345678A", "Pepito");
        refereeDAO = new RefereeDAOImpl();
        refereeDAO.setEM(em);
        //refereeDAO.addReferee(referee);

        team1 = new Team("Team1", Category.FEMALE);
        team2 = new Team("Team2", Category.FEMALE);
        teamDAO = new TeamDAOImpl();
        //teamDAO.addTeam(team1);
        //teamDAO.addTeam(team2);

        match = new Match(team1, team2);
        matchDAO = new MatchDAOImpl();
        matchDAO.setEm(em);
        matchDAO.setRefereedb(refereeDAO);
        //matchDAO.insertMatch(match);
        
        matchNew = new Match(team1, team2);
    }

    /*
     * addRefereeToMatch function TEST
     */
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId() throws Exception {

        matchDAO.addRefereeToMatch("AAA", referee.getNif());
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif() throws Exception {

        matchDAO.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }

    //@Test
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

    //@Test
    public void findMatch_CorrectId() throws Exception {
        //Introduir un Match-->insertMatch(Match);
        matchDAO.insertMatch(matchNew);
        assertEquals(matchNew, matchDAO.findMatchById(matchNew.getIdMatch()));
    }

    private EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {

        em = refereeDAO.getEM();
        if (em.isOpen()) {
            em.close();
        }

        em = matchDAO.getEm();
        if (em.isOpen()) {
            em.close();
        }


        em = getEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM Referee");
        Query query2 = em.createQuery("DELETE FROM Match");
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();

        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");

    }
}