package org.fofo.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author oriol i Jordi
 */
public class WeekMatchesTest {
    
    Match match, match2, match3;
    WeekMatches wm;
    Team team1, team2;
    
    public WeekMatchesTest() {
    }

    @Before
    public void setUp() {
        team1 = new Team("ABCD",Category.MALE);
        team2 = new Team("DCBA",Category.VETERAN);
        match = new Match(team1, team2,"M1");
        match2 = new Match(team2, team1,"M2");
        match3 = new Match(team1, team2,"M3");
        wm = new WeekMatches("J1");
    }

    @Test(expected=NonUniqueIdException.class)
    public void testNonUniqueIdException() throws NonUniqueIdException{
        wm.addMatch(match);
        wm.addMatch(match);
    }
    
    @Test(expected=UnknownMatchException.class)
    public void testUnknownMatchException() throws UnknownMatchException{
        Match result = wm.getMatch("AAA");
    }
    
    @Test
    public void testGetWeekMatchId(){
        String result = wm.getWeekMatchId();
        assertEquals(result,"J1");
    }
    
    @Test
    public void testAddAndGetOneMatchId() throws UnknownMatchException, NonUniqueIdException {        
        wm.addMatch(match);
        Match result = wm.getMatch("M1");
        assertEquals(result.getId(),"M1");
    }
    
    @Test
    public void testAddAndGetOneMatchLocalTeam() throws UnknownMatchException, NonUniqueIdException {        
        wm.addMatch(match);
        Match result = wm.getMatch("M1");
        assertEquals(result.getLocal().getName(),team1.getName());
    }
    
    @Test
    public void testAddAndGetOneMatchVisitantTeam() throws UnknownMatchException, NonUniqueIdException {        
        wm.addMatch(match);
        Match result = wm.getMatch("M1");
        assertEquals(result.getVisitant().getName(),team2.getName());
    }
    
    @Test
    public void testGetListOfMatchs() throws NonUniqueIdException{
        wm.addMatch(match);
        wm.addMatch(match2);
        wm.addMatch(match3);
        List<Match> list = wm.getListOfWeekMatches();
        
        List<Match> expected = new ArrayList<Match>();
        expected.add(match3);
        expected.add(match2);
        expected.add(match);
        
        Iterator i = list.iterator();
        Iterator j = expected.iterator();        
        
        while(i.hasNext() && j.hasNext()){
            Match m1 = (Match) i.next();
            Match m2 = (Match) j.next();           
            assertEquals(m1.getId(),m2.getId());
        }
    }
    
    @Test
    public void testGetNumberOfWeekMatchs() throws NonUniqueIdException{
        wm.addMatch(match);
        wm.addMatch(match2);
        wm.addMatch(match3);
        
        int result = wm.getNumberOfMatchs();
        assertEquals(3,result);
    }
}