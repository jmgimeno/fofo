package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
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

    Mockery context = new JUnit4Mockery();
    private EntityManager em;
    MatchDAOImpl matchdao;
    Match match1;
    Match matchdb;
    Referee referee1;

    @Before
    public void setUp() throws Exception {

        em = context.mock(EntityManager.class);
              
        referee1.setName("Pepito");
        referee1.setNif("123456A");
        
        match1.setReferee(referee1);
       
        matchdao = new MatchDAOImpl();
        matchdao.setEm(em);
        matchdao.setMatchdb(match1);
        
       



    }

    
    //@Test(expected = PersistException.class)
    public void MatchNotHasId() throws Exception {
        
      final String idMatch = "1234";
      
          context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, idMatch); will(returnValue(null));
                oneOf(em).getTransaction().commit();
            }
           
          });
          
        matchdao.findMatchById(idMatch);
    }
    
    //@Test
    public void findMatchById() throws Exception {
        
      final String idMatch = "1234";
      
          context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, idMatch); will(returnValue(match1));
                oneOf(em).getTransaction().commit();
            }
           
          });
          
        matchdao.findMatchById(idMatch);
    }
    
    
   // @Test(expected = PersistException.class)
    public void NotMatch() throws Exception {

        final String idMatch = "1234";

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, idMatch); will(returnValue(null)); //will(returnValue(match1));
                oneOf(em).getTransaction().commit();
                oneOf(em).find(Referee.class, referee1.getNif());
                will(returnValue(referee1));

            }
        });

        matchdao.addRefereeToMatch(idMatch, referee1.getNif());


    }

    //@Test(expected = PersistException.class)
    public void NotReferee() throws Exception {

        final String idMatch = "1234";

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Match.class, idMatch);
                will(returnValue(match1));
                oneOf(em).find(Referee.class, referee1.getNif());
                will(returnValue(null));

            }
        });

        matchdao.addRefereeToMatch(idMatch, referee1.getNif());


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