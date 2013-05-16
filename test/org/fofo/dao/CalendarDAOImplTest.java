package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.WeekMatches;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
@RunWith(JMock.class)
public class CalendarDAOImplTest {

    Mockery context = new JUnit4Mockery();
    EntityManager em;
    EntityTransaction transaction;
    CalendarDAOImpl calDAO;
    Match match1, match2, match3, match4;
    WeekMatches wm1, wm2;
    FCalendar cal;

    @Before
    public void setUp() throws Exception {
        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);

        calDAO = new CalendarDAOImpl();
        calDAO.setEm(em);

        match1 = new Match();
        match2 = new Match();
        match3 = new Match();
        match4 = new Match();
        
        wm1 = new WeekMatches();
        wm2 = new WeekMatches();

        cal = new FCalendar();
    }

    /**
     * One Calendar cal, with just 1 wm and 1 match.
     * @throws Exception 
     */
    @Test
    public void testAdditionOfJustOneMatch() throws Exception {
        wm1.addMatch(match1);
        
        cal.getAllWeekMatches().add(wm1);
        
        calDAO.addCalendar(cal);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).commit();
                oneOf(em).persist(cal);
                oneOf(em).persist(wm1);
                oneOf(em).persist(match1);
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * 
     * Various matches in one WM.
     */
    @Test
    public void testAdditionOfVariousMatchesOneWM() throws Exception {

        wm1.addMatch(match1);
        wm1.addMatch(match2);
        wm2.addMatch(match3);
        wm2.addMatch(match4);
        
        cal.getAllWeekMatches().add(wm1);
        cal.getAllWeekMatches().add(wm2);
        
        calDAO.addCalendar(cal);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).commit();
                oneOf(em).persist(cal);
                oneOf(em).persist(wm1);
                oneOf(em).persist(match1);
                oneOf(em).persist(match2);
                oneOf(em).persist(match3);
                oneOf(em).persist(match4);
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * 
     * Various WM.
     */
    @Test
    public void testAddVariousWeekMatches() throws Exception {

        List<WeekMatches> Lwm = null;

        wm1.addMatch(match1);
        wm1.addMatch(match2);
        wm2.addMatch(match3);
        wm2.addMatch(match4);

        Lwm.add(wm1);
        Lwm.add(wm2);

        cal.setWeekMatches(Lwm);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).commit();
                oneOf(em).persist(cal);
                oneOf(em).persist(wm1);
                oneOf(em).persist(wm2);
                oneOf(em).persist(match1);
                oneOf(em).persist(match2);
                oneOf(em).persist(match3);
                oneOf(em).persist(match4);
            }
        });

        calDAO.addCalendar(cal);
    }
}