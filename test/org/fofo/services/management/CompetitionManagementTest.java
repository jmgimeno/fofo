/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

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
        //...

        
    }
    
     /*
     * 
     * A competition with MALE category is OK
     * 
     * 
     */
    
    //@Test
    public void testCorrectCategory() {
    
        service.addCompetition(comp);
        //......
    }
    
    
    /*
     * 
     * A category which is not either MALE, FEMALE, VETERAN throws an exception
     * of type ***********
     * 
     */
    
    @Test(expected=IncorrectCompetitionData.class)
    public void testIncorrectCategory() {
       Competition comp2 = new  Competition();
       
       comp2.setMaxTeams(12);
       //...
       comp2.setCategory(null);
       
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
    public void competitionInsertedIntoTheDB() {
      
        
        
        context.checking(new Expectations() {{
            oneOf (cdao).addCompetition(comp);
            
        }});

        service.addCompetition(comp);      
        
    }
    
}
