package org.fofo.services.management;

import javax.sound.midi.Sequence;
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
    
    final org.jmock.Sequence seq = context.sequence("seq");

    @Before
    public void setup() {

        service = new ManagementService();
        Cdao = context.mock(CompetitionDAO.class); //Fem mock de una classe amb interface
        teamDAO = context.mock(TeamDAO.class); 
        clubdao = context.mock(ClubDAO.class);
        
        service.setcDao(Cdao);
        service.setTeamDao(teamDAO);


        team = new Team("Team", Category.MALE);
        team.setClub(club);
        team.setCompetition(comp);
        team.setEmail("Team@hotmail.com");
       
        
        team1 = new Team();
        team2 = new Team();
        team3 = new Team();
        team4 = new Team(); team5 = new Team();

     

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
       // comp2.getTeams().add(team2);
       // comp2.getTeams().add(team3);
       
        


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
    
     @Test (expected = InscriptionTeamException.class)
     public void competition_NotExist_without_list() throws Exception {

        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");
        team.setCompetition(comp);

        //comp2 = new Competition(Comp);

        
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
     public void PeriodeOpen() throws Exception {
        
        System.out.println("Not yet implemented");
    }
    
    
    @Test
     public void EnoughtTeamSpace() throws Exception {  
                 
        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com");
        team1.setCompetition(comp);
        
        team2 = new Team("Team2", Category.FEMALE);
        team2.setClub(club);
        team2.setEmail("Team2@hotmail.com");
        team2.setCompetition(comp);
        
        
        team3 = new Team("Team3", Category.FEMALE);
        team3.setClub(club);
        team3.setEmail("Team3@hotmail.com");
        team3.setCompetition(comp);
        
        team4 = new Team("Team4", Category.VETERAN);
        team4.setClub(club);
        team4.setEmail("Team4@hotmail.com");
        team4.setCompetition(comp);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp);
        

        context.checking(new Expectations() {

            {
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp, team1); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp, team2); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp, team3); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp, team4); inSequence(seq);
                
            }
        });

       
        service.addTeam(comp, team1);
        service.addTeam(comp, team2);
        service.addTeam(comp, team3);
        service.addTeam(comp, team4);
                
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
    

    @Test(expected = InscriptionTeamException.class)
    public void periodNotOpen() throws InscriptionTeamException, Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 11);
        comp.setInici(cal.getTime());
        service.addTeam(comp, team);
    }

    
    //@Test(expected = InscriptionTeamException.class)
    public void notTeamSpace() throws Exception {

        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com");
        team1.setCompetition(comp2);
        
        team2 = new Team("Team2", Category.FEMALE);
        team2.setClub(club);
        team2.setEmail("Team2@hotmail.com");
        team2.setCompetition(comp2);
        
        
        team3 = new Team("Team3", Category.FEMALE);
        team3.setClub(club);
        team3.setEmail("Team3@hotmail.com");
        team3.setCompetition(comp2);
        
        team4 = new Team("Team4", Category.VETERAN);
        team4.setClub(club);
        team4.setEmail("Team4@hotmail.com");
        team4.setCompetition(comp2);
        
        team5 = new Team("Team5", Category.VETERAN);
        team5.setClub(club);
        team5.setEmail("Team5@hotmail.com");
        team5.setCompetition(comp2);

        final List<Competition> competitions = new ArrayList<Competition>();
        competitions.add(comp2);
        

        context.checking(new Expectations() {

            {
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp2, team1); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp2, team2); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp2, team3); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp2, team4); inSequence(seq);
                oneOf(Cdao).getCompetitionms(); will(returnValue(competitions)); inSequence(seq);
                oneOf (Cdao).addTeam(comp2, team5); inSequence(seq);
                
            }
        });

       
        service.addTeam(comp2, team1);
        service.addTeam(comp2, team2);
        service.addTeam(comp2, team3);
        service.addTeam(comp2, team4);
        service.addTeam(comp2, team5);
                
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

        final List<Competition> lcomp = new ArrayList<Competition>();

        lcomp.add(comp); 
        
        context.checking(new Expectations() {

            {
                oneOf(Cdao).getCompetitionms(); will (returnValue(lcomp));       
                oneOf(Cdao).addTeam(comp, team);
            }
        });
        
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
