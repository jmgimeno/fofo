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
        wm = new WeekMatches("J1");
    }

    @Test(expected=NonUniqueIdException.class)
    public void testNonUniqueIdException() throws NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        wm.addMatch(match);
        wm.addMatch(match);
    }
    
    @Test(expected=UnknownMatchException.class)
    public void testUnknownMatchException() throws UnknownMatchException{
        Match result = wm.getMatch("AAA");
    }
    
    @Test(expected=TeamCanPlayOnlyOneMatchForAWeekException.class)
    public void testRepeatedTeams() throws NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        wm.addMatch(match);
        wm.addMatch(repeatedTeamMatch);        
    }
    
    @Test
    public void testGetWeekMatchId(){
        String result = wm.getWeekMatchId();
        assertEquals(result,"J1");
    }
    
    @Test
    public void testAddAndGetOneMatchId() throws UnknownMatchException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException {        
        wm.addMatch(match);
        Match result = wm.getMatch(match.getId());
        assertEquals(result.getId(),match.getId());
    }
    
    @Test
    public void testAddAndGetOneMatchLocalTeam() throws UnknownMatchException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException {        
        wm.addMatch(match);
        Match result = wm.getMatch(match.getId());
        assertEquals(result.getLocal().getName(),team1.getName());
    }
    
    @Test
    public void testAddAndGetOneMatchVisitantTeam() throws UnknownMatchException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException {        
        wm.addMatch(match);
        Match result = wm.getMatch(match.getId());
        assertEquals(result.getVisitant().getName(),team2.getName());
    }
    
    @Test
    public void testGetListOfMatchs() throws NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
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
    public void testGetNumberOfWeekMatchs() throws NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        wm.addMatch(match);
        wm.addMatch(match2);
        wm.addMatch(match3);
        
        int result = wm.getNumberOfMatchs();
        assertEquals(3,result);
    }
}