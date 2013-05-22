package org.fofo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.Category;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.Team;
import org.fofo.entity.WeekMatch;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.fofo.entity.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
@RunWith(JMock.class)
public class CalendarDAOImplTest {

    Mockery context = new JUnit4Mockery();
    private EntityManager em;
    private TeamDAO tdao;
    private CalendarDAOImpl calDAO;
    private EntityTransaction transaction;
    FCalendar cal;
    Competition comp;
    Club club;
    WeekMatch wm1, wm2, wm3, wm4, wm5;
    Match match1, match2, match3, match4, match5, match6;
    Team team1, team2, team3, team4;

    @Before
    public void setUp() throws Exception {

        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        tdao = context.mock(TeamDAO.class);


        calDAO = new CalendarDAOImpl();
        calDAO.setEm(em);
        calDAO.setTd(tdao);

        club = new Club("ClubExemple");
        club.setEmail("exemple@hotmail.com");


        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com");

        team2 = new Team("Team2", Category.FEMALE);
        team2.setClub(club);
        team2.setEmail("Team2@hotmail.com");

        team3 = new Team("Team3", Category.FEMALE);
        team3.setClub(club);
        team3.setEmail("Team3@hotmail.com");

        team4 = new Team("Team4", Category.FEMALE);
        team4.setClub(club);
        team4.setEmail("Team4@hotmail.com");
        

        match1 = new Match();
        match1.setLocal(team1);
        match1.setVisitant(team2);

        match2 = new Match();
        match2.setLocal(team3);
        match2.setVisitant(team4);

        match3 = new Match();
        match3.setLocal(team2);
        match3.setVisitant(team1);

        match4 = new Match();
        match4.setLocal(team4);
        match4.setVisitant(team3);

        match5 = new Match();


        wm1 = new WeekMatch();
        wm1.addMatch(match1);

        wm2 = new WeekMatch();
        wm2.addMatch(match2);
        wm2.addMatch(match3);

        wm3 = new WeekMatch();
        wm3.addMatch(match3);
        wm3.addMatch(match4);

        wm4 = new WeekMatch();

        wm5 = new WeekMatch();
        wm5.addMatch(match5); //This match, There have no local or visitant team


        comp = Competition.create(CompetitionType.CUP);
        comp.setCategory(Category.FEMALE);
        comp.setInici(new Date());
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");

        cal = new FCalendar();
        cal.setCompetition(comp);

    }

    /**
     * Add Calendar with no WM.
     *
     * @throws Exception
     */
   // @Test
    public void CorrectAddCalenadrwithNotWM() throws Exception {

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).persist(cal);
                oneOf(em).getTransaction().commit();
                oneOf(em).close();
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * Add Calendar with wm, but without Matches.
     *
     * @throws Exception
     */
    //@Test
    public void CorrectAddCalenadrwithNotMatch() throws Exception {

        cal.getAllWeekMatches().add(wm4);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();
                oneOf(em).persist(cal.getWeekMatch(0));
                oneOf(em).persist(cal);
                oneOf(em).getTransaction().commit();
                oneOf(em).close();
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * Add Calendar with wm, but without Matches.
     *
     * @throws Exception
     */
   //@Test(expected = IncorrectTeamException.class)
    public void NoTeamsInMatch() throws Exception {

        cal.getAllWeekMatches().add(wm1);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();

                oneOf(tdao).getTeams();
                will(returnValue(null));

                oneOf(em).close();
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * The match don't have a local team.
     *
     * @throws Exception 
     */
 //@Test(expected = IncorrectTeamException.class)
    public void NotLocalTeamInMatch() throws Exception {

        cal.getAllWeekMatches().add(wm5);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();

                final List<Team> teams = new ArrayList<Team>();
                teams.add(cal.getWeekMatch(0).getListOfWeekMatches().get(0).getLocal());
             
                
                oneOf(tdao).getTeams(); will(returnValue(teams.get(0)));

                oneOf(em).find(Team.class, teams.get(0));           
              
               
             //oneOf(tdao).findTeam(teams.get(0));
               
                oneOf(em).close();
            }
        });

        calDAO.addCalendar(cal);

    }

    /**
     * The match don't have a visitant team.
     *
     * @throws Exception
     */
  // @Test(expected = IncorrectTeamException.class)
    public void NotVisitantTeamInMatch() throws Exception {

        cal.getAllWeekMatches().add(wm5);

        context.checking(new Expectations() {

            {
                oneOf(em).getTransaction().begin();

                final List<Team> teams = new ArrayList<Team>();
                teams.add(cal.getWeekMatch(0).getListOfWeekMatches().get(0).getLocal());
                teams.add(cal.getWeekMatch(0).getListOfWeekMatches().get(0).getVisitant());

                oneOf(tdao).getTeams();
                will(returnValue(teams));
                
                oneOf(em).find(Team.class, teams.get(0).getName());
                //oneOf(tdao).findTeam(teams.get(0));
                will(returnValue(true));
                 oneOf(em).find(Team.class, teams.get(1).getName());
                //oneOf(tdao).findTeam(teams.get(1));

                oneOf(em).close();
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * Add Calendar with only one WeekMatch with only one Match.
     *
     * @throws Exception
     */
   // @Test
    public void testAdditionOfJustOneMatch() throws Exception {

        cal.getAllWeekMatches().add(wm1);

        context.checking(new Expectations() {

            {

                allowing(em).getTransaction().begin();

                /*
                 * Match
                 */

                final List<Team> teams = new ArrayList<Team>();
                teams.add(cal.getWeekMatch(0).getListOfWeekMatches().get(0).getLocal());
                teams.add(cal.getWeekMatch(0).getListOfWeekMatches().get(0).getVisitant());

                oneOf(tdao).getTeams();
                will(returnValue(teams));

                allowing(tdao).findTeam(teams.get(0));
                will(returnValue(true));
                allowing(tdao).findTeam(teams.get(1));
                will(returnValue(true));
                allowing(em).persist(cal.getWeekMatch(0).getListOfWeekMatches().get(0));

                /*
                 * WeekMatch
                 */
                allowing(em).persist(cal.getWeekMatch(0));
                 
                /*
                 * Cal
                 */
                allowing(em).persist(cal);
                allowing(em).getTransaction().commit();
                allowing(em).close();

            }
        });

        calDAO.addCalendar(cal);

    }

    /**
     * Add Calendar with only one WeekMatch with various Match.
     *
     * @throws Exception
     */
    //@Test
    public void testAdditionOfVariousMatchesOneWM() throws Exception {

        cal.getAllWeekMatches().add(wm2);


        context.checking(new Expectations() {

            {

                allowing(em).getTransaction().begin();

                for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {

                    for (int x = 0; x < cal.getWeekMatch(i).getNumberOfMatchs(); x++) {

                        /*
                         * Match
                         */
                        final List<Team> teams = new ArrayList<Team>();
                        teams.add(cal.getWeekMatch(i).getListOfWeekMatches().get(x).getLocal());
                        teams.add(cal.getWeekMatch(i).getListOfWeekMatches().get(x).getVisitant());

                        oneOf(tdao).getTeams();
                        will(returnValue(teams));
                        allowing(tdao).findTeam(teams.get(0));
                        will(returnValue(true));
                        allowing(tdao).findTeam(teams.get(1));
                        will(returnValue(true));
                        allowing(em).persist(cal.getWeekMatch(i).getListOfWeekMatches().get(x));

                    }
                    /*
                     * WeekMatch
                     */
                    allowing(em).persist(cal.getWeekMatch(i));
                }

                /*
                 * Cal
                 */
                allowing(em).persist(cal);
                allowing(em).getTransaction().commit();
                allowing(em).close();
            }
        });

        calDAO.addCalendar(cal);
    }

    /**
     * Add Calendar with various WeekMatch with various Match.
     *
     * @throws Exception
     */
   // @Test
    public void testAddVariousWeekMatches() throws Exception {

        cal.getAllWeekMatches().add(wm1);
        cal.getAllWeekMatches().add(wm2);
        cal.getAllWeekMatches().add(wm3);


        context.checking(new Expectations() {

            {

                allowing(em).getTransaction().begin();

                for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {

                    for (int x = 0; x < cal.getWeekMatch(i).getNumberOfMatchs(); x++) {

                        /*
                         * Match
                         */
                        final List<Team> teams = new ArrayList<Team>();
                        teams.add(cal.getWeekMatch(i).getListOfWeekMatches().get(x).getLocal());
                        teams.add(cal.getWeekMatch(i).getListOfWeekMatches().get(x).getVisitant());

                        allowing(tdao).getTeams();
                        will(returnValue(teams));
                        allowing(tdao).findTeam(teams.get(0));
                        will(returnValue(true));
                        allowing(tdao).findTeam(teams.get(1));
                        will(returnValue(true));
                        allowing(em).persist(cal.getWeekMatch(i).getListOfWeekMatches().get(x));

                    }
                    /*
                     * WeekMatch
                     */
                    allowing(em).persist(cal.getWeekMatch(i));
                }

                /*
                 * Cal
                 */
                allowing(em).persist(cal);
                allowing(em).getTransaction().commit();
                allowing(em).close();

            }
        });

        calDAO.addCalendar(cal);

    }
//    
//    
//    @Test
//    public void CorrectAddMatch() throws Exception {
//        context.checking(new Expectations() {
//
//            {
//                oneOf(tdao).getTeams();
//                will(returnValue(new ArrayList<Team>()));
//                oneOf(tdao).findTeam(match1.getLocal());
//                will(returnValue(true));
//                oneOf(tdao).findTeam(match1.getVisitant());
//                will(returnValue(true));
//                oneOf(em).persist(match1);
//            }
//        });
//
//
//        calDAO.addMatch(match1);
//    }
//
//    @Test
//    public void CorrectAddWeekMatches() throws Exception {
//
//        context.checking(new Expectations() {
//
//            {
//                for (int i = 0; i < wm1.getNumberOfMatchs(); i++) {
//                    oneOf(tdao).getTeams();
//                    will(returnValue(new ArrayList<Team>()));
//                    oneOf(tdao).findTeam(wm1.getListOfWeekMatches().get(i).getLocal());
//                    will(returnValue(true));
//                    oneOf(tdao).findTeam(wm1.getListOfWeekMatches().get(i).getVisitant());
//                    will(returnValue(true));
//                    oneOf(em).persist(wm1.getListOfWeekMatches().get(i));
//                }
//                oneOf(em).persist(wm1);
//            }
//        });
//
//
//        calDAO.addWeekMatches(wm1);
//    }
}
