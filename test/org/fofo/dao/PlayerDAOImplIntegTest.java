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
import org.fofo.dao.exception.AlreadyExistingPlayerException;
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
    private Player player;
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

        player = new Player("nifPlayer", "namePlayer");
        team = new Team("EF Cervera");
    }

    @Test
    public void testAddPlayer() throws Exception {

        pDao.addPlayer(player);
        Player playerDB = getPlayerFromDB(player.getNif());
        assertEquals("Should have found the inserted referee",
                player, playerDB);
    }

    @Test
    public void testFindPlayerByNif() throws Exception {

        pDao.addPlayer(player);
        assertEquals(player, pDao.findPlayerByNif(player.getNif()));
    }

    //@Test
    public void testFindPlayersByTeam() throws Exception {
        
        pDao.addPlayer(player);
        List<Player> players = new ArrayList<Player>();
        players.add(player);
        tDao.addPlayerToTeam(team.getName(), player.getNif());
        assertEquals(players, pDao.findPlayersByTeam(team.getName()));

    }

    @Test
    public void testGetAllPlayers() throws Exception {

        pDao.addPlayer(player);
        List<Player> players = new ArrayList<Player>();
        players.add(player);
        assertEquals(players, pDao.getAllPlayers());
    }

    @Test(expected = AlreadyExistingPlayerException.class)
    public void alreadyExistPlayerInDB() throws Exception {
        pDao.addPlayer(player);
        pDao.addPlayer(player);
    }

    @After
    public void tearDown() {

        em = pDao.getEm();
        if (em.isOpen()) {
            em.close();
        }

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