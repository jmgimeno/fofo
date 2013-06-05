package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
 * @author auc2
 */
@RunWith(JMock.class)
public class MatchDAOImplTest {

    private Mockery context = new JUnit4Mockery();
    private EntityManager em;
    private MatchDAOImpl matchdao;
    private Match match1;
    private Match matchdb;
    private Referee referee1;
    private RefereeDAO refereedao;
    private Team team1, team2;
    private EntityTransaction transaction;

    @Before
    public void setUp() throws Exception {

        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        refereedao = context.mock(RefereeDAO.class);
        
        refereedao.setEM(em);
        
              
        referee1 = new Referee();
        referee1.setName("Pepito");
        referee1.setNif("123456A");
        
        team1 = new Team("Team1", Category.FEMALE);

        team2 = new Team("Team2", Category.FEMALE);

        
        match1 = new Match(team1, team2);
        match1.setReferee(referee1);
       
        matchdao = new MatchDAOImpl();
        matchdao.setEm(em);  
        matchdao.setRefereedb(refereedao);
        

    }

    
    //@Test(expected = PersistException.class)
    public void MatchNotHasId() throws Exception {
        
      
          context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match1.getIdMatch()); will(returnValue(null));
                oneOf(em).getTransaction().commit();
            }
           
          });
          
        matchdao.findMatchById(match1.getIdMatch());
    }
    
   // @Test
    public void findMatchById() throws Exception {
        
       context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match1.getIdMatch()); will(returnValue(match1));
                oneOf(em).getTransaction().commit();

            }
           
          });
          
        matchdao.findMatchById(match1.getIdMatch());
    }
    
    
    //@Test(expected = PersistException.class)
    public void NotMatch() throws Exception {

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match1.getIdMatch()); will(returnValue(null));


            }
        });

        matchdao.addRefereeToMatch(match1.getIdMatch(), referee1.getNif());


    }

    //@Test(expected = PersistException.class)
    public void NotReferee() throws Exception {

//        referee1.setNif(null);
        
        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, match1.getIdMatch()); will(returnValue(match1));
                oneOf(refereedao).findRefereeByNif(referee1.getNif()); will(returnValue(null));


            }
        });

        matchdao.addRefereeToMatch(match1.getIdMatch(), referee1.getNif());


    }

   // @Test
    public void CorrectAddRefereeToMatch() throws Exception {

        final String idMatch = "1234";

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, idMatch);
                will(returnValue(match1));
                oneOf(em).find(Referee.class, referee1.getNif());
                will(returnValue(referee1));
                oneOf(matchdb).setReferee(referee1);
                oneOf(em).getTransaction().commit();

            }
        });

        matchdao.addRefereeToMatch(idMatch, referee1.getNif());


    }
}