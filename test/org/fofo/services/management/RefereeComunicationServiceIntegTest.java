/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.fofo.dao.MatchDAO;
import org.fofo.dao.MatchDAOImpl;
import org.fofo.dao.RefereeDAO;
import org.fofo.dao.RefereeDAOImpl;
import org.fofo.entity.*;
import org.fofo.utils.InfoMatch;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adr4 & imt1
 */
public class RefereeComunicationServiceIntegTest {
//No podem fer tests d'integracio fins que no estigui ben definit el ORM.
   /* RefereeComunicationService r;
    Match match, m1;
    Referee ref;
    InfoMatch info;
    RefereeDAO refDAO;
    MatchDAO matchDAO;
    Mockery context;
    Competition comp;
    Team local, visitor;
    EntityManager em = null;

    public RefereeComunicationServiceIntegTest() {
    }

    @Before
    public void setUp() throws Exception{
        context = new JUnit4Mockery();
        matchDAO = new MatchDAOImpl();
        refDAO = new RefereeDAOImpl();
        r = new RefereeComunicationService();
        match = new Match();
        m1 = new Match();
        local = new Team("local");
        visitor = new Team("visitor");
        m1.setHome(local);
        m1.setVisitor(visitor);
        ref = new Referee("11111", "Allu");
        info = new InfoMatch(match);
        comp = new CompetitionLeague();

        r.setMatchDAO(matchDAO);
        r.setRefDAO(refDAO);

        em = getEntityManagerFact();

        em.getTransaction().begin();
        em.persist(ref);
        em.getTransaction().commit();
    }

    private EntityManager getEntityManagerFact() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }

    @Test(expected = InvalidMatchException.class)
    public void incorrectMatch() throws Exception {
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }

    @Test(expected = InvalidMatchException.class)
    public void matchNotAssignedToReferee() throws Exception {
        context.checking(new Expectations() {

            {
                oneOf(refDAO).findRefereeByNif(ref.getNif());
                will(returnValue(ref));
                oneOf(matchDAO).findMatchById(match.getIdMatch());
                will(returnValue(match));
            }
        });
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
    }

    @Test
    public void communicateResultMatch() throws Exception {
        ref.getMatches().add(match);
        context.checking(new Expectations() {

            {
                oneOf(refDAO).findRefereeByNif(ref.getNif());
                will(returnValue(ref));
                oneOf(matchDAO).findMatchById(match.getIdMatch());
                will(returnValue(match));
            }
        });
        r.communicateResultMatch(ref.getNif(), match.getIdMatch(), info);
        assertEquals(info, match.getInfo());
    }

    @Test
    public void communicateMatchsToRef() throws Exception {
        setUpCompetition();
        r.communicateRefereesMatchesComp(comp);
        assertEquals(ref.getMatches(), Arrays.asList(m1));
    }

    private void setUpCompetition() {
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
    }*/
}
