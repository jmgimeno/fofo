/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;


import java.util.ArrayList;
import java.util.List;
import org.fofo.entity.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anatoli
 */
public class CalendarGenTest {

    private Competition compOK;
   
    
    public CalendarGenTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        setUpCompLeagueOK();       
        
    }
    
    @Test (expected = UnknownCompetitionTypeException.class)
    public void unknownCompetitionType() throws Exception{
        Competition compKO = compOK;
        compKO.setType(null);
        
        CalendarGen.checkRequirements(compKO);

    }
    
    @Test (expected = IncorrectCompetitionTypeException.class)
    public void wrongCompetitionTypeForLeagueComp() throws Exception{
        Competition compKO = compOK;
        compKO.setType(Type.CUP);
        
        CalendarGen.checkRequirements(compKO);
    }
    
    @Test (expected = IncorrectCompetitionTypeException.class)
    public void wrongCompetitionTypeForCupComp() throws Exception{
        Competition compKO = new CompetitionCup(Type.LEAGUE);
        
        CalendarGen.checkRequirements(compKO);
    }

    @Test (expected = MinimumDaysException.class)
    public void minimDaysNotPassed() throws Exception {
        Competition compKO = compOK;
        compKO.setInici(new DateTime().minusDays(6).toDate());
        
        CalendarGen.checkRequirements(compKO);
    }
    
    @Test(expected = NumberOfTeamsException.class)
    public void notEnoughTeams() throws Exception {
        Competition compKO = compOK;
        List<Team> lessThanMinTeamsList = new ArrayList<Team>();
        for (int i = 1; i <compOK.getMinTeams(); i++) {   //minTeams-1
            lessThanMinTeamsList.add(new Team("Team" + i, Category.MALE));
        }
        compKO.setTeams(lessThanMinTeamsList);
        
        CalendarGen.checkRequirements(compKO);
        
    }

    @Test(expected = NumberOfTeamsException.class)
    public void excessOfTeams() throws Exception {
        Competition compKO = compOK;
        List<Team> moreThanMaxTeams = new ArrayList<Team>();
        for (int i = 1; i <=compOK.getMaxTeams()+1; i++) {   //maxTeams+1
            moreThanMaxTeams.add(new Team("Team " + i, Category.MALE));
        }
        compKO.setTeams(moreThanMaxTeams);       
        
        CalendarGen.checkRequirements(compKO);
    }
    
    private void setUpCompLeagueOK(){
        compOK = new CompetitionLeague(Type.LEAGUE);
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