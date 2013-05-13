/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;


import java.util.List;
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
    
    CalendarDAOImpl calDAO;
    
    Match match1, match2, match3, match4, match5;
    WeekMatches wm, wm2, wm3;
    FCalendar fcal, cal, cal2;
    

    @Before
    public void setUp() throws Exception {
        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        
        calDAO = new CalendarDAOImpl(); 
        calDAO.setEm(em);
        
        match1 = new Match();
        match2 = new Match();
        match3 = new Match();
        match4 = new Match();
               
        
        wm.addMatch(match1);
        wm.addMatch(match2);
        
        wm2.addMatch(match3);
        wm2.addMatch(match4);
        
        cal = new FCalendar();
          
    }
    
    /**
     * One Calendar cal, with just 1 wm and 1 match.
     * @throws Exception 
     */
    @Test
    public void testAdditionOfJustOneMatch() throws Exception{

        List<WeekMatches> Lwm = null;
        
        cal2 = new FCalendar();
        wm3.addMatch(match5);        
        Lwm.add(wm3);
        cal2.setWeekMatches(Lwm);
      
        
        context.checking(new Expectations() {{                   
            
            oneOf (em).getTransaction(); will(returnValue(transaction));
            oneOf (transaction).begin();
            oneOf (em).getTransaction(); will(returnValue(transaction));
            oneOf (transaction).commit();
            oneOf (em).persist(cal2);
            oneOf (em).persist(cal2.getAllWeekMatches().get(0));
            oneOf (em).persist(match5);
            
        }});
        
        calDAO.addCalendar(cal2);
    }

    /**
     * 
     * Various matches in one WM.
     */
    @Test
    public void testAdditionOfVariousMatchesOneWM() throws Exception{
  
        List<WeekMatches> Lwm = null;
        cal2 = new FCalendar();
        
         WeekMatches wm4 = null;       
         Match match6 = new Match();  
         Match match7 = new Match();  
         Match match8 = new Match();  
         Match match9 = new Match();  
         
         wm4.addMatch(match6);
         wm4.addMatch(match7);
         wm4.addMatch(match8);
         wm4.addMatch(match9);
            
         Lwm.add(wm4);

         cal2.setWeekMatches(Lwm);
         
         
            context.checking(new Expectations() {{                   
            
            oneOf (em).getTransaction(); will(returnValue(transaction));
            oneOf (transaction).begin();
            oneOf (em).getTransaction(); will(returnValue(transaction));
            oneOf (transaction).commit();
            oneOf (em).persist(cal2);
            oneOf (em).persist(cal2.getAllWeekMatches().get(0));
            oneOf (em).persist(match5);
            
        }});
        
        calDAO.addCalendar(cal2);
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
