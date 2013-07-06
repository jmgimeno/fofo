/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import org.fofo.services.management.exception.InvalidMatchException;
import org.fofo.services.management.exception.MatchOutOfPeriodException;
import org.fofo.services.management.exception.InvalidRefereeException;
import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectMatchException;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.dao.*;
import org.fofo.entity.*;
import org.fofo.utils.InfoMatch;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adr4 & imt1
 */
public class RefereeComunicationServiceIntegTest {
    EntityManager em = null;
    RefereeComunicationService r;
    Match match,m1,match2;
    Referee ref;
    InfoMatch info;
    RefereeDAOImpl refDAO;
    MatchDAO matchDAO;
    Competition comp;
    Team local, visitor;
    CompetitionDAOImpl compDAO;
    Club club;
    
    public RefereeComunicationServiceIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception{
        matchDAO = new MatchDAOImpl();
        refDAO = new RefereeDAOImpl();
        compDAO = new CompetitionDAOImpl();
        r = new RefereeComunicationService();
        club = new Club("nomCLub");
        
        match = new Match();
        match2 = new Match();
        m1 = new Match();
        local = new Team("local");
        visitor = new Team ("visitor");
        local.setCategory(Category.MALE);
        local.setClub(club);
        
        visitor.setCategory(Category.MALE);
        visitor.setClub(club);
        
        club.getTeams().add(local);
        club.getTeams().add(visitor);
        
        m1.setHome(local);
        m1.setVisitor(visitor);
        ref = new Referee("11111", "Allu");
        
        ref.getMatches().add(match);
        
        info = new InfoMatch(match);
        comp = new CompetitionLeague();
        comp.setName("Lliga");
        comp.setInici(new DateTime().toDate());
        info.setIdCompetition(comp.getName());
        
        
        match.setHome(local);
        match.setVisitor(visitor);
        match.setReferee(ref);
        
        match2.setHome(local);
        match2.setVisitor(visitor);
        
        
        
        em = getEntityManagerFact();
        
        em.getTransaction().begin();
        em.persist(comp);

        
        em.persist(ref);
        
        em.persist(club);
        
        em.persist(local);   //Innecessary, since they have been persisted by em.persist(club)
        em.persist(visitor); //Idem
        
        em.persist(match);
        em.persist(match2);
        em.getTransaction().commit();
       
        r.setMatchDAO(matchDAO);
        r.setRefDAO(refDAO);
        r.setCompDAO(compDAO);
        
        matchDAO.setEm(em);
        refDAO.setEM(em);
        compDAO.setEM(em);
        
    }
    
    @After
    public void tearDown() throws Exception{
        
        em = getEntityManagerFact();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Competition c");
        Query query2 = em.createQuery("DELETE FROM Referee r");
        Query query3 = em.createQuery("DELETE FROM Match m");
        Query query4 = em.createQuery("DELETE FROM Team m");
        Query query5 = em.createQuery("DELETE FROM Club m");
        
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();
        deleteRecords = query4.executeUpdate();
        deleteRecords = query5.executeUpdate();
                              
        em.getTransaction().commit();
        em.close();
    }
    
    private EntityManager getEntityManagerFact() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }

    @Test (expected=IncorrectMatchException.class)   
    public void incorrectMatch() throws Exception{
        String idMatch = "111";
        r.communicateResultMatch(ref.getNif(), idMatch, info);
    }
    
    @Test (expected=InvalidRefereeException.class) 
    public void incorrectReferee() throws Exception{
        String nif = "111";
        r.communicateResultMatch(nif, match.getIdMatch(), info);
    }
  
    @Test(expected=PersistException.class)   
    public void incorrectCompetition() throws Exception{
        info.setIdCompetition("111");
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }
    
    @Test(expected = InvalidMatchException.class) 
    public void matchNotAssignedToReferee() throws Exception{

        
        
        r.communicateResultMatch(ref.getNif(), match2.getIdMatch(), info);
    }
    
    @Test (expected=MatchOutOfPeriodException.class)   
    public void notFinishedMatch() throws Exception{
        InfoMatch imAux = new InfoMatch();
        DateTime date2 = new DateTime();
        DateTime date = date2.minusWeeks(3);
        imAux.setMatchDate(date.toDate());
        imAux.setIdCompetition(comp.getName());
        ref.getMatches().add(match);
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
        /*context.checking(new Expectations() {{
            oneOf (refDAO).findRefereeByNif(ref.getNif());will(returnValue(ref));
            oneOf (matchDAO).findMatchById(match.getIdMatch());will(returnValue(match));
            oneOf (compDAO).findCompetitionByName(comp.getName());will(returnValue(comp));
        }}); */
        r.communicateResultMatch(ref.getNif(), 
                match.getIdMatch(), imAux);
    }    
    
    @Test  
    public void communicateResultMatch() throws Exception{
        ref.getMatches().add(match);
         
        info.setMatchDate(new DateTime().toDate());
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
    
    //@Test   
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
    @Test
    public void test1(){}
}
