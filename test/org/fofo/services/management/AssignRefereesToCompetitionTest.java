package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import org.fofo.dao.*;
import org.fofo.dao.MatchDAO;
import org.fofo.entity.*;
import org.fofo.services.management.AssignReferees;
import org.fofo.services.management.AssignReferees;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 *
 * @author Jordi Niubo i Oriol Capell
 */

@RunWith(JMock.class)
public class AssignRefereesToCompetitionTest {
    AssignReferees  service;   
    Mockery context = new JUnit4Mockery();
    CalendarDAO calendarDao;
    RefereeDAO refereeDao;  
    MatchDAO matchDao;
    
    Competition comp;   
    WeekMatch wm1, wm2, wm3, wm4;  
    Match match1, match2, match3, match4;
    Team team1, team2, team3, team4;
    
    public AssignRefereesToCompetitionTest() {
    }

    @Before
    public void setUp() throws Exception {     
        service = new AssignReferees();        
        calendarDao = context.mock(CalendarDAO.class);    
        refereeDao = context.mock(RefereeDAO.class);     
        matchDao = context.mock(MatchDAO.class); 
        
        comp = Competition.create(CompetitionType.LEAGUE);        
        createCompetition();
    }


    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoDAO() throws Exception {
        service.assignRefereesToCompetition(comp);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoRefereeDAO() throws Exception {
        service.setCalendarDao(calendarDao);
        service.assignRefereesToCompetition(comp);
    }    
    
    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoCalendarDAO() throws Exception {
        service.setRefereeDao(refereeDao);
        service.assignRefereesToCompetition(comp);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoMatchDAO() throws Exception {
        service.setMatchDao(matchDao);
        service.assignRefereesToCompetition(comp);
    }    
    

    
    @Test(expected= CompetitionWithoutFCalendarException.class)
    public void testAssignRefereesToCompetitionWithNoFCalendar() throws Exception{    
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(null));
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }

    @Test(expected= IncorrectFCalendarException.class)
    public void testAssignRefereesToCompetitionWithFCalendarWithNoWeekMatch() throws Exception{  
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        final FCalendar calendar = new FCalendar(); 
        
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
            }
        }); 
        comp.setFcalendar(calendar);
        service.assignRefereesToCompetition(comp);
    }    
    
    @Test(expected= PersistException.class)
    public void testAssignRefereesInWeekMatchWithNoMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        final FCalendar calendar = new FCalendar();    
        calendar.getAllWeekMatches().add(wm4);
        
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }
    
    @Test(expected= InsuficientRefereesException.class)
    public void testInsuficientRefereesFor1WeekMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        final FCalendar calendar = new FCalendar();    
        calendar.getAllWeekMatches().add(wm1);
        
        final List<Referee> listReferee = new ArrayList<Referee>(); 

        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
                oneOf(refereeDao).getAllReferees();
                                    will(returnValue(listReferee));               
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }  
    
    @Test
    public void testAssignRefereesInOneWeekMatchWithOneMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        final FCalendar calendar = new FCalendar();    
        calendar.getAllWeekMatches().add(wm1);
        
        final List<Referee> listReferee = new ArrayList<Referee>(); 
        Referee referee = new Referee("47935051S", "Jordi");
        listReferee.add(referee);
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
                oneOf(refereeDao).getAllReferees();
                                    will(returnValue(listReferee));
                oneOf(matchDao).addRefereeToMatch(match1.getIdMatch(), "47935051S");                    
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }     
    
    @Test
    public void testAssignRefereesInOneWeekMatchWithTwoMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        final FCalendar calendar = new FCalendar();    
        calendar.getAllWeekMatches().add(wm2);
        
        final List<Referee> listReferee = new ArrayList<Referee>(); 
        Referee referee1 = new Referee("47935051S", "Jordi");
        listReferee.add(referee1);   
        Referee referee2 = new Referee("ABCDEE", "Oriol");   
        listReferee.add(referee2);
        
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
                oneOf(refereeDao).getAllReferees();
                                    will(returnValue(listReferee));
                oneOf(matchDao).addRefereeToMatch(match2.getIdMatch(), "47935051S");              
                oneOf(matchDao).addRefereeToMatch(match3.getIdMatch(), "ABCDEE");                    
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }    
    
    @Test(expected= InsuficientRefereesException.class)
    public void testInsuficientRefereesFor2WeekMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
                final FCalendar calendar = new FCalendar();    
        calendar.getAllWeekMatches().add(wm1);
        calendar.getAllWeekMatches().add(wm2);
        
        final List<Referee> listReferee = new ArrayList<Referee>(); 
        Referee referee1 = new Referee("47935051S", "Jordi");
        listReferee.add(referee1);   
        
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
                oneOf(refereeDao).getAllReferees();
                                    will(returnValue(listReferee));               
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }      
    
    @Test
    public void testAssignRefereesFor2WeekMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        
        final FCalendar calendar = new FCalendar();   
        calendar.getAllWeekMatches().add(wm1);
        calendar.getAllWeekMatches().add(wm2);
        
        final List<Referee> listReferee = new ArrayList<Referee>(); 
        Referee referee1 = new Referee("47935051S", "Jordi");
        listReferee.add(referee1);   
        Referee referee2 = new Referee("ABCDEE", "Oriol");     
        listReferee.add(referee2);     
        
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
                oneOf(refereeDao).getAllReferees();
                                    will(returnValue(listReferee));
                oneOf(matchDao).addRefereeToMatch(match1.getIdMatch(), "47935051S");                                     
                oneOf(matchDao).addRefereeToMatch(match2.getIdMatch(), "47935051S");              
                oneOf(matchDao).addRefereeToMatch(match3.getIdMatch(), "ABCDEE");                    
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }
    
    
    @Test
    public void testAssignRefereesForWeekMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        
        WeekMatch wm5 = new WeekMatch();
        wm5.addMatch(match1); 
        wm5.addMatch(match2); 
        wm5.addMatch(match3); 
        wm5.addMatch(match4);   
                
        final FCalendar calendar = new FCalendar();   
        calendar.getAllWeekMatches().add(wm1);
        calendar.getAllWeekMatches().add(wm2);
        calendar.getAllWeekMatches().add(wm5);

        
        
        final List<Referee> listReferee = new ArrayList<Referee>(); 
        Referee referee1 = new Referee("47935051S", "Jordi");
        listReferee.add(referee1);   
        Referee referee2 = new Referee("ABCDEF", "Oriol");     
        listReferee.add(referee2);
        Referee referee3 = new Referee("XYZ123", "Number3");     
        listReferee.add(referee3);  
        Referee referee4 = new Referee("12345678A", "MR 123");     
        listReferee.add(referee4);  
        
        
        context.checking(new Expectations() {
            {
                oneOf(calendarDao).findFCalendarByCompetitionName(comp.getName());
                                    will(returnValue(calendar));
                oneOf(refereeDao).getAllReferees();
                                    will(returnValue(listReferee));                                    
                oneOf(matchDao).addRefereeToMatch(match1.getIdMatch(), "47935051S");                                     
                oneOf(matchDao).addRefereeToMatch(match2.getIdMatch(), "47935051S");              
                oneOf(matchDao).addRefereeToMatch(match3.getIdMatch(), "ABCDEF");   
                
                oneOf(matchDao).addRefereeToMatch(match1.getIdMatch(), "47935051S");              
                oneOf(matchDao).addRefereeToMatch(match2.getIdMatch(), "ABCDEF");              
                oneOf(matchDao).addRefereeToMatch(match3.getIdMatch(), "XYZ123");              
                oneOf(matchDao).addRefereeToMatch(match4.getIdMatch(), "12345678A");
                
            }
        }); 
        service.assignRefereesToCompetition(comp);
    }    
    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    private void createCompetition() {   
        comp.setName("Competition 1");
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);    
        comp.setInici(new DateTime().minusDays(8).toDate());         

        createTeams();
        createWeekmatch();    
    }

    private void createTeams() {
        team1 = new Team("Team1", Category.FEMALE);
        team1.setEmail("Team1@hotmail.com");
        comp.addTeam(team1);

        team2 = new Team("Team2", Category.FEMALE);
        team2.setEmail("Team2@hotmail.com");
        comp.addTeam(team2);
        
        team3 = new Team("Team3", Category.FEMALE);
        team3.setEmail("Team3@hotmail.com");
        comp.addTeam(team3);
        
        team4 = new Team("Team4", Category.FEMALE);
        team4.setEmail("Team4@hotmail.com");  
        comp.addTeam(team4);
    }



    private void createWeekmatch() {
        createMatchs();
        
        wm1 = new WeekMatch();
        wm1.addMatch(match1);

        wm2 = new WeekMatch();
        wm2.addMatch(match2);
        wm2.addMatch(match3);

        wm3 = new WeekMatch();
        wm3.addMatch(match3);
        wm3.addMatch(match4);

        wm4 = new WeekMatch();
    }
    
    private void createMatchs() {
        match1 = new Match();
        match1.setHome(team1);
        match1.setVisitor(team2);

        match2 = new Match();
        match2.setHome(team3);
        match2.setVisitor(team4);

        match3 = new Match();
        match3.setHome(team2);
        match3.setVisitor(team1);

        match4 = new Match();
        match4.setHome(team4);
        match4.setVisitor(team3);
    }
    
}