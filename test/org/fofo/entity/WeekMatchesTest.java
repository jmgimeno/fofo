package org.fofo.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author oriol i Jordi, Mohamed
 */
public class WeekMatchesTest {
    
    Match match, match2, match3, repeatedTeamMatch;
    WeekMatches wm;
    Team team1, team2, team3, team4, team5, team6;
    
    public WeekMatchesTest() {
    }

    @Before
    public void setUp() {
        team1 = new Team("ABCD",Category.MALE);
        team2 = new Team("DCBA",Category.VETERAN);
        team3 = new Team("AAAA",Category.MALE);
        team4 = new Team("BBBB",Category.FEMALE);
        team5 = new Team("RRRR",Category.MALE);
        team6 = new Team("SSSS",Category.MALE);
        match = new Match(team1, team2);
        match2 = new Match(team3, team4);
        match3 = new Match(team5, team6);
        repeatedTeamMatch = new Match(team4, team1);
        wm = new WeekMatches();
    }
           
    @Test
    public void testGetListOfMatchs(){
        wm.addMatch(match);
        wm.addMatch(match2);
        wm.addMatch(match3);
        List<Match> list = wm.getListOfWeekMatches();
        
        List<Match> expected = new ArrayList<Match>();
        expected.add(match);
        expected.add(match2);
        expected.add(match3);
        
        Iterator i = list.iterator();       
        while(i.hasNext()){
            Match m1 = (Match) i.next();
            assertTrue(expected.contains(m1));
        }
    }
    
    @Test
    public void testGetNumberOfWeekMatchs(){
        wm.addMatch(match);
        wm.addMatch(match2);
        wm.addMatch(match3);
        
        int result = wm.getNumberOfMatchs();
        assertEquals(3,result);
    }
}