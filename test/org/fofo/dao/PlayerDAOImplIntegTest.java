/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.Player;
import org.fofo.entity.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mohamed
 */
public class PlayerDAOImplIntegTest {

    private EntityManager em;
    private PlayerDAOImpl pDao;
    private TeamDAOImpl tDao;
    
    private Player player1, player2, player3, player4;
    private Team team;

    public PlayerDAOImplIntegTest() {
    }

    @Before
    public void setUp() {

        em = getEntityManager();
        pDao = new PlayerDAOImpl();
        pDao.setEm(em);
        
        tDao = new TeamDAOImpl();
        tDao.setEM(em);

        player1 = new Player("nifPlayer1", "namePlayer1");
        player2 = new Player("nifPlayer2", "namePlayer2");
        player3 = new Player("nifPlayer3", "namePlayer3");
        player4 = new Player("nifPlayer4", "namePlayer4");
    }

    @Test
    public void testAddPlayer() throws Exception {

        pDao.addPlayer(player1);
        Player playerDB = getPlayerFromDB(player1.getNif());
        assertEquals("Should have found the inserted referee",
                player1, playerDB);
    }

    @Test
    public void testFindPlayerByNif() throws Exception {
        
        pDao.addPlayer(player2);
        assertEquals(player2, pDao.findPlayerByNif(player2.getNif()));
    }

    //@Test
    public void testFindPlayerByTeam() throws Exception {
        
        pDao.addPlayer(player3);
        tDao.addPlayerToTeam(team.getName(), player3.getNif());
        assertEquals(player3, pDao.findPlayerByTeam(team.getName()));
        
    }

    @Test
    public void testGetAllPlayers() throws Exception {
        
        pDao.addPlayer(player1);
        pDao.addPlayer(player2);
        pDao.addPlayer(player3);
        pDao.addPlayer(player4);
        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        assertEquals(players, pDao.getAllPlayers());
    }

    @After
    public void tearDown() {
        
        em = pDao.getEm();
        if(em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query = em.createQuery("DELETE FROM Player");
        Query query2 = em.createQuery("DELETE FROM Team");
        
        int deleteRecords = query.executeUpdate();
        deleteRecords = query2.executeUpdate();
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();
    }

    private Player getPlayerFromDB(String nif) {

        em = getEntityManager();
        em.getTransaction().begin();
        Player pDB = (Player) em.find(Player.class, nif);
        em.getTransaction().commit();
        em.close();

        return pDB;
    }
}