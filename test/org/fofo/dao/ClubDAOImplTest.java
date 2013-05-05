/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.Club;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author RogerTorra
 */
@RunWith(JMock.class)
public class ClubDAOImplTest {
    
    Mockery context = new JUnit4Mockery();

    ClubDAOImpl cdao;
    
    EntityManager em;
    EntityTransaction transaction;
    
    public ClubDAOImplTest() {
    }
    
    @Before
    public void setUp() {
        cdao = new ClubDAOImpl();        
        em  = context.mock(EntityManager.class);
        cdao.setEM(em);
        transaction = context.mock(EntityTransaction.class);
    }
    
    @Test
    public void testAddClub() throws Exception{
        final Club club = new Club();
        context.checking(new Expectations() {{
                atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
                oneOf (transaction).begin();
                oneOf (em).persist(club);
                oneOf (transaction).commit();
                oneOf (em).isOpen(); will (returnValue(true));
                oneOf (em).close();
        }});
    
        cdao.addClub(club);
    }
}
