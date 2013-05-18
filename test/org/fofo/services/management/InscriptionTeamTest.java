package org.fofo.services.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.fofo.dao.ClubDAO;
import org.fofo.dao.CompetitionDAO;
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
    private CompetitionDAO Cdao;
    private Competition comp, comp2;
    private Team team;
    private Team team1;
    private Team team2;
    private Team team3;
    private Team team4;
    private Team team5;
    private TeamDAO teamDAO;
    private Club club;
    private ClubDAO clubdao;

    @Before
    public void setup() {

        service = new ManagementService();
        Cdao = context.mock(CompetitionDAO.class); //Fem mock de una classe amb interface

        service.setcDao(Cdao);
       
        teamDAO = context.mock(TeamDAO.class); //Fem mock de una classe amb interface
        clubdao = context.mock(ClubDAO.class);
        

        team = new Team("Team", Category.MALE);
        team.setClub(club);
        team.setCompetition(comp);
        team.setEmail("Team@hotmail.com");
        
        
        team1 = new Team(); team2 = new Team(); team3 = new Team();
        team4 = new Team(); team5 = new Team();

     

        comp = Competition.create(Type.CUP);
        comp.setCategory(Category.FEMALE);
        comp.setInici(new Date()); //Conte la data actual
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");
        comp.getTeams().add(team);
        comp.getTeams().add(team1);
        service.setCompetition(comp);
        
        /*comp2 = Competition.create(Type.CUP);
        comp2.setCategory(Category.FEMALE);
        comp2.setInici(new Date()); //Conte la data actual
        comp2.setMaxTeams(4);
        comp2.setMinTeams(2);
        comp2.setName("Lleida");
        comp2.getTeams().add(team);
        comp2.getTeams().add(team1);
        comp2.setType(Type.CUP);
        service.setCompetition(comp2);*/
        


        club = new Club("ClubExemple");
        club.setEmail("exemple@hotmail.com"); 
        club.getTeams().add(team);
        club.getTeams().add(team1);
        service.setClubDao(clubdao);


    }
    
    
    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectName() throws Exception {

        team.setName(null);

        service.addTeam(comp, team);
    }

    
    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectEmail() throws Exception {
        
        team.setEmail(null);

        service.addTeam(comp, team);
    }
    

    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectCompetition() throws Exception {

        team.setCompetition(null);

        service.addTeam(comp, team);
    }

    
    @Test(expected = InscriptionTeamException.class)
    public void testIncorrectCategory() throws Exception {

        team.setCategory(null);

        service.addTeam(comp, team);
    }
    
      
    @Test (expected = InscriptionTeamException.class)
     public void competition_NotExist_without_empty_list() throws Exception {

        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");
        team.setCompetition(comp);
        
        
        context.checking(new Expectations() {

            {
                oneOf(Cdao).getCompetitionms(); will(returnValue(new ArrayList<Competition>())); //Conte un valor null    
            }
        });

        service.addTeam(comp, team);
    }
    
    // @Test (expected = InscriptionTeamException.class)
     public void competition_NotExist_without_list() throws Exception { //Pendent d'arreglar..

        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");
        team.setCompetition(comp);

        //comp2 = new Competition(Comp);
        comp2.setName("popop");
        
        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp2);
        
        context.checking(new Expectations() {

            {
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions));
            }
        });

        service.addTeam(comp, team);
    }
    
    
    //@Test (expected = InscriptionTeamException.class)
     public void NotPeriodeOpen() throws Exception {
        
        System.out.println("Not yet implemented");
    }
    
    
    //@Test (expected = InscriptionTeamException.class)
     public void NotTeamSpace() throws Exception {
        
        System.out.println("Not yet implemented");
    }
     

    @Test
    public void CorrectAddTeam() throws Exception {

        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");
        team.setCompetition(comp);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);
        
        final List<Club> clubs = new ArrayList<Club>();
        clubs.add(club);

        context.checking(new Expectations() {

            {
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions));
                
                oneOf (Cdao).addTeam(comp, team);
                
            }
        });


        System.out.println("***TEST: Team="+team+" club="+team.getClub());
        
        service.addTeam(comp, team);
    }
    
    
    //NO SE BEN BE QUE VOLEU TESTEJAR AMB AQUEST
    //@Test(expected = IncorrectDate.class)
    public void correctAddTeamToCategory() throws Exception {
        //Cdao.addTeam(Comp, team);
        //clubDAO.addClub(club);

        service.addCompetition(comp);
        team2.setCategory(null);

        service.addTeam(comp, team2);
        //assertTrue("L'equip no ha estat inserit",Comp.getTeams().contains(team2));
    }

    @Test(expected = InscriptionTeamException.class)
    public void periodNotOpen() throws InscriptionTeamException, Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 11);
        comp.setInici(cal.getTime());
        service.addTeam(comp, team);
    }

    @Test(expected = InscriptionTeamException.class)
    public void notTeamSpace() throws Exception {

        service.addTeam(comp, team1);
        service.addTeam(comp, team2);
        service.addTeam(comp, team3);
        service.addTeam(comp, team4);
        service.addTeam(comp, team5);

    }

    @Test (expected = InscriptionTeamException.class)
    public void correctAddTeamToList() throws Exception {
        service.addTeam(comp, team);

        context.checking(new Expectations() {

            {
                oneOf(Cdao).addCompetition(comp);
                oneOf(Cdao).getCompetitionms();
                    will(returnValue(new ArrayList<Competition>() {
                    { 
                        add(comp);
                        comp.getTeams().contains(team);
                    }
                }));
            }
        });
        // assertTrue(Comp.getTeams().contains(team));
    }

//EN AQUEST TEST US FALTEN LES EXPECTATIONS    
//    @Test(expected=InscriptionTeamException.class)
    public void testIncorrectClub() throws Exception {

        team.setCategory(Category.MALE);
        team.setClub(null);
        team.setCompetition(comp);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");

        service.addTeam(comp, team);

    }

    @Test
    public void TeamInsertedIntoTheDB() throws Exception {


        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(comp);
        team.setEmail("Team@hotmail.com");
        team.setName("team");

        final List<Team> teams = new ArrayList<Team>();
        final List<Competition> lcomp = new ArrayList<Competition>();

        lcomp.add(comp); 
        
        
        context.checking(new Expectations() {

            {
                oneOf(Cdao).addTeam(comp, team);
                //oneOf(teamDAO).getTeams();
                //       will(returnValue(teams));
                oneOf(Cdao).getCompetitionms(); will (returnValue(lcomp));       
            }
        });

        
        
        service.setcDao(Cdao);
        service.setTeamDao(teamDAO);
        //AQUESTS DOS SETS, NO HAURIEN D'ANAR AL SETUP???
        
        service.addTeam(comp, team);


    }

 //***EN AQUEST TEST US FALTEN LES EXPECTATIONS  
//    @Test//(expected=InscriptionTeamException.class)
    public void testInsertTeamInCompetition() throws Exception {

        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(comp);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");

        /*
         * team1.setCategory(Category.MALE); team1.setClub(club);
         * team1.setCompetition(Comp); team1.setEmail("Team@hotmail.com");
         * team1.setName("Team1");          *
         * context.checking(new Expectations() { { oneOf(clubdao).addClub(club);
         * oneOf(Cdao).addCompetition(Comp);
         *
         * oneOf(Cdao).getCompetitionms(); will(returnValue(new
         * ArrayList<Competition>() { { add(Comp); } }));
         * oneOf(TeamDAO).addTeam(team); oneOf(TeamDAO).addTeam(team1); }
        });
         */

        service.addTeam(comp, team);
        assertTrue(comp.getTeams().contains(team));
    }
    
  
    
    
    
    
}
