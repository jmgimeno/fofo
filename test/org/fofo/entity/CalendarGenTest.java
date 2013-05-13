/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anatoli
 */
public class CalendarGenTest {
    
    //private CalendarGen generator; //No fem us...
    private CompetitionLeague compOK;
    
    public CalendarGenTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        setUpCompOK();
        //generator = new CalendarGen(compOK);
        
    }
    
    @Test (expected = UnknownCompetitionTypeException.class)
    public void unknownCompetitionType() throws Exception{
        Competition compKO = compOK;
        compKO.setType(null);
        
        new CalendarGen(compKO).CalculateCalendar();
        fail();
    }

    @Test (expected = MinimumDaysException.class)
    public void minimDaysNotPassed() throws Exception {
        Competition compKO = compOK;
        compKO.setInici(new DateTime().minusDays(6).toDate());
        
        new CalendarGen(compKO).CalculateCalendar();
        fail();
    }
    
        @Test(expected = NumberOfTeamsException.class)
    public void notEnoughTeams() throws Exception {
        Competition compKO = compOK;
        System.out.println(compKO.getMinTeams());
        List<Team> lessThanMinTeams = new ArrayList<Team>();
        for (int i = 1; i <compOK.getMinTeams(); i++) {   
            lessThanMinTeams.add(new Team("Team" + i, Category.MALE));
        }
        compKO.setTeams(lessThanMinTeams);
        
        new CalendarGen(compKO).CalculateCalendar();
        fail();
    }

    @Test(expected = NumberOfTeamsException.class)
    public void excessOfTeams() throws Exception {
        Competition compKO = compOK;
        List<Team> moreThanMaxTeams = new ArrayList<Team>();
        for (int i = 1; i <=compOK.getMaxTeams()+1; i++) { 
            moreThanMaxTeams.add(new Team("Team " + i, Category.MALE));
        }
        compKO.setTeams(moreThanMaxTeams);       
        
        new CalendarGen(compKO).CalculateCalendar();
        fail();
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