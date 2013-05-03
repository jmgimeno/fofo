/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.Category;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.fofo.services.management.IncorrectCompetitionData;
import org.fofo.services.management.ManagementService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
/**
 *
 * @author josepma
 */
@RunWith(JMock.class)
public class TeamDAOImplTest {
    
    Mockery context = new JUnit4Mockery();

    TeamDAOImpl tdao;
    
    EntityManager em;
    EntityTransaction transaction;
    
    public TeamDAOImplTest() {
    }

    @Before
    public void setup(){
    
      tdao = new TeamDAOImpl();
      
      em  = context.mock(EntityManager.class);
      tdao.setEM(em);
      transaction = context.mock(EntityTransaction.class);  
    }
    
           
        
        
    
    
    /**
     * Test of addTeam method, of class TeamDAOImpl.
     */
    @Test
    public void testAddTeam() throws Exception{
    
      final Team team = new Team();   
        
      context.checking(new Expectations() {{
        atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
        oneOf (transaction).begin();
        oneOf (em).persist(team);
        oneOf (transaction).commit();
        oneOf (em).isOpen(); will (returnValue(true));
        oneOf (em).close();
      }});

      tdao.addTeam(team);
      
      
    


    }


}
