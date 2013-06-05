/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.util.Arrays;
import java.util.List;
import org.fofo.dao.MatchDAO;
import org.fofo.dao.RefereeDAO;
import org.fofo.entity.*;
import org.fofo.utils.InfoMatch;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
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
    
    public RefereeComunicationServiceTest() {
    }
    
    @Before
    public void setUp(){
        context = new JUnit4Mockery();
        matchDAO = context.mock(MatchDAO.class);
        refDAO = context.mock(RefereeDAO.class);
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
        
        r.setMatchDAO(matchDAO);
        r.setRefDAO(refDAO);
    }

    @Test (expected=InvalidMatchException.class)
    public void incorrectMatch() throws Exception{
        
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(null));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
    
  //  @Test (expected=InvalidRefereeException.class)
    public void incorrectReferee() throws Exception{
        //TO DO...
        /*context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(null));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    */}
    @Test(expected = InvalidMatchException.class)
    public void matchNotAssignedToReferee() throws Exception{
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
    
   // @Test (expected=MatchOutOfPeriodException.class)
    public void notFinishedMatch() throws Exception{
        //TODO...
        InfoMatch imAux = new InfoMatch(/*Data anterior a la 
         data d'inici de la competicio*/);
        r.communicateResultMatch(ref.getNif(), 
                match.getIdMatch(), imAux);
    }
    
   // @Test (expected=MatchOutOfPeriodException.class)
    public void notFinishedMatch2() throws Exception{
        //TODO...
        InfoMatch imAux = new InfoMatch(/*Data anterior a la 
         data de la jornada actual*/);
        r.communicateResultMatch(ref.getNif(), 
                match.getIdMatch(), imAux);
    }    
    
   // @Test
    public void communicateResultMatch() throws Exception{
        ref.getMatches().add(match);
        context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
        }}); 
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
       // assertEquals(info, match.getInfo());
       //*****MALAMENT. MATCH NO TE GETINFO. SE SUBSTITUEIX PELS ATRIBUTS CORRESPONENTS 
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
