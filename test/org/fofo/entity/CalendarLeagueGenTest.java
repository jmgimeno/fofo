/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

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
/**
 * *********************
 */
/* Calendar LEAGUE test */
/**
 * *********************
 */
public class CalendarLeagueGenTest {

    private CalendarGen calendar;
    private Competition compOK;

    public CalendarLeagueGenTest() {
    }

    @Before
    public void setUp() throws Exception {
        compOK = Competition.create(Type.LEAGUE);
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

    @Test(expected = MinimumDaysException.class)
    public void lessThanSevenDaysBetwenCalendarAndCompetition() throws Exception {
        calendar = new CalendarGen(compOK);
        compOK.setInici(new DateTime().minusDays(6).toDate());
        calendar.CalculateCalendar();
        fail();
    }

    @Test(expected = NumberOfTeamsException.class)
    public void numOfTeamsIsNotPair() throws Exception {
        calendar = new CalendarGen(compOK);
        Competition compKO = compOK;
        List<Team> notPairTeams = compOK.getTeams();
        notPairTeams.remove(0); //10-1 = 9 equips
        compKO.setTeams(notPairTeams);
        calendar.CalculateCalendar();
       fail();
    }

    /*@Test(expected = NumberOfTeamsException.class)
    public void notEnoughTeams() throws Exception {
        calendar = new CalendarGen(compOK);
        Competition compKO = compOK;
        List<Team> lessThanMinTeams = new ArrayList<Team>();
        for (int i = 1; i < compOK.getMinTeams(); i++) {
            lessThanMinTeams.add(new Team("Team" + 1, Category.MALE));
        }
        compKO.setTeams(lessThanMinTeams);
        calendar.CalculateCalendar();
        fail();
    }*/

    @Test(expected = NumberOfTeamsException.class)
    public void excessOfTeams() throws Exception {
        calendar = new CalendarGen(compOK);
        Competition compKO = compOK;
        List<Team> moreThanMaxTeams = new ArrayList<Team>();
        for (int i = 1; i <= compOK.getMaxTeams() + 1; i++) {
            moreThanMaxTeams.add(new Team("Team " + i, Category.MALE));
        }
        compKO.setTeams(moreThanMaxTeams);
        calendar.CalculateCalendar();
        fail();
    }

    /*@Test
    public void WeekMatchesHasCorrectNumOfMatches() throws Exception {
        calendar = new CalendarGen(compOK);
        FCalendar cal = calendar.CalculateCalendar();
        int expected = compOK.getNumberOfTeams() / 2;

        for (WeekMatches wm : cal.getAllWeekMatches()) {
            assertEquals(expected, wm.getNumberOfMatchs());
        }
        calendar.CalculateCalendar();
    }*/
}