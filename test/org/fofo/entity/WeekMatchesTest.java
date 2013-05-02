package org.fofo.entity;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author oriol i Jordi
 */

@RunWith(JMock.class)
public class WeekMatchesTest {
    Mockery context = new JUnit4Mockery();
    
    Match match;
    WeekMatches wm;
    
    public WeekMatchesTest() {
    }

    @Before
    public void setUp() {
        match = context.mock(Match.class, "match");
        wm = new WeekMatches();
    }

    @Test
    public void testUnknownMatchException(){
        try {
            Match result = wm.getMatch("AAA");
            fail();
        } catch (Exception ex) {
        }
    }
    
    //@Test
    public void testAddAndGetOneMatch() throws UnknownMatchException {        
        wm.addMatch("AAA", match);
        Match result = wm.getMatch("AAA");
        assertEquals("AAA",result.getId());
    }

}
