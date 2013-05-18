/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import org.fofo.services.management.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.fofo.entity.*;
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
    private CompetitionLeague compOK;

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
    public void eachWeekMatchesHasCorrectNumOfMatches() throws Exception {
        int expected = compOK.getNumberOfTeams() / 2;

        for (WeekMatches wm : calendar.getAllWeekMatches()) {
            assertEquals(expected, wm.getNumberOfMatchs());
        }
        
    }    
    
    
    @Test 
    public void eachRoundHasCorrectNumOfWeekMatches() throws Exception{
        
        int expected = compOK.getNumberOfTeams()-1;
        
        for(int i=1; i<=2; i++){
            int result = generator.getWeekMatchesByRound(i).size();
            assertEquals(expected,result);
        }
    }
    
    
    
    /** 
     * A team plays once in a week match   
     */
//    @Test 
//    public void testCorrectWeekMatches(){
//        
//        //Seria exactament lo mateix que testCorrectRound... no?
//            
//        
//    }
    
    /** 
     * A team plays onece  against each oponent 
     */
    @Test
    public void testCorrectRound() throws Exception{
        for (int i = 0; i< compOK.getNumberOfTeams();i++){ 
            String team = compOK.getTeams().get(i).getName();
            List<String> result = removeOpponents(calendar, team); 
    
            List<String> expected = new ArrayList<String>();
            expected.add(team);
        
            assertEquals("There should remain only this team",
                      expected,result);           
        }
    }
    
    /** 
     * Second round is as first round with the only difference of a change in 
     * locals and visiting teams in each match.     
     */
    @Test 
    public void testSecondRound() {
        int nteams = compOK.getNumberOfTeams();
        
        for (int i = 0 ; i<nteams-1;i++){
            compareRounds(calendar.getWeekMatch(i).getListOfWeekMatches(),
                    calendar.getWeekMatch(i+nteams-1).getListOfWeekMatches());
        } 
    }
    
    
    /* PRIVATE OPS */
    
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
    
    private void compareRounds(List<Match> first, List<Match> second){
        
        for (int i = 0; i<first.size();i++ ){
            
            assertEquals("home of first should be equal to visiting of second",
                          first.get(i).getLocal(),second.get(i).getVisitant());
            assertEquals("visiting of first should be equal to home of second",
                         first.get(i).getVisitant(),second.get(i).getLocal());
            
        }
        
    }
    
    private List<String> removeOpponents(FCalendar cal, String team) throws Exception{ 
    
        Competition comp = cal.getCompetition();
        List<String> listTeams = getListNamesOfTeams(comp.getTeams());

        int nteams = comp.getNumberOfTeams();        
        
        for (int i=0; i<nteams-1;i++){
            Team teamAgainst = getTeamAgainst(team,cal.getWeekMatch(i).getListOfWeekMatches());
                 
            if (!listTeams.remove(teamAgainst.getName())) throw new Exception();
        }        
                
        return listTeams;    
            
            
    }        
    
    private Team getTeamAgainst(String firstTeam, List<Match> matches) throws Exception{
        

        for (Match match: matches){
            
            if (match.getLocal().getName().equals(firstTeam)){
                
                return match.getVisitant();
            }
            if (match.getVisitant().getName().equals(firstTeam)){
                
                return match.getLocal();
            }    
        }        
                
        throw new Exception();        
                
         
    }
    
    private List<String> getListNamesOfTeams(List<Team> teams){
        
        List<String> result = new ArrayList<String>();
        
        for (Team team : teams){
            result.add(team.getName());
        }
        return result;
    }
}