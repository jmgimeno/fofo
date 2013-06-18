/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import org.fofo.services.management.exception.NumberOfTeamsException;
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

        for (WeekMatch wm : calendar.getAllWeekMatches()) {
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
    
//    @Test
//    public void eachRoundHasCorrectNumOfMatchesForEachTeam(){
//        int expected = compOK.getNumberOfTeams()-1;
//        
//        //MOLT COSTÒS. ALGU ALTRE (MATCH, TEAM...) ENS HAURIA D'AJUDAR A 
//        //OBTINDRE EL NOMBRE DE PARTITS QUE HA JUGAT UN EQUIP  
//    }
    
    /** 
     * A team plays once in a week match   
     */
    @Test 
    public void testCorrectWeekMatches() throws Exception{
        
        int nteams = compOK.getNumberOfTeams();
        String firstTeam = compOK.getTeams().get(0).getName();
        
        List<String> listTeams = getListNamesOfTeams(compOK.getTeams());
        
        
        for (int i=0; i<nteams-1;i++){
            
            Team teamAgainst = getTeamAgainst(firstTeam,calendar.getWeekMatch(i).getListOfWeekMatches());
            if (!listTeams.remove(teamAgainst.getName())) throw new Exception();
        }        
        
        List<String> lteams = new ArrayList<String>();
        lteams.add(firstTeam);
        
        assertEquals("There should reamin only the first team",
                      lteams,listTeams);
            
        
    }
    
    /** 
     * A team plays once  against each oponent 
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
        compOK = (CompetitionLeague) Competition.create(CompetitionType.LEAGUE);
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
                          first.get(i).getHome(),second.get(i).getVisitor());
            assertEquals("visiting of first should be equal to home of second",
                         first.get(i).getVisitor(),second.get(i).getHome());
            
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
            
            if (match.getHome().getName().equals(firstTeam)){
                
                return match.getVisitor();
            }
            if (match.getVisitor().getName().equals(firstTeam)){
                
                return match.getHome();
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