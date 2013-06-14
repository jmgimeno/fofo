/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.*;
import org.fofo.entity.Player;
import org.fofo.entity.Team;

/**
 *
 * @author mohamed
 */
public class PlayerDAOImpl implements PlayerDAO {

    private EntityManager em;

    /**
     *
     */
    public PlayerDAOImpl() {
    }

    /**
     *
     * @param em
     */
    public PlayerDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @return
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     *
     * @param em
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addPlayer(Player pl) throws Exception {
        try {

            em.getTransaction().begin();
            checkExistingPlayer(pl);
            em.persist(pl);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw e;
        }
    }

    @Override
    public Player findPlayerByNif(String nif) throws IllegalArgumentException {

        Player player = null;
        try {

            em.getTransaction().begin();
            player = (Player) em.find(Player.class, nif);
            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            throw e;
        }

        return player;
    }

    @Override
    public List<Player> findPlayerByTeam(String teamName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Player> gelAllPlayers() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void checkExistingPlayer(Player pl) throws AlreadyExistingPlayerException {
        if (em.find(Player.class, pl.getNif()) != null) {
            throw new AlreadyExistingPlayerException("This player <"
                    + pl.getNif() + "> already exist in DB");
        }
    }
}
