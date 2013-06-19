package org.fofo.services.management;

import org.fofo.services.management.exception.InscriptionTeamException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.fofo.dao.ClubDAO;
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.exception.PersistException;
import org.fofo.dao.TeamDAO;
import org.fofo.entity.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
@RunWith(JMock.class)
public class InscriptionTeamTest {

    Mockery context = new JUnit4Mockery();
    ManagementService service;
    private CompetitionDAO cdao;
    private Competition comp, comp2;
    private Team team1, team2, team3, team4, team5;
    private TeamDAO teamDAO;
    private Club club;
    private ClubDAO clubdao;
    final org.jmock.Sequence seq = context.sequence("seq");

    @Before
    public void setup() {

        service = new ManagementService();
        cdao = context.mock(CompetitionDAO.class); //Fem mock de una classe amb interface
        teamDAO = context.mock(TeamDAO.class);
        clubdao = context.mock(ClubDAO.class);

        service.setcDao(cdao);
        service.setTeamDao(teamDAO);

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

        team5 = new Team("Team5", Category.VETERAN);
        team5.setClub(club);
        team5.setEmail("Team5@hotmail.com");

        comp = Competition.create(CompetitionType.CUP);
        comp.setCategory(Category.FEMALE);
        comp.setInici(new Date()); //Conte la data actual
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");
        service.setCompetition(comp);

        comp2 = Competition.create(CompetitionType.CUP);
        comp2.setCategory(Category.FEMALE);
        comp2.setInici(new Date());
        comp2.setMaxTeams(4);
        comp2.setMinTeams(2);
        comp2.setName("Barcelona");

        club = new Club("ClubExemple");
        club.setEmail("exemple@hotmail.com");
        club.getTeams().add(team1);
        service.setClubDao(clubdao);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamName() throws Exception {

        team1.setName(null);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(null));
            }
        });


        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamEmail() throws Exception {

        team1.setEmail(null);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamCategory() throws Exception {


        team1.setCategory(null);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamClub() throws Exception {

        team1.setClub(null);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void competitionNotExistWithEmptyList() throws Exception {

        team1.getCompetitions().add(comp);
        team1.setClub(club);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(new ArrayList<Competition>())); //Conte un valor null
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void competitionNotExistWithList() throws Exception {

        team1.getCompetitions().add(comp);
        team1.setClub(club);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp2);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void competitionInscriptionPeriodClosed() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 11);
        comp.setInici(cal.getTime());

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));

            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void notEnoughTeamSpaceInCompetition() throws Exception {

        service.setCompetition(comp2);
        team1.setClub(club);
        team2.setClub(club);
        team3.setClub(club);
        team4.setClub(club);
        team5.setClub(club);

        team1.getCompetitions().add(comp2);
        team2.getCompetitions().add(comp2);
        team3.getCompetitions().add(comp2);
        team4.getCompetitions().add(comp2);
        team5.getCompetitions().add(comp2);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp2);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp2, team1);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team2.getName());
                will(returnValue(team2));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp2, team2);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team3.getName());
                will(returnValue(team3));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp2, team3);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team4.getName());
                will(returnValue(team4));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp2, team4);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team5.getName());
                will(returnValue(team5));


            }
        });

        service.addTeam(comp2, team1);
        service.addTeam(comp2, team2);
        service.addTeam(comp2, team3);
        service.addTeam(comp2, team4);
        service.addTeam(comp2, team5);
    }

    /**
     * Team5 and comp, have different category. So, it's not possible assign this team in that competition.
     * @throws PersistException
     * @throws Exception
     */
    @Test(expected = InscriptionTeamException.class)
    public void diffCategTeamAndCategory() throws PersistException, Exception {

        comp.setCategory(Category.FEMALE);
        team5.setCategory(Category.MALE);
        team5.setClub(club);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team5.getName());
                will(returnValue(team5));

            }
        });



        service.addTeam(comp, team5);
    }

    @Test
    public void testInsertTeamInCompetition() throws Exception {

        team1.getCompetitions().add(comp);
        team1.setClub(club);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team1);
                inSequence(seq);

            }
        });

        service.addTeam(comp, team1);
        //It is only necessary to test that the interaction with the dao has been
        //carried out. The following assertion has no sense here:
        // assertTrue(comp.getTeams().contains(team1));
    }

    @Test
    public void CorrectAddOneTeam() throws Exception {

        team1.getCompetitions().add(comp);
        team1.setClub(club);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);


        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team1);
                inSequence(seq);

            }
        });

        //System.out.println("***TEST: Team1=" + team1 + " club=" + team1.getClub());
        service.addTeam(comp, team1);
    }

    @Test
    public void CorrectAddVariousTeam() throws Exception {

        team1.getCompetitions().add(comp);
        team2.getCompetitions().add(comp);
        team3.getCompetitions().add(comp);
        team4.getCompetitions().add(comp);

        team1.setClub(club);
        team2.setClub(club);
        team3.setClub(club);
        team4.setClub(club);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team1);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team2.getName());
                will(returnValue(team2));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team2);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team3.getName());
                will(returnValue(team3));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team3);
                inSequence(seq);

                oneOf(teamDAO).findTeamByName(team4.getName());
                will(returnValue(team4));
                oneOf(cdao).getCompetitionms();
                will(returnValue(competitions));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team4);
                inSequence(seq);

            }
        });
        service.addTeam(comp, team1);
        service.addTeam(comp, team2);
        service.addTeam(comp, team3);
        service.addTeam(comp, team4);
    }

    @Test
    public void correctTeamInsertedIntoTheDB() throws Exception {

        team1.getCompetitions().add(comp);
        team1.setClub(club);

        final List<Competition> lcomp = new ArrayList<Competition>();
        lcomp.add(comp);

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName(team1.getName());
                will(returnValue(team1));
                oneOf(cdao).getCompetitionms();
                will(returnValue(lcomp));
                inSequence(seq);
                oneOf(cdao).addTeam(comp, team1);
                inSequence(seq);
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamName2() throws Exception {

        context.checking(new Expectations() {

            {
                oneOf(teamDAO).findTeamByName("failteam"); will(returnValue(null));
                oneOf(cdao).findCompetitionByName(comp.getName());
            }
        });
        
    service.addTeam(comp.getName(), "failteam");

    }
}