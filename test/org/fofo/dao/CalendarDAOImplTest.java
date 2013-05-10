/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.FCalendar;
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
public class CalendarDAOImplTest {
    
    Mockery context = new JUnit4Mockery();
    
    EntityManager em;
    EntityTransaction transaction;
    FCalendar fcal;
    
    CalendarDAOImpl calDAO;
    
    
    
    @Before
    public void setUp() {
        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
          
    }
    
    
    @Test
    public void testAddMatches(){
         
        context.checking(new Expectations() {{             
           
             //   oneOf(em).persist(em.get(0));              
               // oneOf(em).persist(em.get(1));
                oneOf(em).close();
        }});
    
        calDAO.addCalendar(fcal);       
    }
    
    
    
}
