package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import org.fofo.dao.CalendarDAO;
import org.fofo.entity.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author jnp2
 */

@RunWith(JMock.class)
public class CalendarCalculatorServiceCupTest extends CalendarCupGen{ 
    
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
      
        compCup = Competition.create(CompetitionType.CUP);
        compCup.setName("Competition 1");
        
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

    private void createCompetition(Competition comp) {   
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);    
        comp.setInici(new DateTime().minusDays(8).toDate()); 
        
        Club club = new Club();
        club.setName("AA");
        club.setEmail("email@email.com");
        
        for(int i=0; i<16;i++){
            comp.addTeam(new Team("Team number "+i,club, Category.MALE));
        }
    }
    

    
    
}
