package org.fofo.services.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.fofo.dao.ClubDAO;
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.PersistException;
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



        comp = Competition.create(Type.CUP);
        comp.setCategory(Category.FEMALE);
        comp.setInici(new Date()); //Conte la data actual
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");
        service.setCompetition(comp);

        comp2 = Competition.create(Type.CUP);
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

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamEmail() throws Exception {

        team1.setEmail(null);

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamCompetition() throws Exception {

        team1.setCompetition(null);

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamCategory() throws Exception {

        team1.setCategory(null);

        service.addTeam(comp, team1);
    }

    //EN AQUEST TEST US FALTEN LES EXPECTATIONS    
    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectTeamClub() throws Exception {

        team1.setClub(null);

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void competitionNotExist_withEmptyList() throws Exception {

        team1.setCompetition(comp);

        context.checking(new Expectations() {

            {
                oneOf(cdao).getCompetitionms();
                will(returnValue(new ArrayList<Competition>())); //Conte un valor null    
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void competitionNotExist_withList() throws Exception {

        team1.setCompetition(comp);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp2);

        context.checking(new Expectations() {

            {
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions));
            }
        });

        service.addTeam(comp, team1);
    }

    @Test(expected = InscriptionTeamException.class)
    public void competitionInscriptionPeriodClosed() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 11);
        comp.setInici(cal.getTime());
        service.addTeam(comp, team1);
    }

    @Test (expected = InscriptionTeamException.class)
    public void notEnoughTeamSpaceInCompetition() throws Exception {
        
        service.setCompetition(comp2);

        team1.setCompetition(comp2);
        team2.setCompetition(comp2);
        team3.setCompetition(comp2);
        team4.setCompetition(comp2);
        team5.setCompetition(comp2);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp2);
        
        final List<Team> teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        teams.add(team5);

        context.checking(new Expectations() {

            {
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp2, team1); inSequence(seq);
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp2, team2); inSequence(seq);
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp2, team3); inSequence(seq);
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp2, team4); inSequence(seq);

            }
        });
        
        service.addTeam(comp2, team1);
        service.addTeam(comp2, team2);
        service.addTeam(comp2, team3);
        service.addTeam(comp2, team4);
        service.addTeam(comp2, team5);
    }

    @Test
    public void testInsertTeamInCompetition() throws Exception {

        team1.setCompetition(comp);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);
        
        final List<Team> teams = new ArrayList<Team>();
        teams.add(team1);

        context.checking(new Expectations() {

            {
               oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
               oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
               oneOf(cdao).addTeam(comp, team1); inSequence(seq);

            }
        });

        service.addTeam(comp, team1);

        assertTrue(comp.getTeams().contains(team1));
    }

    @Test
    public void CorrectAddOneTeam() throws Exception {

        team1.setCompetition(comp);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);
        
        final List<Team> teams = new ArrayList<Team>();
        teams.add(team1);


        context.checking(new Expectations() {

            {
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp, team1); inSequence(seq);

            }
        });

        //System.out.println("***TEST: Team1=" + team1 + " club=" + team1.getClub());
        service.addTeam(comp, team1);
    }

    @Test
    public void CorrectAddVariousTeam() throws Exception {

        team1.setCompetition(comp);
        team2.setCompetition(comp);
        team3.setCompetition(comp);
        team4.setCompetition(comp);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);
        
        final List<Team> teams = new ArrayList<Team>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);


        context.checking(new Expectations() {

            {
                
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp, team1); inSequence(seq);
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp, team2); inSequence(seq);
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp, team3); inSequence(seq);
                oneOf(cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp, team4); inSequence(seq);

            }
        });
        service.addTeam(comp, team1);
        service.addTeam(comp, team2);
        service.addTeam(comp, team3);
        service.addTeam(comp, team4);

        assertTrue(comp.getTeams().contains(team1));
        assertTrue(comp.getTeams().contains(team2));
        assertTrue(comp.getTeams().contains(team3));
        assertTrue(comp.getTeams().contains(team4));
    }

    @Test
    public void correctTeamInsertedIntoTheDB() throws Exception {

        team1.setCompetition(comp);

        final List<Competition> lcomp = new ArrayList<Competition>();
        lcomp.add(comp);
        
        final List<Team> teams = new ArrayList<Team>();
        teams.add(team1);

        context.checking(new Expectations() {

            {
                oneOf(cdao).getCompetitionms(); will(returnValue(lcomp)); inSequence(seq);
                oneOf(teamDAO).getTeams(); will(returnValue(teams)); inSequence(seq);
                oneOf(cdao).addTeam(comp, team1); inSequence(seq);
            }
        });

        service.addTeam(comp, team1);
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

        service.addTeam(comp, team5);
    }
}