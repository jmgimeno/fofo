package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.fofo.dao.ClubDAO;
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.TeamDAO;
import org.fofo.entity.*;
import org.fofo.dao.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author josepma
 */
public class ClubManagementServiceIntegTest {
    
    ManagementService service;
    TeamDAOImpl teamDao;
    ClubDAOImpl clubDao;
    Team team1, team2;
    Club club;
    EntityManager em;
    
    public ClubManagementServiceIntegTest() {
    }

    @Before
    public void setUp() throws Exception {
        service = new ManagementService();  
        teamDao  = new TeamDAOImpl();
        clubDao  = new ClubDAOImpl();

        em = getEntityManager();
        clubDao.setEM(em);
        teamDao.setEM(em);
        
        service.setTeamDao(teamDao);
        service.setClubDao(clubDao);
        team1 = new Team("Team 1");
        team1.setCategory(Category.MALE);  
        team1.setEmail("email@email.com"); 
        
        team2 = new Team("Team 2");      
        team2.setCategory(Category.MALE);  
        team2.setEmail("email@email.com");         
        club = new Club("Club 1");
    }


     @After
    public void tearDown() throws Exception{
        
        em = clubDao.getEM();
        if (em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Team");
        Query query2=em.createQuery("DELETE FROM Club");
        int deleteRecords=query.executeUpdate();
        deleteRecords=query2.executeUpdate();
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
         
    }
   
    
    @Test(expected=InscriptionClubException.class)
    public void testInscriptionClubWithNoemail() throws Exception{
        service.addClub(club);
        
    }

    @Test
    public void testCorrectInscriptionClubWithNoTeams() throws Exception{
        
        club.setEmail("email@email.com");                    
        service.addClub(club);
        
         Club club1 = clubDao.findClubByName("Club 1");
        
         System.out.println("CLUB DB="+club1);
         System.out.println("CLUB   ="+club);
         
         assertEquals("Clubs should be equals",club,club1);
         
    }  
    
   @Test(expected=InscriptionClubException.class)
    public void testInscriptionClubAlreadyInDB() throws Exception{
        club.setEmail("email@email.com");                    
        service.addClub(club);
        service.addClub(club);
    }  
    
   /*
    * 
    * THE FOLLOWING TESTS DO NOT WORK SINCE THE OP. teamDAO.getTeams() has not 
    * been implemented yet.
    * 
    */
   
  //  @Test(expected=InscriptionClubException.class)
    public void testInscriptionClubWithTeamAlreadyInDB() throws Exception{
        final List<Team> list = new ArrayList<Team>();
        list.add(team1);
        club.getTeams().add(team1);
    
        club.setEmail("email@email.com");                    
        service.addClub(club);
    }     
    
  //  @Test(expected=InscriptionClubException.class)
    public void testInscriptionClubWithTeamsAlreadyInDB() throws Exception{
        final List<Team> list = new ArrayList<Team>();
        list.add(team1);
        list.add(team2);
        club.getTeams().add(team1);    
        club.getTeams().add(team2);
      //  context.checking(new Expectations() {{
      //      oneOf (clubDao).findClubByName(club.getName());will(returnValue(null));
      //      oneOf (teamDao).getTeams();will(returnValue(list));
      //  }});  
        
        club.setEmail("email@email.com");                    
        service.addClub(club);
    }     
    
   // @Test
    public void testCorrectInscriptionClubWithTeam() throws Exception{
        final List<Team> list = new ArrayList<Team>();    
        final List<Club> listClubs = new ArrayList<Club>();
        listClubs.add(club);
        
        club.getTeams().add(team1);   

        //context.checking(new Expectations() {{
        //    oneOf (clubDao).findClubByName(club.getName());will(returnValue(null));
         //   oneOf (teamDao).getTeams();will(returnValue(list));            
          //  oneOf (clubDao).addClub(club);
         //   oneOf (clubDao).getClubs();will(returnValue(listClubs));
         //   oneOf (teamDao).addTeam(team1);
        //}});  
        
        club.setEmail("email@email.com");                    
        service.addClub(club);
    }     
    
    //@Test
    public void testCorrectInscriptionClubWithTeams() throws Exception{
        final List<Team> list = new ArrayList<Team>();
        final List<Club> listClubs = new ArrayList<Club>();
        listClubs.add(club);
        
        club.getTeams().add(team1);    
        club.getTeams().add(team2);
        
        //context.checking(new Expectations() {{
        //    oneOf (clubDao).findClubByName(club.getName());will(returnValue(null));
        //    oneOf (teamDao).getTeams();will(returnValue(list));
        //    oneOf (clubDao).addClub(club);
        //    oneOf (clubDao).getClubs();will(returnValue(listClubs));
        //    oneOf (teamDao).addTeam(team1); 
        //   oneOf (clubDao).getClubs();will(returnValue(listClubs));           
        //    oneOf (teamDao).addTeam(team2);
        //}});  
        
        club.setEmail("email@email.com");                    
        service.addClub(club);
    }     

  /*
   * PRIVATE OPS
   * 
   * 
   */
    
     private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }


}
