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
import org.fofo.utils.Classification.InfoClassTeam;
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
    
    ClassificationTC ClassificationTC1, ClassificationTC2, ClassificationTC3, ClassificationTC4,           
            ClassificationTC5, ClassificationTC6, ClassificationTC7, ClassificationTC8,  
            ClassificationTC9, ClassificationTC10;    
    
    InfoClassTeam infoTeam1, infoTeam2, infoTeam3, infoTeam4,
            infoTeam5, infoTeam6, infoTeam7, infoTeam8, infoTeam9, infoTeam10;
    
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
        
        createInfoTeam();
        createClassificationTC();
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
    
    //@Test
    public void testInfoTeamOfClassification() throws Exception{  
        final List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();
        classificationsTC.add(ClassificationTC1);   
        classificationsTC.add(ClassificationTC2);  
        classificationsTC.add(ClassificationTC3);        
        classificationsTC.add(ClassificationTC4);
        
        List<InfoClassTeam> InfoClassTeams = new ArrayList<InfoClassTeam>();
        InfoClassTeams.add(infoTeam1);   
        InfoClassTeams.add(infoTeam2);     
        InfoClassTeams.add(infoTeam3);     
        InfoClassTeams.add(infoTeam4);
        
        service.setCompetitionDao(compdao);
//        context.checking(new Expectations() {
//            {
//                oneOf(compdao).findClassificationsTC(compLeague.getName());
//                                    will(returnValue(classificationsTC));
//            }
//        }); 
        
        Classification classification =  service.getClassificationOfCompetition(compLeague);

        assertTrue(classification.getInfoClassTeam().containsAll(InfoClassTeams));
        
        
    } 
    
    //@Test
    public void testCorrectOrderOfClassification() throws Exception{  
        final List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();
        ClassificationTC1.setPoints(30);
        classificationsTC.add(ClassificationTC1);
        
        ClassificationTC2.setPoints(120); 
        classificationsTC.add(ClassificationTC2); 
 
        ClassificationTC3.setPoints(5);      
        classificationsTC.add(ClassificationTC3);    
        classificationsTC.add(ClassificationTC4);
        
        List<InfoClassTeam> InfoClassTeams = new ArrayList<InfoClassTeam>();
        infoTeam1.setPoints(30);   
        infoTeam2.setPoints(120);   
        infoTeam3.setPoints(5); 
        
        InfoClassTeams.add(infoTeam2); 
        InfoClassTeams.add(infoTeam1);  
        InfoClassTeams.add(infoTeam3); 
        InfoClassTeams.add(infoTeam4);
        
        service.setCompetitionDao(compdao);
//        context.checking(new Expectations() {
//            {
//                oneOf(compdao).findClassificationsTC(compLeague.getName());
//                                    will(returnValue(classificationsTC));
//            }
//        }); 
        
        Classification classification =  service.getClassificationOfCompetition(compLeague);
        
        List<InfoClassTeam> InfoCTDB = classification.getInfoClassTeam();         
        
        assertTrue(InfoCTDB.equals(InfoClassTeams));        
    }    
    
    
    //@Test
    public void testCorrectOrderOfClassificationWith10Teams() throws Exception{  
        //createNewTeams();
        
        final List<ClassificationTC> specialClassificationsTC = createNewClassificationTC();
        List<InfoClassTeam> specialInfoClassTeams = createNewInfoTeam();
                
        service.setCompetitionDao(compdao);
//        context.checking(new Expectations() {
//            {
//                oneOf(compdao).findClassificationsTC(compLeague.getName());
//                                    will(returnValue(specialClassificationsTC));
//            }
//        }); 
        
        Classification classification =  service.getClassificationOfCompetition(compLeague);
        
        List<InfoClassTeam> InfoCTDB = classification.getInfoClassTeam();
        
System.out.println(classification.toString());         
        
        assertTrue(InfoCTDB.equals(specialInfoClassTeams));        
    }    
    
    
    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    
    private void createInfoTeam() {
        infoTeam1 = new InfoClassTeam(team1);
        infoTeam2 = new InfoClassTeam(team2);
        infoTeam3 = new InfoClassTeam(team3);
        infoTeam4 = new InfoClassTeam(team4);
    }
    
    private void createClassificationTC() {       
        ClassificationTC1 = new ClassificationTC(compLeague, team1);
        ClassificationTC2 = new ClassificationTC(compLeague, team2);
        ClassificationTC3 = new ClassificationTC(compLeague, team3);
        ClassificationTC4 = new ClassificationTC(compLeague, team4);
    }    
    
    private List<InfoClassTeam> createNewInfoTeam() {  
        List<InfoClassTeam> list = new ArrayList<InfoClassTeam>();
        
        infoTeam2 = new InfoClassTeam(team2);
        infoTeam2.setPoints(100); 
        list.add(infoTeam2);  
        
        infoTeam5 = new InfoClassTeam(team5);
        infoTeam5.setPoints(67);     
        list.add(infoTeam5); 
        
        infoTeam7 = new InfoClassTeam(team7);
        infoTeam7.setPoints(56);
        list.add(infoTeam7);   
         
        infoTeam9 = new InfoClassTeam(team9);
        infoTeam9.setPoints(53);   
        list.add(infoTeam9);    
        
        infoTeam10 = new InfoClassTeam(team10); 
        infoTeam10.setPoints(44); 
        list.add(infoTeam10);
        
        infoTeam4 = new InfoClassTeam(team4);
        infoTeam4.setPoints(34); 
        list.add(infoTeam4); 
        
        infoTeam3 = new InfoClassTeam(team3);
        infoTeam3.setPoints(23); 
        list.add(infoTeam3);  
        
        infoTeam6 = new InfoClassTeam(team6);
        infoTeam6.setPoints(12); 
        list.add(infoTeam6);
        
        infoTeam1 = new InfoClassTeam(team1);
        infoTeam1.setPoints(10);     
        list.add(infoTeam1);   
        
        infoTeam8 = new InfoClassTeam(team8);
        infoTeam8.setPoints(9);   
        list.add(infoTeam8);
        
        return list;
    }

    private List<ClassificationTC> createNewClassificationTC() {  
        List<ClassificationTC> list = new ArrayList<ClassificationTC>();                
        ClassificationTC2 = new ClassificationTC(compLeague, team2);
        ClassificationTC2.setPoints(100);list.add(ClassificationTC2);
        
        ClassificationTC5 = new ClassificationTC(compLeague, team5);
        ClassificationTC5.setPoints(67);list.add(ClassificationTC5);
        
        ClassificationTC7 = new ClassificationTC(compLeague, team7);
        ClassificationTC7.setPoints(56);list.add(ClassificationTC7);
        
        ClassificationTC9 = new ClassificationTC(compLeague, team9);
        ClassificationTC9.setPoints(53);list.add(ClassificationTC9);
        
        ClassificationTC10 = new ClassificationTC(compLeague, team10);
        ClassificationTC10.setPoints(44);list.add(ClassificationTC10);
        
        ClassificationTC4 = new ClassificationTC(compLeague, team4);  
        ClassificationTC4.setPoints(34);list.add(ClassificationTC4);
        
        ClassificationTC3 = new ClassificationTC(compLeague, team3);
        ClassificationTC3.setPoints(23);list.add(ClassificationTC3);  
        
        ClassificationTC6 = new ClassificationTC(compLeague, team6);
        ClassificationTC6.setPoints(12);list.add(ClassificationTC6);
        
        ClassificationTC1 = new ClassificationTC(compLeague, team1);
        ClassificationTC1.setPoints(10);list.add(ClassificationTC1);  
        
        ClassificationTC8 = new ClassificationTC(compLeague, team8);
        ClassificationTC8.setPoints(9);list.add(ClassificationTC8);
        
        return list;
    }
   
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
        
System.out.println("****addCompetition");  

        for(Team t : teams){
            compdao.addClassificationTC(t.getName(), comp.getName());
        }
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