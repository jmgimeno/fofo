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

/**
 *
 * @author jnp2
 */
@RunWith(JMock.class)
public class CalendarCalculatorServiceTest { 
    
    CalendarCalculatorService  service;
    Mockery context = new JUnit4Mockery(); 
    
    CalendarDAO cdao;   
    Competition comp;
    FCalendar calendar;
    
    
    public CalendarCalculatorServiceTest() {
    }

    @Before
    public void setUp() throws Exception{
        service = new CalendarCalculatorService();        
        cdao = context.mock(CalendarDAO.class);         
      
        comp = Competition.create(Type.CUP);
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        Club club = new Club();
        club.setName("AA");
        List<Team> list = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            list.add(new Team("Team number "+i,club, Category.MALE));
        }
        comp.setTeams(list);
        comp.setInici(new DateTime().minusDays(8).toDate()); 
        
       CalendarGen calGen = new CalendarLeagueGen(comp);
       calendar = calGen.calculateCalendar();   
    }

    @Test
    public void testCalculateAndStoreLeagueCalendar() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(cdao).addCalendar(calendar);
            }
        });        
        
        
        service.setCalendarDao(cdao);
        service.calculateAndStoreLeagueCalendar(comp);
    }
    
    @Test
    public void testCalculateAndStoreCupCalendar() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(cdao).addCalendar(calendar);
            }
        }); 
        
        service.setCalendarDao(cdao);
        service.calculateAndStoreCupCalendar(comp);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testIfServiceNoHaveCalendarDaoIncalculateAndStoreCupCalendar() throws Exception {
        service.calculateAndStoreCupCalendar(comp);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testIfServiceNoHaveCalendarDaoIncalculateAndStoreLeagueCalendar() throws Exception {
        service.calculateAndStoreCupCalendar(comp);
    }
    
    
}
