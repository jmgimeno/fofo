/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import org.fofo.services.management.NumberOfTeamsException;
import org.fofo.services.management.CalendarLeagueGen;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.joda.time.*;
import static org.junit.Assert.*;

/**
 *
 * @author Anatoli, Mohamed
 */

public class CalendarLeagueGenTest {

    private CalendarLeagueGen generator;
    private FCalendar calendar;
    private Competition compOK;

    public CalendarLeagueGenTest() {
    }

    @Before
    public void setUp() throws Exception {
        setUpCompOK();
        generator = new CalendarLeagueGen();
        calendar = generator.calculateCalendar(compOK);
    }

    @Test (expected = NumberOfTeamsException.class)
    public void numOfTeamsNotPair() throws Exception{
        Competition compKO = compOK;
        List<Team> notPairTeams = compOK.getTeams();
        notPairTeams.remove(0); //10-1 = 9 teams
        compKO.setTeams(notPairTeams);
        
        new CalendarLeagueGen().calculateCalendar(compKO);        
    }
    
    @Test
    public void oneWeekMatchesHasCorrectNumOfMatches() throws Exception {
        int expected = compOK.getNumberOfTeams() / 2;

        for (WeekMatches wm : calendar.getAllWeekMatches()) {
            assertEquals(expected, wm.getNumberOfMatchs());
        }
        
    }
    
    @Test
    public void oneRoundHasCorrectNumOfWeekMatches() throws Exception{
        
        int expected = compOK.getNumberOfTeams()-1;
        int result = calendar.getNumOfWeekMatches()/2; //2 = num of rounds
        assertEquals(expected,result);
    }
    
    @Test
    public void eachRoundHasTheSameNumOfWeekMatches() throws Exception{
        
        int expected = compOK.getNumberOfTeams()-1;
        
        for(int i=1; i<=2; i++){
            int result = generator.getWeekMatchesByRound(i).size();
            assertEquals(expected,result);
        }
    }
    
    
    @Test
    public void oneTeamPlaysOneTimeForOneWeekMatches(){
        //Necessitem que Team ens dongui el num. de partits jugats
    }
    
    @Test
    public void uniqueEncountersForEachRound(){
        
    }
    
    
    private void setUpCompOK(){
        compOK = (CompetitionLeague) Competition.create(Type.LEAGUE);
        compOK.setCategory(Category.MALE);
        compOK.setMinTeams(2);
        compOK.setMaxTeams(20);
        compOK.setInici(new DateTime().minusDays(8).toDate());
        List<Team> teams = new ArrayList<Team>();
        for (int i = 0; i < 10; i++) {
            teams.add(new Team("Team " + i, Category.MALE));
        }
        compOK.setTeams(teams);
    }
}