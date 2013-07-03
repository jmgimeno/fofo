/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.IncorrectTeamException;
import org.fofo.dao.exception.PersistException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;

import org.fofo.entity.Category;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.fofo.entity.Club;
import org.fofo.entity.Player;
import org.fofo.services.management.IncorrectCompetitionData;
import org.fofo.services.management.ManagementService;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
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

    private Mockery context = new JUnit4Mockery();
    private TeamDAOImpl tdao;
    private EntityManager em;
    private EntityTransaction transaction;
    private Query query;
    private Team team;
    private Club club;
    private Player player;
    private PlayerDAO pdao;

    public TeamDAOImplTest() {
    }

    @Before
    public void setup() {

        tdao = new TeamDAOImpl();
        pdao = context.mock(PlayerDAO.class);

        em = context.mock(EntityManager.class);
        tdao.setEM(em);
        transaction = context.mock(EntityTransaction.class);
        query = context.mock(Query.class);

        club = new Club();
        club.setName("Lleida FC");
        club.setEmail("lleida@fc.cat");

        team = new Team();
        team.setCategory(Category.MALE);
        team.setClub(club);
        team.setEmail("popo@fc.cat");
        team.setName("petits Lleida Fc");

        player = new Player("namePlayer", "nifPlayer");

        tdao.setPlayerDB(pdao);
    }

    /**
     * Test of addTeam method, of class TeamDAOImpl. Addition of a team with a
     * correct club
     */
    @Test
    public void testAddTeam() throws Exception {

        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(em).find(Club.class, club.getName());
                will(returnValue(club));
                oneOf(em).persist(team);
                oneOf(transaction).commit();
                oneOf(transaction).isActive();
            }
        });

        tdao.addTeam(team);
    }

    /**
     * Test of addTeam method, of class TeamDAOImpl. Addition of a team with an
     * incorrect club (non existing club)
     */
    @Test(expected = IncorrectTeamException.class)
    public void testAddTeamNonExistingClub() throws Exception {

        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(em).find(Club.class, club.getName());
                will(returnValue(null));
                never(em).persist(team);
                oneOf(transaction).isActive();
                will(returnValue(true));
                oneOf(transaction).rollback();

            }
        });


        tdao.addTeam(team);
    }

    /**
     * Test of addTeam method, of class TeamDAOImpl. Addition of a team with an
     * incorrect club (team has no club assigned)
     */
    @Test(expected = IncorrectTeamException.class)
    public void testAddTeamWithNotAssignedClub() throws Exception {

        team.setClub(null);

        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(transaction).isActive();
                will(returnValue(true));
                oneOf(transaction).rollback();

            }
        });


        tdao.addTeam(team);

    }

    /**
     * Test of addTeam method, of class TeamDAOImpl. Addition of a team with an
     * incorrect club (team has a club assigned, but this club has no name)
     */
    @Test(expected = IncorrectTeamException.class)
    public void testAddTeamWithNoNamedClub() throws Exception {

        team.getClub().setName(null);

        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(transaction).isActive();
                will(returnValue(true));
                oneOf(transaction).rollback();

            }
        });


        tdao.addTeam(team);

    }

    /**
     * Test of addTeam method, of class TeamDAOImpl. Addition of an already
     * existing team
     */
    @Test(expected = PersistException.class)
    public void testAddAlreadyExistingTeam() throws Exception {


        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(em).find(Club.class, club.getName());
                will(returnValue(club));
                oneOf(em).persist(team);
                will(throwException(new EntityExistsException()));
                oneOf(transaction).isActive();
                will(returnValue(true));
                oneOf(transaction).rollback();

            }
        });


        tdao.addTeam(team);

    }

    /**
     * Test of getTeams method, of class TeamDAOImpl. Get list of teams
     */
    @Test
    public void testGetTeams() throws Exception {

        final List<Team> expected = new ArrayList<Team>();
        expected.add(team);


        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(transaction).commit();
                oneOf(em).createQuery("SELECT t FROM Team t");
                will(returnValue(query));

                oneOf(query).getResultList();
                will(returnValue(expected));

            }
        });

        List<Team> result = tdao.getTeams();

        assertEquals(expected, result);

    }

    @Test
    public void testGetTeamByName() throws Exception {


        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(transaction).commit();
                oneOf(em).find(Team.class, team.getName());
                will(returnValue(team));
            }
        });

        tdao.findTeamByName("petits Lleida Fc");
    }

    @Test
    public void testGetTeamsByClub() throws Exception {

        final List<Team> teams = new ArrayList<Team>();
        teams.add(team);
        club.setTeams(teams);

        context.checking(new Expectations() {
            {
                atLeast(1).of(em).getTransaction();
                will(returnValue(transaction));
                oneOf(transaction).begin();
                oneOf(transaction).commit();
                oneOf(em).find(Club.class, club.getName());
                will(returnValue(club));
            }
        });

        tdao.findTeamsByClub("Lleida FC");
    }

    @Test
    public void testAddPlayerToTeam() throws Exception {

        context.checking(new Expectations() {
            {
                oneOf(em).getTransaction().begin();
                oneOf(em).find(Team.class, team.getName());
                will(returnValue(team));
                oneOf(em).getTransaction().commit();
                oneOf(pdao).findPlayerByNif(player.getNif());
                will(returnValue(player));
            }
        });

        tdao.addPlayerToTeam(team.getName(), player.getNif());
    }

    @Test
    public void testGetPlayersOfTeam() throws Exception {

        final List<Player> players = new ArrayList<Player>();
        players.add(player);

        context.checking(new Expectations() {
            {
                oneOf(pdao).findPlayersByTeam(team.getName());
                will(returnValue(players));
            }
        });

        tdao.getPlayersOfTeam(team.getName());
    }

    @Test
    public void testGetNumberOfPlayers() throws Exception {

        final List<Player> players = new ArrayList<Player>();
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

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);
        players.add(player7);
        players.add(player8);
        players.add(player9);
        players.add(player10);
        players.add(player11);

        context.checking(new Expectations() {
            {
                oneOf(pdao).getAllPlayers();
                will(returnValue(players));
            }
        });

        tdao.getNumberOfPlayers();
    }
}
