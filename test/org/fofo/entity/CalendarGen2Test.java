/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author josepma
 */
public class CalendarGen2Test {
    
    Competition comp;
    CalendarLeagueGen calcal;

    
    
    @Before
    public void setup() throws Exception{
    
        comp = new CompetitionLeague();

        
        

        java.util.Calendar gcal = new java.util.GregorianCalendar();
        
        gcal.set(10,11,2013);
        
        
        Club club = new Club("Nom club");
        
        comp.setName("League1");
        comp.setInici(gcal.getTime());
        comp.setCategory(Category.MALE);
        comp.setType(Type.LEAGUE);
        
        
        
        
        comp.getTeams().add(new Team("team0",club,Category.MALE));
        comp.getTeams().add(new Team("team1",club,Category.MALE));
        comp.getTeams().add(new Team("team2",club,Category.MALE));
        comp.getTeams().add(new Team("team3",club,Category.MALE));
        comp.getTeams().add(new Team("team4",club,Category.MALE));
        comp.getTeams().add(new Team("team5",club,Category.MALE));
        

       calcal = new CalendarLeagueGen(comp);

        
        
    }
    
    
    
    public CalendarGen2Test() {

    
    
    }

    
    /**
     * 
     * The competition is a league competition
     * 
     * 
     * 
     */
    
    //@Test(expected=IncorrectCompetitionTypeException.class)
    public void testLeagueCompetition() throws Exception{

        comp.setType(Type.CUP);

        this.calcal.calculateCalendar(comp);
         
        
    }
    
    
   /**
    * 
    * The competition contains at least 2 teams 
    * 
    * 
    */ 
    
   // @Test(expected=NumberOfTeamsException.class)
    public void testMoreThanOneTeam() throws Exception{
    
        List<Team> teams = new ArrayList<Team>();
        teams.add(new Team());
        
        comp.setTeams(teams);
              
        calcal.calculateCalendar(comp);
        
        
        
    }
    
    /**
     * 
     * The competition contains an even number of teams
     * 
     * 
     */
    
     @Test(expected=NumberOfTeamsException.class)
     public void testEvenTeams() throws Exception{
     
         comp.getTeams().add(new Team());
         
         calcal.calculateCalendar(comp);
        
        
    }
    
     
    /**
     * The number of weeks of the calendar is 2*(nTeams-1).
     * 
     */
    @Test
    public void testCorrectWeekNb() throws Exception{
    
        
       FCalendar cal =  calcal.calculateCalendar(comp);
       
       assertEquals("Incorrect number of weeks",2*(comp.getNumberOfTeams()-1),
                    cal.getAllWeekMatches().size());
       
    }
    
    
    
    /**
     * 
     * In the first round (nteams-1 weeks) thwe first team plays once with the 
     * remaining teams. 
     * 
     * 
     * 
     */
    
    @Test
    public void testFirstTeamFirstRound() throws Exception{
    
        FCalendar cal =  calcal.calculateCalendar(comp);
    
        int nteams = comp.getNumberOfTeams();
        String firstTeam = comp.getTeams().get(0).getName();
        
        List<String> listTeams = getListNamesOfTeams(comp.getTeams());
        
        
        for (int i=0; i<nteams-1;i++){
            
            Team teamAgainst = getTeamAgainst(firstTeam,cal.getAllWeekMatches().get(i).getListOfWeekMatches());
            if (!listTeams.remove(teamAgainst.getName())) throw new Exception();
        }        
        
        List<String> lteams = new ArrayList<String>();
        lteams.add(firstTeam);
        
        assertEquals("There should reamin only the first team",
                      lteams,listTeams);
        
    }
    
   
    /**
     * 
     * In the first nTeams-1 weeks (i.e., the first round), each team plays with 
     * the remaining of them.
     * 
     * 
     */
    @Test
    public void testCorrectRound() throws Exception{

        FCalendar cal =  calcal.calculateCalendar(comp);

        
        for (int i = 0; i< comp.getNumberOfTeams();i++){
            String team = comp.getTeams().get(i).getName();
            List<String> listTeams = removeOpponents(cal, team); 
    
            List<String> lteams = new ArrayList<String>();
            lteams.add(team);
        
           assertEquals("There should remain only this team",
                      lteams,listTeams);
                
            
        }
        
        
    }
    
    
    
    /**
     * 
     * Second round is as first round with the only difference of a change in 
     * and visiting teams in each match.
     * 
     * 
     */
    //@Test
    public void testSecondRound() throws Exception{
        
        FCalendar cal =  calcal.calculateCalendar(comp);

        int nteams = comp.getNumberOfTeams();
        
        for (int i = 0 ; i<nteams-1;i++){
            compareRounds(cal.getAllWeekMatches().get(i).getListOfWeekMatches(),
                    cal.getAllWeekMatches().get(i+nteams-1).getListOfWeekMatches());
        }    
            
            
    }
        
      
        
        
    
    
   /*
    * 
    * 
    * PRIVATE OPERATIONS......
    * 
    * 
    */ 
    
    
    private void compareRounds(List<Match> first, List<Match> second){
        
        for (int i = 0; i<first.size();i++ ){
            
            assertEquals("home of first should be equal to visiting of second",
                          first.get(i).getLocal(),second.get(i).getVisitant());
            assertEquals("visiting of first should be equal to home of second",
                         first.get(i).getVisitant(),second.get(i).getLocal());
            
        }
        
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

    private List<String> removeOpponents(FCalendar cal, String team) throws Exception{ 
    
            Competition comp = cal.getCompetition();
            List<String> listTeams = getListNamesOfTeams(comp.getTeams());
        
            int nteams = comp.getNumberOfTeams();
            
        
        //System.out.println("*****first="+firstTeam+"list="+listTeams);
        
        
        for (int i=0; i<nteams-1;i++){
            Team teamAgainst = getTeamAgainst(team,cal.getAllWeekMatches().get(i).getListOfWeekMatches());
                 
            if (!listTeams.remove(teamAgainst.getName())) throw new Exception();
        }        
                
        return listTeams;    
            
            
    }        

    
    

    
    
    
    
}
