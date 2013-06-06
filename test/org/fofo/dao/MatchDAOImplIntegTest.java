package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.fofo.entity.FCalendar;
import org.fofo.entity.WeekMatch;
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
    private MatchDAOImpl matchdao;
    private RefereeDAO refereedao;
    private Match match;
    private Referee referee;
    private Team team1, team2;

    @Before
    public void setUp() throws Exception {
        
        em = getEntityManager();
        matchdao = new MatchDAOImpl();
        matchdao.setRefereedb(refereedao);
        matchdao.setEm(em);
        
        //em = getEntityManager();


        
        referee = new Referee("12345678A", "Pepito");

        team1 = new Team("Team1", Category.FEMALE);
        team2 = new Team("Team2", Category.FEMALE);

        match = new Match(team1, team2);
    }

//addRefereeToMatch TEST    
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId() throws Exception {

        matchdao.addRefereeToMatch("AAA", referee.getNif());
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif() throws Exception {

        matchdao.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }

    //@Test
    public void addRefereeToMatch_correct() throws Exception {
       
        //Introduir un Match-->insertMatch(Match);  
        matchdao.addRefereeToMatch(match.getIdMatch(), referee.getNif());
        assertEquals(referee, match.getReferee());
    }

//findMatchById TEST  
    
    @Test(expected =  IncorrectMatchException.class)
    public void findMatch_IncorrectId() throws Exception {
        
        matchdao.findMatchById("AAA");
    }

   // @Test
    public void findMatch_CorrectId() throws Exception {
        //Introduir un Match-->insertMatch(Match);
        assertEquals(match, matchdao.findMatchById(match.getIdMatch()));
    }
    
    
      private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }
      
}