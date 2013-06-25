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
import org.fofo.entity.Player;
import org.fofo.entity.Team;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 *
 * @author mohamed
 */
@RunWith(JMock.class)
public class PlayerDAOImplTest {

    private Mockery context = new JUnit4Mockery();
    private EntityManager em;
    private EntityTransaction transaction;
    private PlayerDAOImpl pdao;
    private Query query;

    public PlayerDAOImplTest() {
    }

    @Before
    public void setUp() {
        pdao = new PlayerDAOImpl();
        em = context.mock(EntityManager.class);
        pdao.setEm(em);
        transaction = context.mock(EntityTransaction.class);
        query = context.mock(Query.class);
    }

    @Test
    public void testAddPlayer() throws Exception {

        final Player player = new Player("nifPlayer", "namePlayer");
        transactionExpectations();
        context.checking(new Expectations() {
            {
                oneOf(em).find(Player.class, player.getNif());
                will(returnValue(null));
                oneOf(em).persist(player);
            }
        });

        pdao.addPlayer(player);
    }

    @Test
    public void testFindPlayerByNif() throws Exception {

        final Player player = new Player("nifPlayer", "namePlayer");
        transactionExpectations();
        context.checking(new Expectations() {
            {
                oneOf(em).find(Player.class, player.getNif());
                will(returnValue(player));
            }
        });

        assertEquals(player, pdao.findPlayerByNif(player.getNif()));
    }

    @Test
    public void testFindPlayerByTeam() throws Exception {

        final Player player = new Player("nifPlayer", "namePlayer");
        final List<Player> players = new ArrayList<Player>();
        final Team team = new Team("EF Cervera");
        player.setTeam(team);
        players.add(player);
        team.setPlayers(players);
   
        transactionExpectations();
        context.checking(new Expectations() {
            {
                oneOf(em).find(Team.class, team.getName());
                will(returnValue(team));
            }
        });

        assertEquals(players, pdao.findPlayersByTeam("EF Cervera"));
    }

    @Test
    public void testGetAllPlayers() throws Exception {

        final Player player = new Player("nifPlayer", "namePlayer");
        final List<Player> players = new ArrayList<Player>();
        players.add(player);

        transactionExpectations();
        context.checking(new Expectations() {
            {
                oneOf(em).createQuery("SELECT p FROM Player p");
                will(returnValue(query));
                oneOf(query).getResultList();
                will(returnValue(players));
            }
        });

        assertEquals(players, pdao.getAllPlayers());
    }

    private void transactionExpectations() {
        context.checking(new Expectations() {
            {
                allowing(em).getTransaction();
                will(returnValue(transaction));
                allowing(transaction).begin();
                allowing(transaction).commit();
            }
        });
    }
}
