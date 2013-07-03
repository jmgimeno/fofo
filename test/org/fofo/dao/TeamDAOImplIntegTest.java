/*
 * EXAMPLE OF INTEGRATION TEST FOR TeamDAOImpl
 */
package org.fofo.dao;

import org.fofo.dao.exception.IncorrectTeamException;
import org.fofo.dao.exception.PersistException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 *
 * @author josepma
 */
public class TeamDAOImplIntegTest {

    private EntityManager em;
    private TeamDAOImpl tdao;
    private Club club;
    private PlayerDAOImpl pdao;

    public TeamDAOImplIntegTest() {
    }

    @Before
    public void setup() throws Exception {

        club = new Club();

        club.setName("Lleida");
        club.setEmail("lleida@lleida.net");

        tdao = new TeamDAOImpl();
        pdao = new PlayerDAOImpl();

        em = getEntityManagerFact();
        em.getTransaction().begin();
        em.persist(club);
        em.getTransaction().commit();

        pdao.setEm(em);
        tdao.setEM(em);
    }

    /**
     * Test of addTeam method, of class TeamDAO. Case: correct insertion
     */
    @Test
    public void testAddTeam() throws Exception {

        final Team team = new Team("team2");

        team.setClub(club);

        tdao.addTeam(team);

        Team teamDB = getTeamFromDB("team2");

        assertEquals("Should have found the inserted team",
                team, teamDB);
    }

    /**
     * Test of addTeam method, of class TeamDAO. Case: Club of team not inserted
     * into the db
     */
    @Test(expected = IncorrectTeamException.class)
    public void testAddTeamIncorrectClub() throws Exception {

        final Team team = new Team("team2");

        Club club2 = new Club();

        club2.setName("Barna");
        club2.setEmail("barna@fb.net");

        team.setClub(club2);

        tdao.addTeam(team);
    }

    /**
     * Test of addTeam method, of class TeamDAO. Case: already existing team
     * into the db
     *
     *  ****ATTENTION: THIS TEST DOES NOT WORK. TWO PERSIST OF THE SAME ENTITY
     * DO NOT THROW AN EXCEPTION. TOPLINK PROBLEMS???????
     *
     */
    @Test(expected = PersistException.class)
    public void testAddExistingTeam() throws Exception {

        Team team = new Team("team2");
        team.setClub(club);

        em.getTransaction().begin();
        em.persist(team);
        em.getTransaction().commit();
        em.clear();
        tdao.addTeam(team);
    }

    @Test
    public void testGetTeams() throws Exception {

        Team team = new Team("team2");
        team.setClub(club);

        tdao.addTeam(team);

        List<Team> expected = new ArrayList<Team>();
        expected.add(team);

        assertEquals("There should be only team2",
                expected, tdao.getTeams());
    }

    @Test
    public void testFindTeamByName() throws Exception {
        Team team = new Team("team2");
        team.setClub(club);

        tdao.addTeam(team);
        assertEquals(team, tdao.findTeamByName("team2"));
    }
    
    @Test
    public void testGetTeamsByClub() throws Exception {
        List<Team> teams = new ArrayList<Team>();
        Team team = new Team("team3");
        team.setClub(club);
        teams.add(team);
        tdao.addTeam(team);
        
        assertEquals(teams, tdao.findTeamsByClub(club.getName()));
        
    }
    
    
    @Test
    public void testAddPlayerToTeam() throws Exception {
        Team team = new Team("team2");
        team.setClub(club);

        Player player = new Player("nifPlayer", "namePlayer");
        tdao.addTeam(team);
        pdao.addPlayer(player);
        tdao.setPlayerDB(pdao);

        tdao.addPlayerToTeam(team.getName(), player.getNif());
        assertEquals(team, player.getTeam());

    }

    @Test
    public void testGetPlayersOfTeam() throws Exception {
        Team team = new Team("team2");
        team.setClub(club);

        List<Player> players = new ArrayList<Player>();
        Player player = new Player("nifPlayer", "namePlayer");
        Player player1 = new Player("nifPlayer2", "namePlayer2");
        players.add(player);
        players.add(player1);

        tdao.addTeam(team);
        pdao.addPlayer(player);
        pdao.addPlayer(player1);
        tdao.setPlayerDB(pdao);

        tdao.addPlayerToTeam(team.getName(), player.getNif());
        tdao.addPlayerToTeam(team.getName(), player1.getNif());
        assertEquals(players, pdao.findPlayersByTeam(team.getName()));
    }

    @Test
    public void testGetNumberOfPlayers() throws Exception {
        Team team = new Team("team3");
        team.setClub(club);
        
        List<Player> players = new ArrayList<Player>();
        Player player1 = new Player("nifPlayer1", "namePlayer1");
        Player player2 = new Player("nifPlayer2", "namePlayer2");
        Player player3 = new Player("nifPlayer3", "namePlayer3");
        Player player4 = new Player("nifPlayer4", "namePlayer4");
        Player player5 = new Player("nifPlayer5", "namePlayer5");
        Player player6 = new Player("nifPlayer6", "namePlayer6");
        Player player7 = new Player("nifPlayer7", "namePlayer7");
        Player player8 = new Player("nifPlayer8", "namePlayer8");
        Player player9 = new Player("nifPlayer9", "namePlayer9");
        Player player10 = new Player("nifPlayer10", "namePlayer10");
        Player player11 = new Player("nifPlayer11", "namePlayer11");
        
        players.add(player1); players.add(player2);
        players.add(player3); players.add(player4);
        players.add(player5); players.add(player6);
        players.add(player7); players.add(player8);
        players.add(player9); players.add(player10);
        players.add(player11);
        
        tdao.addTeam(team);
        pdao.addPlayer(player1); pdao.addPlayer(player2);
        pdao.addPlayer(player3); pdao.addPlayer(player4);
        pdao.addPlayer(player5); pdao.addPlayer(player6);
        pdao.addPlayer(player7); pdao.addPlayer(player8);
        pdao.addPlayer(player9); pdao.addPlayer(player10);
        pdao.addPlayer(player11); 
        tdao.setPlayerDB(pdao);
       
        assertEquals(players.size(), tdao.getNumberOfPlayers());
    }

    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    private EntityManager getEntityManagerFact() throws Exception {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("fofo");

        return emf.createEntityManager();
    }

    private Team getTeamFromDB(String name) throws Exception {

        em = getEntityManagerFact();
        em.getTransaction().begin();
        Team teamDB = em.find(Team.class, name);
        em.getTransaction().commit();
        em.close();
        return teamDB;
    }

    @After
    public void tearDown() throws Exception {
        EntityManager em = getEntityManagerFact();
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM Team st");
        Query query2 = em.createQuery("DELETE FROM Club cl");
        Query query3 = em.createQuery("DELETE FROM Player");

        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        deleteRecords = query3.executeUpdate();

        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
    }
}
