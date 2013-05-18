/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.CompetitionLeague;
import org.fofo.entity.Team;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Alejandro
 */
public class CompetitionDAOImplIntegTest {

    EntityManager em;
    EntityTransaction transaction;
    CompetitionDAOImpl competitionDAO;
    Team team;
    Competition competition;
    Club club;

    @Before
    public void setUp() {

        em = null;
        transaction = null;
        competitionDAO = new CompetitionDAOImpl();
        team = new Team("name");
        competition = new CompetitionLeague();
        competition.setName("League1");
        club = new Club("name");
        competitionDAO.setEM(em);
        team.setClub(club);

    }

    //@Test
    
   // De momento no lo podemos hacer pq el getCompetition no esta 
    // implentado no?
    public void addCompetition() throws PersistException {

        competitionDAO.addCompetition(competition);
        Competition comp = competitionDAO.findCompetitionByName("League1");
        assertNotNull(comp);
    }

    @Test
    public void addTeam_To_Competition() throws PersistException {

        competition.addTeam(team);
       List<Team> expected = new ArrayList();
       expected.add(team);
       
       List<Team> obtained = competition.getTeams();
       
       assertEquals(expected, obtained);
    }

    @Test(expected = PersistException.class)
    public void add_incorrect_team_to_competition() throws PersistException {
        competitionDAO.addTeam(competition, null);
        
    }
    
    @Test(expected=PersistException.class)
    public void addTeamtoIncorrectCompetition() throws Exception{
        competitionDAO.addTeam(null, team);
    }
    
    @Test(expected=PersistException.class)
    public void addTeamWithoutClubToCompetition() throws Exception{
        Team team2 = new Team("team2");
        competitionDAO.addTeam(competition, team2);
    }
}