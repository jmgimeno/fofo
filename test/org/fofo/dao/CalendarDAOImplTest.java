/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.WeekMatches;
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
public class CalendarDAOImplTest {
    
    Mockery context = new JUnit4Mockery();
    
    EntityManager em;
    EntityTransaction transaction;
    FCalendar fcal;
    
    CalendarDAOImpl calDAO;
    
    Match match1, match2, match3, match4;
    WeekMatches wm, wm2;
    FCalendar cal;
    

    @Before
    public void setUp() throws Exception {
        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);

        //YOU SHOULD CREATE calDAO
        
        //YOU SHOULD CREATE match1...match4.
        
        
        wm.addMatch(match1);
        wm.addMatch(match2);
        
        wm2.addMatch(match3);
        wm2.addMatch(match4);
          
    }
    
    
    @Test
    public void testAdditionOfJustOneMatch(){

         //YOU SHOULD CREATE A CALENDAR cal WITH JUST ONE wm AND 1 match.

        
        context.checking(new Expectations() {{                   
            
            oneOf (em).getTransaction(); will(returnValue(transaction));
            oneOf (transaction).begin();
            oneOf (em).getTransaction(); will(returnValue(transaction));
            oneOf (transaction).commit();
            oneOf (em).persist(cal);
            oneOf (em).persist(cal.getAllWeekMatches().get(0));
            oneOf (em).persist(match1);
            

        }});
        
        calDAO.addCalendar(cal);
    }

    @Test
    public void testAdditionOfVariousMatchesOneWM(){

    }
    
    @Test
    public void testAddVariousWeekMatches(){
              
        context.checking(new Expectations() {{                   
            
             for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {      
           
                oneOf(em).persist(calDAO.get(i, wm));         
                oneOf(em).persist(calDAO.get(i, wm2)); 
                oneOf(em).close();
                
                 }
        }});
    
        calDAO.addCalendar(fcal);       
    }
      
}
