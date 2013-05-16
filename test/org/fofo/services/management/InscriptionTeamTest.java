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
    private Competition Comp;
    private Team team;
    private Team team1;
    private Team team2;
    private Team team3;
    private Team team4;
    private Team team5;
    private TeamDAO TeamDAO;
    private Club club;
    private ClubDAO clubdao;

    @Before
    public void setup() {

        service = new ManagementService();
        Cdao = context.mock(CompetitionDAO.class); //Fem mock de una classe amb interface
        service.setcDao(Cdao);
        TeamDAO = context.mock(TeamDAO.class); //Fem mock de una classe amb interface
        clubdao = context.mock(ClubDAO.class); 

        team = new Team();
        team1 = new Team();
        team2 = new Team();
        team3 = new Team();
        team4 = new Team();
        team5 = new Team();
        
        List<Team> teams = new ArrayList();
        teams.add(team);
        teams.add(team1);
        
        Comp = Competition.create(Type.CUP);
        Comp.setCategory(Category.FEMALE);
        Comp.setInici(new Date()); //Conte la data actual
        Comp.setMaxTeams(4);
        Comp.setMinTeams(2);
        Comp.setName("Lleida");
        Comp.setTeams(teams);
        Comp.setType(Type.CUP);
     
        
        
        Club club = new Club("ClubExemple");
        club.setEmail("exemple@hotmail.com");
        club.setTeams(teams);
        
        
        
    }

   //@Test(expected = IncorrectDate.class)
    public void correctAddTeamToCategory() throws Exception {
       //Cdao.addTeam(Comp, team);
       //clubDAO.addClub(club);
       
       service.addCompetition(Comp);
       team2.setCategory(null);
        
        service.addTeam(Comp, team2);
        //assertTrue("L'equip no ha estat inserit",Comp.getTeams().contains(team2));
    }

    //@Test(expected = InscriptionTeamException.class)
    public void periodNotOpen() throws InscriptionTeamException, Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 11);
        Comp.setInici(cal.getTime());
        service.addTeam(Comp, team);
    }

    //@Test(expected = AssertionError.class)
    public void notTeamSpace() throws Exception {
      
        service.addTeam(Comp, team1);
        service.addTeam(Comp, team2);
        service.addTeam(Comp, team3);
        service.addTeam(Comp, team4);
        service.addTeam(Comp, team5);
        
    }

    //@Test (expected = InscriptionTeamException.class)
    public void correctAddTeamToList() throws Exception {
        service.addTeam(Comp, team);

        context.checking(new Expectations() {

            {
                oneOf(Cdao).addCompetition(Comp);
                oneOf(Cdao).getCompetitionms();
                will(returnValue(new ArrayList<Competition>() {

                    { //Esprem que la crida a la funció getCompetitionms, ens retorni un array amb les competicions.
                        add(Comp);
                        Comp.getTeams().contains(team);
                    }
                }));
            }
        });
       // assertTrue(Comp.getTeams().contains(team));
    }

    //@Test
    public void TeamInsertedIntoTheDB_old() throws Exception {

        context.checking(new Expectations() {

            {
                oneOf(Cdao).addTeam(Comp, team);
            }
        });

        service.addTeam(Comp, team);
    }
    
    
    
    
    
    @Test(expected=InscriptionTeamException.class)
    public void testIncorrectName() throws Exception{
        
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(Comp);
        team.setEmail("Team@hotmail.com");
        team.setName(null);         
        
        service.addTeam(Comp, team);

    }
    
    
     @Test(expected=InscriptionTeamException.class)
     public void testIncorrectEmail() throws Exception{
        
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(Comp);
        team.setEmail(null);
        team.setName("Team");     
        
        service.addTeam(Comp, team);

    }
     
     @Test(expected=InscriptionTeamException.class)
     public void testIncorrectCompetition() throws Exception{
        
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(null);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");     
        
        service.addTeam(Comp, team);

    }
     
     //@Test(expected=InscriptionTeamException.class)
     public void testIncorrectClub() throws Exception{
        
        team.setCategory(Category.MALE);
        team.setClub(null);
        team.setCompetition(Comp);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");     
        
        service.addTeam(Comp, team);

    }
     
     @Test(expected=InscriptionTeamException.class)
     public void testIncorrectCategory() throws Exception{
        
        team.setCategory(null);
        team.setClub(club);
        team.setCompetition(Comp);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");     
        
        service.addTeam(Comp, team);

    }
    
      
    //@Test
    public void TeamInsertedIntoTheDB() throws Exception {
      
        
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(Comp);
        team.setEmail("Team@hotmail.com");
        team.setName("team");     
        
        final List<Team> teams = new ArrayList<Team>();
        
        context.checking(new Expectations() {{
            oneOf (Cdao).addTeam(Comp,team);
            oneOf (TeamDAO).getTeams();will(returnValue(teams));
        }});  
        
        service.setcDao(Cdao);
        service.setTeamDao(TeamDAO);
        service.addTeam(Comp, team);
        
   
    }
    
    
      
     //@Test//(expected=InscriptionTeamException.class)
     public void testInsertTeamInCompetition() throws Exception{
        
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(Comp);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");  
        
      /*  team1.setCategory(Category.MALE);
        team1.setClub(club);
        team1.setCompetition(Comp);
        team1.setEmail("Team@hotmail.com");
        team1.setName("Team1");  

        context.checking(new Expectations() {
            {
                oneOf(clubdao).addClub(club);
                oneOf(Cdao).addCompetition(Comp);
               
                oneOf(Cdao).getCompetitionms();
                will(returnValue(new ArrayList<Competition>() {
                    { 
                        add(Comp);
                    }
                }));
                oneOf(TeamDAO).addTeam(team);
                oneOf(TeamDAO).addTeam(team1);                
            }
        });*/
        
        service.addTeam(Comp, team);                
        assertTrue(Comp.getTeams().contains(team));
    }
     
     
     //@Test//(expected=InscriptionTeamException.class)
     public void CorrectAddTeam() throws Exception{
        
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setCompetition(Comp);
        team.setEmail("Team@hotmail.com");
        team.setName("Team");     
        
        service.addTeam(Comp, team);
        //Pete per que falte definir expectacions per el mock Competition DAO, ja que la funció getCompetitionms() no està implementada.
    }
}
