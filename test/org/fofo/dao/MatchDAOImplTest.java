package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.Category;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
import org.fofo.entity.Team;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
@RunWith(JMock.class)
public class MatchDAOImplTest {

    private Mockery context = new JUnit4Mockery();
    private EntityManager em;
    private MatchDAOImpl matchdao;
    private RefereeDAO refereedao;
    private Match match, matchNew;
    private Referee referee;
    private Team team1, team2;

    @Before
    public void setUp() throws Exception {
        em = context.mock(EntityManager.class);
        refereedao = context.mock(RefereeDAO.class);

        referee = new Referee("12345678A", "Pepito");

        team1 = new Team("Team1", Category.FEMALE);
        team2 = new Team("Team2", Category.FEMALE);

        match = new Match(team1, team2);

        matchdao = new MatchDAOImpl();
        matchdao.setEm(em);
        matchdao.setRefereedb(refereedao);
        
        
        matchNew = new Match(team1, team2);

        matchdao = new MatchDAOImpl();
        matchdao.setEm(em);
        matchdao.setRefereedb(refereedao);
        
    }

//addRefereeToMatch TEST    
    
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId() throws Exception {
        
        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match.getIdMatch());
                will(returnValue(null));
                oneOf(em).getTransaction().commit();
            }
        });
        matchdao.addRefereeToMatch(match.getIdMatch(), referee.getNif());
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif() throws Exception {
        
        referee.setNif(null);
       
        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match.getIdMatch());
                will(returnValue(match));
                oneOf(em).getTransaction().commit();
                oneOf(refereedao).findRefereeByNif(referee.getNif());
                will(returnValue(null));
            }
        });
        matchdao.addRefereeToMatch(match.getIdMatch(), referee.getNif());
    }

    @Test
    public void addRefereeToMatch_correct() throws Exception {
       
        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match.getIdMatch());
                will(returnValue(match));
                oneOf(em).getTransaction().commit();
                oneOf(refereedao).findRefereeByNif(referee.getNif());
                will(returnValue(referee));
            }
        });
        matchdao.addRefereeToMatch(match.getIdMatch(), referee.getNif());
    }

//findMatchById TEST    
    
    @Test(expected =IncorrectMatchException.class)
    public void findMatch_IncorrectId() throws IncorrectMatchException, PersistException {
       
        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match.getIdMatch());
                will(returnValue(null));
                oneOf(em).getTransaction().commit();
            }
        });

        matchdao.findMatchById(match.getIdMatch());
    }

    @Test
    public void findMatch_CorrectId() throws IncorrectMatchException, PersistException {
      
        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match.getIdMatch());
                will(returnValue(match));
                oneOf(em).getTransaction().commit();

            }
        });

        matchdao.findMatchById(match.getIdMatch());
    }
     
    
   //@Test (expected = MatchIsAlredyInBDException.class)
    public void IncorrectInsertMatch() throws Exception{
        
          context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, matchNew.getIdMatch()); will(returnValue(matchNew));
               // oneOf(em).persist(matchNew);
               // oneOf(em).getTransaction().commit();

            }
        });

        matchdao.insertMatch(matchNew);
        
        
    }
    
    
   //@Test
    public void insertMatch() throws Exception{
        
          context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match.getIdMatch()); will (returnValue(null));
                oneOf(em).persist(matchNew);
                oneOf(em).getTransaction().commit();

            }
        });

        matchdao.insertMatch(matchNew);
        
        
    }
    
}