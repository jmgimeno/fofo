package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import org.fofo.dao.CalendarDAO;
import org.fofo.entity.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 *
 * @author jnp2
 */

@RunWith(JMock.class)
public class CalendarCalculatorServiceMockTest extends CalendarCupGen{ 
    
    CalendarCalculatorService  service;
    private Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};  
    
    CalendarDAO cdao;   
    Competition compCup;
    CalendarGen calGen;
    FCalendar calendar;
    
    @Before
    public void setUp() throws Exception{
        service = new CalendarCalculatorService();        
        cdao = context.mock(CalendarDAO.class); 
        
        calGen = context.mock(CalendarCupGen.class);       
      
        compCup = Competition.create(Type.CUP);
        
        createCompetition(compCup);
    }

    
    @Test(expected=InvalidRequisitsException.class)
    public void testIfServiceNoHaveCalendarDaoIncalculateAndStoreCupCalendar() throws Exception {
        service.calculateAndStoreCupCalendar(compCup);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testIfServiceNoHaveCalendarGenIncalculateAndStoreCupCalendar() throws Exception {
        service.setCalendarDao(cdao);
        service.calculateAndStoreCupCalendar(compCup);
    }      
       
    @Test
    public void testCalculateAndStoreCupCalendar() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(calGen).calculateCalendar(compCup);will(returnValue(calendar));
                oneOf(cdao).addCalendar(calendar);
            }
        }); 
        
        service.setCalendarDao(cdao);        
        service.setCalendarCupGen(calGen);
        service.calculateAndStoreCupCalendar(compCup);
    }

    private void createCompetition(Competition compCup) {   
        compCup.setCategory(Category.MALE);
        compCup.setInici(null);
        compCup.setMaxTeams(16);
        compCup.setMinTeams(4);
        Club club = new Club();
        club.setName("AA");
        List<Team> list = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            list.add(new Team("Team number "+i,club, Category.MALE));
        }
        compCup.setTeams(list);
        compCup.setInici(new DateTime().minusDays(8).toDate()); 
    }
    

    
    
}