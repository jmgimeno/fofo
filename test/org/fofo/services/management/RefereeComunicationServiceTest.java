/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.MatchDAO;
import org.fofo.dao.RefereeDAO;
import org.fofo.entity.*;
import org.fofo.utils.InfoMatch;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author adr4 & imt1
 */
public class RefereeComunicationServiceTest {
    
    RefereeComunicationService r;
    Match match,m1;
    Referee ref;
    InfoMatch info;
    RefereeDAO refDAO;
    MatchDAO matchDAO;
    Mockery context;
    Competition comp;
    Team local, visitor;
    CompetitionDAO compDAO;
    
    public RefereeComunicationServiceTest() {
    }
    
    @Before
    public void setUp(){
        context = new JUnit4Mockery();
        matchDAO = context.mock(MatchDAO.class);
        refDAO = context.mock(RefereeDAO.class);
        compDAO = context.mock(CompetitionDAO.class);
        r = new RefereeComunicationService();
        match = new Match();
        m1 = new Match();
        local = new Team("local");
        visitor = new Team ("visitor");
        m1.setHome(local);
        m1.setVisitor(visitor);
        ref = new Referee("11111", "Allu");
        info = new InfoMatch(match);
        comp = new CompetitionLeague();
        comp.setInici(new DateTime().toDate());
        
        r.setMatchDAO(matchDAO);
        r.setRefDAO(refDAO);
        r.setCompDAO(compDAO);
    }

    @Test (expected=InvalidMatchException.class)
    public void incorrectMatch() throws Exception{
        
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(null));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
    
  @Test (expected=InvalidRefereeException.class)
    public void incorrectReferee() throws Exception{
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(null));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
  
    @Test(expected=InvalidCompetitionException.class)
    public void incorrectCompetition() throws Exception{
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(null));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
    @Test(expected = InvalidMatchException.class)
    public void matchNotAssignedToReferee() throws Exception{
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
    
    @Test (expected=MatchOutOfPeriodException.class)
    public void notFinishedMatch() throws Exception{
        InfoMatch imAux = new InfoMatch();
        DateTime date2 = new DateTime();
        DateTime date = date2.minusWeeks(3);
        imAux.setMatchDate(date.toDate());
        ref.getMatches().add(match);
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); 
        r.communicateResultMatch(ref.getNif(), 
                match.getIdMatch(), imAux);
    }
    
    //@Test (expected=MatchOutOfPeriodException.class)
    //Ens falta la relacio Match-WeekMatch per tal d'obtenir la data de la jornada actual
    public void notFinishedMatch2() throws Exception{
        InfoMatch imAux = new InfoMatch();
        DateTime date2 = new DateTime();
        DateTime date = null;
        imAux.setMatchDate(date.toDate());
        ref.getMatches().add(match);
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); 
        r.communicateResultMatch(ref.getNif(), 
                match.getIdMatch(), imAux);
    }    
    
    @Test
    public void communicateResultMatch() throws Exception{
        ref.getMatches().add(match);
        info.setMatchDate(new DateTime().toDate());
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
        assertInfoMatch(info, match);
    }
    
    private void assertInfoMatch(InfoMatch im, Match m){
        assertEquals(im.getGoalsHome(), m.getGoalsHome());
        assertEquals(im.getGoalsVisiting(), m.getGoalsVisiting());
        assertEquals(im.getMatchDate(), m.getMatchDate());
        assertEquals(im.getPlace(), m.getPlace());
        assertEquals(im.getObservations(), m.getObservations());
    }
    
    @Test
    public void communicateMatchsToRef() throws Exception{
        setUpCompetition();
        r.communicateRefereesMatchesComp(comp);
        assertEquals(ref.getMatches(), Arrays.asList(m1));
    }
    
    private void setUpCompetition(){
        FCalendar cal = new FCalendar();
        WeekMatch wm = new WeekMatch();
        WeekMatch wm2 = new WeekMatch();
        Match m2 = new Match();
        m1.setReferee(ref);
        m2.setReferee(new Referee("2222", "Willy"));
        wm.setMatchs(Arrays.asList(m1, m2));
        cal.setWeekMatches(Arrays.asList(wm, wm2));
        comp.setName("Liga");
        comp.setFcalendar(cal);
    }
}
