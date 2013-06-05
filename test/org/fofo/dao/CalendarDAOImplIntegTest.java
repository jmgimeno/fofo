/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class CalendarDAOImplIntegTest {

    EntityManager em;
    EntityTransaction transaction;
    CalendarDAOImpl calDAO;
    Match match1, match2, match3, match4;
    WeekMatch wm1, wm2;
    FCalendar cal;
    Team team1, team2, team3, team4;
    Competition comp;

    @Before
    public void setUp() throws Exception {
        calDAO = new CalendarDAOImpl();

        em = getEntityManagerFact();
        calDAO.setEm(em);
        cal = calendarSetUp();

        System.out.println(cal);

    }

    @After
    public void tearDown() throws Exception {


        em = getEntityManagerFact();

        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM Team st");

        Query query2 = em.createQuery("DELETE FROM Competition comp");

        Query query3 = em.createQuery("DELETE FROM FCalendar cal");

        Query query4 = em.createQuery("DELETE FROM WeekMatch wm");

        Query query5 = em.createQuery("DELETE FROM Match m");


        int deleteRecords = query3.executeUpdate();      //Delete FCalendar
        deleteRecords = query4.executeUpdate();          //Delete WeekMatches
        deleteRecords = query5.executeUpdate();          //Delete Match_
        deleteRecords = query2.executeUpdate();          //Delete competitions
        deleteRecords = query.executeUpdate();           //Delete teams



        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");


    }

    @Test
    public void testAddCalendarWithNoWeekMatchs() throws Exception {
        FCalendar calendar = new FCalendar(null);
        calDAO.addCalendar(calendar);

        FCalendar calDB = getCalendarFromDB(calendar.getIdFCalendar());

        assertEquals("Should have retrieved the same calendar", calendar, calDB);
    }

    /**
     * One Calendar cal, with just 1 wm and 1 match.
     *
     * @throws Exception
     */
    @Test
    public void testAddCalendar() throws Exception {
        calDAO.addCalendar(cal);
        //    FCalendar calDB = getCalendarFromDB(cal.getIdFCalendar());        


        //       System.out.println("*****COMPETITION of cal="+cal.getCompetition());
        //     System.out.println("*****COMPETITION  caldb="+calDB.getCompetition());


        //   System.out.println("*****cal-->comp--->cal="+cal.getCompetition().getFCalendar());
        // System.out.println("****caldb-->comp-->cal="+calDB.getCompetition().getFCalendar());


        //  assertEquals("Should have retrieved the same calendar",cal,calDB);                
    }

    // @Test
    public void testAddCalendarFindFCalendarByCompetitionName() throws Exception {
        calDAO.addCalendar(cal);


        FCalendar calDB = calDAO.findFCalendarByCompetitionName(cal.getCompetition().getName());

        // System.out.println("*****COMPETITION NAME of cal="+cal.getCompetition().getName());
        // System.out.println("*****COMPETITION NAME of caldb="+calDB.getCompetition().getName());


        assertEquals("Should have retrieved the same calendar", cal, calDB);
    }

    //PRIVATE OPS.
    private FCalendar calendarSetUp() throws Exception {

        em = getEntityManagerFact();

        comp = Competition.create(CompetitionType.LEAGUE);

        comp.setName("League Spring 2013");

        team1 = new Team("team1");
        team2 = new Team("team2");
        team3 = new Team("team3");
        team4 = new Team("team4");


        insertCompDB(comp);

        insertTeamDB(team1);
        insertTeamDB(team2);
        insertTeamDB(team3);
        insertTeamDB(team4);


        wm1 = new WeekMatch();
        wm2 = new WeekMatch();

        Match match1_2 = new Match(team1, team2);
        Match match3_4 = new Match(team3, team4);


        Match match1_3 = new Match(team1, team3);
        Match match2_4 = new Match(team2, team4);

        wm1.setCalendar(cal);
        wm1.getListOfWeekMatches().add(match1_2);
        wm1.getListOfWeekMatches().add(match3_4);

        wm2.setCalendar(cal);
        wm2.getListOfWeekMatches().add(match1_3);
        wm2.getListOfWeekMatches().add(match2_4);

        cal = new FCalendar(comp);

        cal.getAllWeekMatches().add(wm1);
        cal.getAllWeekMatches().add(wm2);
        cal.setCompetition(comp);
        comp.setFcalendar(cal);

        System.out.println("******Calendar set up: comp of cal=" + cal.getCompetition());
        System.out.println("******Calendar set up: cal of comp=" + comp.getFCalendar().getIdFCalendar());


        return cal;

    }

    private void insertCompDB(Competition comp) throws Exception {

        em.getTransaction().begin();
        em.persist(comp);
        em.getTransaction().commit();



    }

    private void insertTeamDB(Team team) throws Exception {

        em.getTransaction().begin();
        em.persist(team);
        em.getTransaction().commit();



    }

    private EntityManager getEntityManagerFact() throws Exception {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("fofo");

        return emf.createEntityManager();
    }

    private FCalendar getCalendarFromDB(String idcalendar) throws Exception {

        em = getEntityManagerFact();
        em.getTransaction().begin();
        FCalendar calendarDB = em.find(FCalendar.class, idcalendar);
        em.getTransaction().commit();
        em.close();

        return calendarDB;

    }
}
