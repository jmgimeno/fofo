/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import org.fofo.entity.*;
import org.fofo.dao.CompetitionDAO;
import org.fofo.services.*;

import org.jmock.States;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import  org.junit.runner.RunWith; 
import org.jmock.Sequence;


/**
 *
 * @author josepma
 */
@RunWith(JMock.class)
public class CompetitionManagementTest {

    ManagementService service;
    
    Mockery context = new JUnit4Mockery();
    
    CompetitionDAO cdao;
    Competition comp;
    
    public CompetitionManagementTest() {
    }

    //Proves unitaries per fer TDD sobre ManagementService.addCompetition(...)  
    @Before
    public void setup(){
    
      service = new ManagementService();  
      cdao  = context.mock(CompetitionDAO.class);

      comp = new Competition();
        
      comp.setCategory(Category.MALE);
      comp.setType(Type.LEAGUE);
      comp.setMinTeams(2);
      comp.setMaxTeams(20);
      Calendar cal = Calendar.getInstance();
      cal.set(2013, Calendar.MAY, 24);
      comp.setInici(cal.getTime());
        //...

        
    }
    
     /*
     * 
     * A competition with MALE category is OK
     * 
     * 
     */
    
    @Test
    public void testCorrectCategory() throws Exception{
    
        service.addCompetition(comp);
        //......
    }
    
    @Test
    public void testCorrectType() throws Exception{
        service.addCompetition(comp);
        //........
    }
    
    @Test
    public void testCorrectMinTeams() throws Exception{
        service.addCompetition(comp);
        //.........
    }
    
    @Test
    public void testCorrectMaxTeams() throws Exception{
        service.addCompetition(comp);
    }
    
    /*
     * 
     * A category which is not either MALE, FEMALE, VETERAN throws an exception
     * of type ***********
     * 
     */
    
    @Test(expected=IncorrectCompetitionData.class)
    public void testIncorrectCategory() throws Exception {
       Competition comp2 = new  Competition();
       
       comp2.setMaxTeams(12);
       //...
       comp2.setCategory(null);
       //TO BE COMPLETED....
       service.addCompetition(comp2);
       
       
    }
    
    @Test(expected=IncorrectTypeData.class)
    public void testIncorrectType() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(null);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMinNumberOfTeams.class)
    public void testIncorrectMinTeams() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.LEAGUE);
        comp2.setMinTeams(1);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMaxNumberOfTeams.class)
    public void testIncorrectMaxTeams() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.LEAGUE);
        comp2.setMaxTeams(21);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMinNumberOfTeams.class)
    public void testMinTeamsNotEven() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.LEAGUE);
        comp2.setMinTeams(3);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMaxNumberOfTeams.class)
    public void testMaxTeamsNotEven() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.LEAGUE);
        comp2.setMaxTeams(17);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMaxNumberOfTeams.class)
    public void testIncorrectMaxTeamsInCup() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.CUP);
        comp2.setMaxTeams(65);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMinNumberOfTeams.class)
    public void testMinTeamsNotPowerOfTwo() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.CUP);
        comp2.setMinTeams(6);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectMaxNumberOfTeams.class)
    public void testMaxTeamsNotPowerOfTwo() throws Exception{
        Competition comp2 = new Competition();
        comp2.setType(Type.CUP);
        comp2.setMaxTeams(18);
        service.addCompetition(comp2);
    }
    
    @Test(expected=IncorrectDate.class)
    public void testIncorrectDate() throws Exception{
        Competition comp2 = new Competition();
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MAY, 14);
        comp2.setInici(cal.getTime());
        service.addCompetition(comp2);
    }
    
    /***
     * 
     * ADD MORE TESTS TO CHECK ALL THE ASPECTS CONSIDERED IN THE ACCEPTANCE TESTS
     * 
     */ 
    
    
    
    /*
     * 
     * A competition should be inserted into the database... Therefore,
     * check that, after ManagementService.addCompetition(comp) 
     * the competition comp has been actually inserted into the database
     * 
     */
    
    //@Test
    public void competitionInsertedIntoTheDB() throws Exception {
      
        
        
        context.checking(new Expectations() {{
            oneOf (cdao).addCompetition(comp);
            
        }});

        service.addCompetition(comp);      
        
    }
    
}
