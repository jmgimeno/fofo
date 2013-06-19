package org.fofo.services.management;

import org.fofo.services.management.exception.CompetitionWithoutClassificationTCException;
import org.fofo.services.management.exception.InvalidRequisitsException;
import org.fofo.services.management.exception.CompetitionWithoutCorrectClassificationTCException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.dao.*;
import org.fofo.entity.*;
import org.fofo.utils.Classification;
import org.fofo.utils.InfoClassTeam;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 *
 * @author Jordi Niubo i Oriol Capell
 */


public class ClassificationOfCompetitionIntegTest {
    CompetitionRunServices  service; 
    
    EntityManager em = null;
    
    CompetitionDAOImpl compdao = null;
    ClubDAOImpl clubdao = null;
    TeamDAOImpl tdao = null;    
    
    Competition compLeague;  
    
    Team team1, team2, team3, team4, team5, team6, team7, team8, team9, team10;
     
    Club club;
    List<Team> teams;
    
    public ClassificationOfCompetitionIntegTest() {
    }

    @Before
    public void setUp() throws Exception {    
        em = getEntityManagerFact(); 
        
        service = new CompetitionRunServices();  
        
        compdao = new CompetitionDAOImpl();  
        compdao.setEM(em);
        
        clubdao = new ClubDAOImpl(); 
        clubdao.setEM(em); 
        
        tdao = new TeamDAOImpl();   
        tdao.setEM(em);
        
        compLeague = Competition.create(CompetitionType.LEAGUE);        
        compLeague.setName("Competition League"); 
                
        createClub();
        createTeams();
        createCompetition(compLeague);  
        
    }
    
    @After
    public void tearDown() throws Exception{  
        if (em.isOpen()) em.close();
        
        em = getEntityManagerFact();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Team");       
        Query query2=em.createQuery("DELETE FROM Competition");     
        Query query3=em.createQuery("DELETE FROM FCalendar");     
        Query query4=em.createQuery("DELETE FROM WeekMatch");     
        Query query5=em.createQuery("DELETE FROM Match");        
        Query query6=em.createQuery("DELETE FROM Club");
        Query query7=em.createQuery("DELETE FROM Referee");  
        Query query8=em.createQuery("DELETE FROM ClassificationTC");          
         
        int deleteRecords=query.executeUpdate();      //Delete Team
        deleteRecords=query2.executeUpdate();          //Delete Competition
        deleteRecords=query3.executeUpdate();          //Delete FCalendar
        deleteRecords=query4.executeUpdate();          //Delete WeekMatch
        deleteRecords=query5.executeUpdate();           //Delete Match       
        deleteRecords=query6.executeUpdate();           //Delete Club          
        deleteRecords=query7.executeUpdate();           //Delete Referee  
        deleteRecords=query8.executeUpdate();           //Delete ClassificationTC          
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");       
     
    }


    @Test(expected=InvalidRequisitsException.class)
    public void testGetClassificationOfCompetitionWithNoDAO() throws Exception {
        service.getClassificationOfCompetition(compLeague);
    }
              
    
    @Test
    public void testCompetitionOfClassification() throws Exception{         
        service.setCompetitionDao(compdao);
        
        Classification classification =  service.getClassificationOfCompetition(compLeague);
        assertEquals(compLeague,classification.getCompetition());        
    }    
    
    @Test
    public void testInfoTeamOfClassification() throws Exception{   
        service.setCompetitionDao(compdao);
        
        List<InfoClassTeam> InfoClassTeams = new ArrayList<InfoClassTeam>();        
        for(Team t : compLeague.getTeams()){
             InfoClassTeam infoTeam = new InfoClassTeam(t);
             InfoClassTeams.add(infoTeam);
        }
      
        Classification classification =  service.getClassificationOfCompetition(compLeague);

        assertTrue(classification.getInfoClassTeam().containsAll(InfoClassTeams));
    } 
    
    @Test
    public void testCorrectOrderOfClassification() throws Exception{      
        service.setCompetitionDao(compdao);
        
        List<InfoClassTeam> InfoClassTeams = new ArrayList<InfoClassTeam>();
        for(Team t : teams){
             InfoClassTeam infoTeam = new InfoClassTeam(t);
             InfoClassTeams.add(infoTeam);
        }
        
        Classification classification =  service.getClassificationOfCompetition(compLeague);
        
        List<InfoClassTeam> InfoCTDB = classification.getInfoClassTeam(); 
               
        assertTrue(InfoCTDB.equals(InfoClassTeams));        
    }    
     
    
    @Test
    public void testCorrectOrderOfClassificationWithUpdate() throws Exception{      
        service.setCompetitionDao(compdao);
        
        updateClassification();
        List<InfoClassTeam> InfoClassTeams = createInfoTeam();
 
        
        Classification classification =  service.getClassificationOfCompetition(compLeague);
        
        List<InfoClassTeam> InfoCTDB = classification.getInfoClassTeam(); 
        
        System.out.println(classification.toString());   
        
        assertTrue(InfoCTDB.equals(InfoClassTeams));        
    }    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
      
   
    private EntityManager getEntityManagerFact() throws Exception{

     try{
         EntityManagerFactory emf = 
                 Persistence.createEntityManagerFactory("fofo");
         return emf.createEntityManager();  
     }
     catch(Exception e){
         System.out.println("ERROR CREATING ENTITY MANAGER FACTORY");
	 throw e;
     }

    }
    
    private void createCompetition(Competition comp) throws Exception{   
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        comp.setInici(new DateTime().minusDays(8).toDate()); 
        comp.setTeams(teams);  
                
        compdao.addCompetition(comp);
        
        addClassification(comp);
    }
    
    private void addClassification(Competition comp) throws Exception{
        for(Team t : comp.getTeams()){
            compdao.addClassificationTC(t.getName(), comp.getName());
        }
    }
    
    private void updateClassification() throws Exception{
        compdao.addPointsToClassificationTC(teams.get(0).getName(), compLeague.getName(), 10);
        compdao.addPointsToClassificationTC(teams.get(1).getName(), compLeague.getName(), 1);
        compdao.addPointsToClassificationTC(teams.get(2).getName(), compLeague.getName(), 5);
        compdao.addPointsToClassificationTC(teams.get(3).getName(), compLeague.getName(), 87);
        compdao.addPointsToClassificationTC(teams.get(4).getName(), compLeague.getName(), 3);
        compdao.addPointsToClassificationTC(teams.get(5).getName(), compLeague.getName(), 66);
        compdao.addPointsToClassificationTC(teams.get(6).getName(), compLeague.getName(), 40);
        compdao.addPointsToClassificationTC(teams.get(7).getName(), compLeague.getName(), 34);
        compdao.addPointsToClassificationTC(teams.get(8).getName(), compLeague.getName(), 33);
        compdao.addPointsToClassificationTC(teams.get(9).getName(), compLeague.getName(), 44);
        compdao.addPointsToClassificationTC(teams.get(10).getName(), compLeague.getName(), 55);
        compdao.addPointsToClassificationTC(teams.get(11).getName(), compLeague.getName(), 8);
        compdao.addPointsToClassificationTC(teams.get(12).getName(), compLeague.getName(), 4);
        compdao.addPointsToClassificationTC(teams.get(13).getName(), compLeague.getName(), 6);
        compdao.addPointsToClassificationTC(teams.get(14).getName(), compLeague.getName(), 88);
        compdao.addPointsToClassificationTC(teams.get(15).getName(), compLeague.getName(), 36);
    }

    private List<InfoClassTeam> createInfoTeam() {
        InfoClassTeam infoTeam;
        
        List<InfoClassTeam> list = new ArrayList<InfoClassTeam>();
        
        infoTeam = new InfoClassTeam(teams.get(14));
        infoTeam.setPoints(88);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(3));
        infoTeam.setPoints(87);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(5));
        infoTeam.setPoints(66);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(10));
        infoTeam.setPoints(55);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(9));
        infoTeam.setPoints(44);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(6));
        infoTeam.setPoints(40);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(15));
        infoTeam.setPoints(36);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(7));
        infoTeam.setPoints(34);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(8));
        infoTeam.setPoints(33);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(0));
        infoTeam.setPoints(10);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(11));
        infoTeam.setPoints(8);
        list.add(infoTeam); 
        infoTeam = new InfoClassTeam(teams.get(13));
        infoTeam.setPoints(6);
        list.add(infoTeam);               
        infoTeam = new InfoClassTeam(teams.get(2));
        infoTeam.setPoints(5);
        list.add(infoTeam);
        infoTeam = new InfoClassTeam(teams.get(12));
        infoTeam.setPoints(4);
        list.add(infoTeam);  
        infoTeam = new InfoClassTeam(teams.get(4));
        infoTeam.setPoints(3);
        list.add(infoTeam);      
        infoTeam = new InfoClassTeam(teams.get(1));
        infoTeam.setPoints(1);
        list.add(infoTeam);
       

        return list;
    }

    private void createClub() throws Exception {    
        club = new Club();
        club.setName("Imaginary club");
        club.setEmail("email@email.com");        
        clubdao.addClub(club);
    }

    private void createTeams() throws Exception {      
        teams = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            Team team = new Team("Team number "+i,club, Category.MALE);       
            teams.add(team);
            tdao.addTeam(team);
        }
    }


    
}