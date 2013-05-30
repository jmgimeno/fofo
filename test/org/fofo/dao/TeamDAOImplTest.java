/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.EntityExistsException;

import org.fofo.entity.Category;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.fofo.entity.Club;
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
    
    Team team;
    Club club;
    
    
    public TeamDAOImplTest() {
    }

    @Before
    public void setup(){
    
      tdao = new TeamDAOImpl();
      
      em  = context.mock(EntityManager.class);
      tdao.setEM(em);
      transaction = context.mock(EntityTransaction.class);
      
       club = new Club();
       club.setName("Lleida FC");
       club.setEmail("lleida@fc.cat");
       
       
       team = new Team();
       team.setCategory(Category.MALE);
       team.setClub(club);
       team.setEmail("popo@fc.cat");
       team.setName("petits Lleida Fc");
      
    }
    
           
        
        
    
    
    /**
     * Test of addTeam method, of class TeamDAOImpl.
     * Addition of a team with a correct club
     */
    @Test
    public void testAddTeam() throws Exception{
    
      context.checking(new Expectations() {{
        atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
        oneOf (transaction).begin();
        oneOf (em).find(Club.class, club.getName()); will(returnValue(club));
        oneOf (em).persist(team);
        oneOf (transaction).commit();
        oneOf (transaction).isActive();
       }});

      tdao.addTeam(team);
   }

    /**
     * Test of addTeam method, of class TeamDAOImpl.
     * Addition of a team with an incorrect club (non existing club)
     */
    @Test(expected=IncorrectTeamException.class)
    public void testAddTeamNonExistingClub() throws Exception{
   
      context.checking(new Expectations() {{
        atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
        oneOf (transaction).begin();
        oneOf (em).find(Club.class, club.getName()); will(returnValue(null));
        never (em).persist(team);
        oneOf (transaction).isActive(); will (returnValue(true));
        oneOf (transaction).rollback();
        
       }});


      tdao.addTeam(team);
      
      

        
        
    }
    
    /**
     * Test of addTeam method, of class TeamDAOImpl.
     * Addition of a team with an incorrect club (team has no club assigned)
     */
    @Test(expected=IncorrectTeamException.class)
    public void testAddTeamWithNotAssignedClub() throws Exception{
   
      team.setClub(null);  
        
      context.checking(new Expectations() {{
        atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
        oneOf (transaction).begin();
        oneOf (transaction).isActive(); will (returnValue(true));
        oneOf (transaction).rollback();
        
       }});


      tdao.addTeam(team);
        
    }
    

        /**
     * Test of addTeam method, of class TeamDAOImpl.
     * Addition of a team with an incorrect club (team has a club assigned, 
     *  but this club has no name)
     */
    @Test(expected=IncorrectTeamException.class)
    public void testAddTeamWithNoNamedClub() throws Exception{
   
      team.getClub().setName(null);  
        
      context.checking(new Expectations() {{
        atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
        oneOf (transaction).begin();
        oneOf (transaction).isActive(); will (returnValue(true));
        oneOf (transaction).rollback();
        
       }});


      tdao.addTeam(team);
      
      

        
        
    }
    

    
    /**
     * Test of addTeam method, of class TeamDAOImpl.
     * Addition of an already existing team
     */
    @Test(expected=PersistException.class)
    public void testAddAlreadyExistingTeam() throws Exception{

        
      context.checking(new Expectations() {{
        atLeast(1).of (em).getTransaction(); will(returnValue(transaction));        
        oneOf (transaction).begin();
        oneOf (em).find(Club.class, club.getName()); will(returnValue(club));
        oneOf (em).persist(team); will(throwException(new EntityExistsException()));
        oneOf (transaction).isActive(); will (returnValue(true));
        oneOf (transaction).rollback();
        
       }});


      tdao.addTeam(team);
      
    }
    
    
    
    
    
}
