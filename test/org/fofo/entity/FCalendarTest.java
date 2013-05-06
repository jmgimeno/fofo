/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.joda.time.*;
import static org.junit.Assert.*;

/**
 *
 * @author Anatoli, Mohamed
 */

/************************/
/* Calendar LEAGUE test */
/************************/

public class FCalendarTest {
    
    private FCalendar calendar;
    private Competition compOK;     
    
    public FCalendarTest() {
    }
    
    @Before
    public void setUp() throws Exception { 
        setUpCompOK(); 
        
        calendar = new FCalendar(compOK);   
        
    }    
    
    
    @Test (expected = MinimumDaysException.class)
    public void lessThanSevenDaysBetwenCalendarAndCompetition() throws Exception{
        Competition compKO = compOK;
        compOK.setInici(new DateTime().minusDays(6).toDate());
        
        new FCalendar(compKO);
        fail();
        
    }
    
    @Test (expected = NumberOfTeamsException.class)
    public void numOfTeamsIsNotPair() throws Exception{
        Competition compKO = compOK;
        List<Team> notPairTeams = compOK.getTeams();
        notPairTeams.remove(0); //10-1 = 9 equips
        compKO.setTeams(notPairTeams);
        
        new FCalendar(compKO);
        fail();
        
    }
    
    @Test (expected = NumberOfTeamsException.class)
    public void notEnoughTeams() throws Exception{
        Competition compKO = compOK;
        List<Team> lessThanMinTeams = new ArrayList<Team>();
        for(int i=1; i<compOK.getMinTeams(); i++)
            lessThanMinTeams.add(new Team("Team"+1,Category.MALE));        
        compKO.setTeams(lessThanMinTeams);
        
        new FCalendar(compKO);
        fail();
    }
    
    @Test (expected = NumberOfTeamsException.class)
    public void excessOfTeams() throws Exception{
        Competition compKO = compOK;
        List<Team> moreThanMaxTeams = new ArrayList<Team>();
        for(int i=1; i<=compOK.getMaxTeams()+1; i++)
            moreThanMaxTeams.add(new Team("Team "+i,Category.MALE));        
        compKO.setTeams(moreThanMaxTeams);
        
        new FCalendar(compKO);
        fail();
    }
    
    
    @Test 
    public void WeekMatchesHasCorrectNumOfMatches(){
        int expected = compOK.getNumberOfTeams()/2;
        
        for(WeekMatches wm : calendar.getAllWeekMatches())
            assertEquals(expected, wm.getNumberOfMatchs());
    }
    

    private void setUpCompOK() {
        compOK = Competition.create(Type.LEAGUE);
        compOK.setCategory(Category.MALE);
        compOK.setMinTeams(2);
        compOK.setMaxTeams(20);
        compOK.setInici(new DateTime().minusDays(8).toDate());
        List<Team> teams = new ArrayList<Team>();
        for(int i=1; i<=10; i++)
            teams.add(new Team("Team "+i,Category.MALE));
        compOK.setTeams(teams);
    }
    
    
    
    
}