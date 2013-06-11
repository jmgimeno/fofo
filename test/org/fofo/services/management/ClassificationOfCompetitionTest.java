package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import org.fofo.dao.*;
import org.fofo.entity.*;
import org.fofo.utils.Classification;
import org.fofo.utils.Classification.InfoClassTeam;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 *
 * @author Jordi Niubo i Oriol Capell
 */

@RunWith(JMock.class)
public class ClassificationOfCompetitionTest {
    CompetitionRunServices  service;   
    Mockery context = new JUnit4Mockery();
    
    CompetitionDAO competitionDao;
    
    Competition comp;   
    Team team1, team2, team3, team4, team5, team6, team7, team8, team9, team10;
    ClassificationTC ClassificationTC1, ClassificationTC2, ClassificationTC3, ClassificationTC4,           
            ClassificationTC5, ClassificationTC6, ClassificationTC7, ClassificationTC8,  
            ClassificationTC9, ClassificationTC10;    
    
    InfoClassTeam infoTeam1, infoTeam2, infoTeam3, infoTeam4,
            infoTeam5, infoTeam6, infoTeam7, infoTeam8, infoTeam9, infoTeam10;
    
    public ClassificationOfCompetitionTest() {
    }

    @Before
    public void setUp() throws Exception {     
        service = new CompetitionRunServices();        
        competitionDao = context.mock(CompetitionDAO.class);    
        
        comp = Competition.create(CompetitionType.LEAGUE);        
        createCompetition();    
        createInfoTeam();
        createClassificationTC();
    }


    @Test(expected=InvalidRequisitsException.class)
    public void testGetClassificationOfCompetitionWithNoDAO() throws Exception {
        service.getClassificationOfCompetition(comp);
    }
            
    @Test(expected= CompetitionWithoutClassificationTCException.class)
    public void testGetClassificationOfCompetitionWithNoClassificationsTC() throws Exception{    
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(null));
            }
        }); 
        
        service.getClassificationOfCompetition(comp);
    }
      
    @Test(expected= CompetitionWithoutCorrectClassificationTCException.class)
    public void testGetClassificationOfCompetitionWithoutCorrectClassificationTC() throws Exception{  
        final List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();
        
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(classificationsTC));
            }
        }); 
        
        service.getClassificationOfCompetition(comp);
    }    
    
    
    @Test(expected= CompetitionWithoutCorrectClassificationTCException.class)
    public void testGetClassificationOfCompetitionWithIncorrectNumberClassificationTC() throws Exception{  
        final List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();
        classificationsTC.add(ClassificationTC1);   
        classificationsTC.add(ClassificationTC4);
        
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(classificationsTC));
            }
        }); 
        
        service.getClassificationOfCompetition(comp);
    }      
    
    @Test
    public void testCompetitionOfClassification() throws Exception{  
        final List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();
        classificationsTC.add(ClassificationTC1);   
        classificationsTC.add(ClassificationTC2);  
        classificationsTC.add(ClassificationTC3);        
        classificationsTC.add(ClassificationTC4);
        
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(classificationsTC));
            }
        }); 
        
        Classification classification =  service.getClassificationOfCompetition(comp);
        assertEquals(comp,classification.getCompetition());
    }    
    
    @Test
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
        
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(classificationsTC));
            }
        }); 
        
        Classification classification =  service.getClassificationOfCompetition(comp);

        assertTrue(classification.getInfoClassTeam().containsAll(InfoClassTeams));
        
        
    } 
    
    @Test
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
        
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(classificationsTC));
            }
        }); 
        
        Classification classification =  service.getClassificationOfCompetition(comp);
        
        List<InfoClassTeam> InfoCTDB = classification.getInfoClassTeam();         
        
        assertTrue(InfoCTDB.equals(InfoClassTeams));        
    }    
    
    
    @Test
    public void testCorrectOrderOfClassificationWith10Teams() throws Exception{  
        createNewTeams();
        
        final List<ClassificationTC> specialClassificationsTC = createNewClassificationTC();
        List<InfoClassTeam> specialInfoClassTeams = createNewInfoTeam();
                
        service.setCompetitionDao(competitionDao);
        context.checking(new Expectations() {
            {
                oneOf(competitionDao).findClassificationsTC(comp.getName());
                                    will(returnValue(specialClassificationsTC));
            }
        }); 
        
        Classification classification =  service.getClassificationOfCompetition(comp);
        
        List<InfoClassTeam> InfoCTDB = classification.getInfoClassTeam();
        
System.out.println(classification.toString());         
        
        assertTrue(InfoCTDB.equals(specialInfoClassTeams));        
    }    
    
    
    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    private void createCompetition() {   
        comp.setName("Competition 1");
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);    
        comp.setInici(new DateTime().minusDays(8).toDate());         

        createTeams(); 
    }

    private void createTeams() {
        team1 = new Team("Team1", Category.FEMALE);
        team1.setEmail("Team1@hotmail.com");
        comp.addTeam(team1);

        team2 = new Team("Team2", Category.FEMALE);
        team2.setEmail("Team2@hotmail.com");
        comp.addTeam(team2);
        
        team3 = new Team("Team3", Category.FEMALE);
        team3.setEmail("Team3@hotmail.com");
        comp.addTeam(team3);
        
        team4 = new Team("Team4", Category.FEMALE);
        team4.setEmail("Team4@hotmail.com");  
        comp.addTeam(team4);        
    }
    
    private void createInfoTeam() {
        infoTeam1 = new InfoClassTeam(team1);
        infoTeam2 = new InfoClassTeam(team2);
        infoTeam3 = new InfoClassTeam(team3);
        infoTeam4 = new InfoClassTeam(team4);
    }
    
    private void createClassificationTC() {       
        ClassificationTC1 = new ClassificationTC(comp, team1);
        ClassificationTC2 = new ClassificationTC(comp, team2);
        ClassificationTC3 = new ClassificationTC(comp, team3);
        ClassificationTC4 = new ClassificationTC(comp, team4);
    }    
    
    private void createNewTeams(){                
        team5 = new Team("Team5", Category.FEMALE);
        team5.setEmail("Team5@hotmail.com");  
        comp.addTeam(team5); 
        
        team6 = new Team("Team6", Category.FEMALE);
        team6.setEmail("Team6@hotmail.com");  
        comp.addTeam(team6);   
        
        team7 = new Team("Team7", Category.FEMALE);
        team7.setEmail("Team7@hotmail.com");  
        comp.addTeam(team7); 
        
        team8 = new Team("Team8", Category.FEMALE);
        team8.setEmail("Team8@hotmail.com");  
        comp.addTeam(team8); 
        
        team9 = new Team("Team9", Category.FEMALE);
        team9.setEmail("Team9@hotmail.com");  
        comp.addTeam(team9);   
        
        team10 = new Team("Team10", Category.FEMALE);
        team10.setEmail("Team10@hotmail.com");  
        comp.addTeam(team10);
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
        ClassificationTC2 = new ClassificationTC(comp, team2);
        ClassificationTC2.setPoints(100);list.add(ClassificationTC2);
        
        ClassificationTC5 = new ClassificationTC(comp, team5);
        ClassificationTC5.setPoints(67);list.add(ClassificationTC5);
        
        ClassificationTC7 = new ClassificationTC(comp, team7);
        ClassificationTC7.setPoints(56);list.add(ClassificationTC7);
        
        ClassificationTC9 = new ClassificationTC(comp, team9);
        ClassificationTC9.setPoints(53);list.add(ClassificationTC9);
        
        ClassificationTC10 = new ClassificationTC(comp, team10);
        ClassificationTC10.setPoints(44);list.add(ClassificationTC10);
        
        ClassificationTC4 = new ClassificationTC(comp, team4);  
        ClassificationTC4.setPoints(34);list.add(ClassificationTC4);
        
        ClassificationTC3 = new ClassificationTC(comp, team3);
        ClassificationTC3.setPoints(23);list.add(ClassificationTC3);  
        
        ClassificationTC6 = new ClassificationTC(comp, team6);
        ClassificationTC6.setPoints(12);list.add(ClassificationTC6);
        
        ClassificationTC1 = new ClassificationTC(comp, team1);
        ClassificationTC1.setPoints(10);list.add(ClassificationTC1);  
        
        ClassificationTC8 = new ClassificationTC(comp, team8);
        ClassificationTC8.setPoints(9);list.add(ClassificationTC8);
        
        return list;
    }
   
    
    
}