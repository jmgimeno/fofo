/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.fofo.entity.Club;
import org.fofo.entity.Team;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author RogerTorra, Anatoli, Mohamed
 */
@RunWith(JMock.class)
public class ClubDAOImplTest {
    
    private Mockery context = new JUnit4Mockery();

    private ClubDAOImpl cdao;
    
    private EntityManager em;
    private EntityTransaction transaction;
    private Query query;
    
    public ClubDAOImplTest() {
    }
    
    @Before
    public void setUp() {
        cdao = new ClubDAOImpl();        
        em  = context.mock(EntityManager.class);
        cdao.setEM(em);
        
        transaction = context.mock(EntityTransaction.class);
        query = context.mock(Query.class);
    }
    
    
    
    @Test
    public void testAddClub() throws Exception{
        
        final Club club = new Club("club1");
        //club.setTeams(new ArrayList<Team>());
        club.setEmail("club1@mail.com");
        
        transactionExpectations();
        context.checking(new Expectations() {{                 
                oneOf (em).find(Club.class, club.getName());
                will(returnValue(null));  //clubExist -> false
                
                ignoring (em).find(Team.class, null);
                
                oneOf (em).persist(club);               
        }});
        
    
        cdao.addClub(club);
         
    }
    
    @Test (expected=AlreadyExistingClubOrTeamsException.class)
    public void clubAlreadyExistInDB() throws Exception{
        final Club club = new Club("club1");        
        club.setEmail("club1@mail.com");
        
        transactionExpectations();
        context.checking(new Expectations() {{                 
                oneOf (em).find(Club.class, club.getName());
                will(returnValue(club));  //clubExist -> true
                
                ignoring (em).find(Team.class, null);               
        }});        
    
        cdao.addClub(club);
    }
    
    @Test (expected=AlreadyExistingClubOrTeamsException.class)
    public void teamsAlreadyExistInDB() throws Exception{
        final Club club = new Club("club1");        
        club.setEmail("club1@mail.com");
        List<Team> teams = new ArrayList<Team>();
        final Team team = new Team("testTeam");
        teams.add(team);
        club.setTeams(teams);
        
        
        transactionExpectations();
        context.checking(new Expectations() {{
                 
                oneOf (em).find(Club.class, club.getName());
                will(returnValue(null));  
                
                oneOf (em).find(Team.class, team.getName());
                will(returnValue(team));
               
        }});        
    
        cdao.addClub(club);
    }
    
    @Test
    public void testGetClubs() throws Exception{
                
        final Club club = new Club("club1");        
        club.setEmail("club1@mail.com");
        
        final List<Club> expected = new ArrayList<Club>();
        expected.add(club);
        
        transactionExpectations();

        context.checking(new Expectations() {{                 
                oneOf (em).createQuery("SELECT c FROM Club c");
                will(returnValue(query));
                
                oneOf (query).getResultList();
                will(returnValue(expected));
                
        }});

        List<Club> result = cdao.getClubs();
        
        assertEquals(expected,result);
        
    }
    
    @Test
    public void testGetClubByName() throws Exception{
        final Club club = new Club("club1");        
        club.setEmail("club1@mail.com");
 
        
        transactionExpectations();
        //adding club
        context.checking(new Expectations() {{                 
                oneOf (em).find(Club.class, club.getName());
                will(returnValue(club));                  
                           
        }});   
        
        assertEquals(club,cdao.findClubByName("club1"));
    }
    
    @Test
    public void testGetClubByTeam() throws Exception{
        final Club club = new Club("club1");        
        club.setEmail("club1@mail.com");
        List<Team> teams = new ArrayList<Team>();
        final Team team = new Team("testTeam");
        team.setClub(club);
        teams.add(team);
        club.setTeams(teams);
        
        transactionExpectations();

        context.checking(new Expectations() {{                 
                oneOf (em).find(Team.class, team.getName());
                will(returnValue(team));
            
                oneOf (em).find(Club.class, team.getClub().getName());
                will(returnValue(club));                  
                               
        }});   
        
        assertEquals(club,cdao.findClubByTeam("testTeam"));
    }
    
    
    /*PRIVATE OPS*/    
    private void transactionExpectations(){
        context.checking(new Expectations() {{
                allowing (em).getTransaction(); will(returnValue(transaction));        
                allowing (transaction).begin();
                allowing (transaction).commit();
//                allowing (em).isOpen(); will (returnValue(true));
//                allowing (em).close();
        }});
    }
}
