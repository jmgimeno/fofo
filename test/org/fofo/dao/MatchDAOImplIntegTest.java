package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.fofo.entity.Category;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
import org.fofo.entity.Team;
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
    private Match match;
    private Referee referee;
    private Team team1, team2;
    private TeamDAO teamDAO;

    @Before
    public void setUp() throws Exception {

        em = getEntityManager();

        referee = new Referee("12345678A", "Pepito");
        refereeDAO = new RefereeDAOImpl();
        refereeDAO.setEM(em);
        refereeDAO.addReferee(referee);

        team1 = new Team("Team1", Category.FEMALE);
        team2 = new Team("Team2", Category.FEMALE);
        teamDAO = new TeamDAOImpl();
        teamDAO.addTeam(team1);
        teamDAO.addTeam(team2);
        
        match = new Match(team1, team2);
        matchDAO = new MatchDAOImpl();
        matchDAO.setRefereedb(refereeDAO);
        matchDAO.setEm(em);
        matchDAO.insertMatch(match);
    }

//addRefereeToMatch TEST    
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId() throws Exception {

        matchDAO.addRefereeToMatch("AAA", referee.getNif());
    }

//    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif() throws Exception {

        matchDAO.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }

  //  @Test
    public void addRefereeToMatch_correct() throws Exception {

        //Introduir un Match-->insertMatch(Match);  
        matchDAO.addRefereeToMatch(match.getIdMatch(), referee.getNif());
        assertEquals(referee, match.getReferee());
    }

//findMatchById TEST  
   // @Test(expected = IncorrectMatchException.class)
    public void findMatch_IncorrectId() throws Exception {

        matchDAO.findMatchById("AAA");
    }

   // @Test
    public void findMatch_CorrectId() throws Exception {
        //Introduir un Match-->insertMatch(Match);
        assertEquals(match, matchDAO.findMatchById(match.getIdMatch()));
    }

    private EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }
}