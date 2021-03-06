/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.AlreadyExistingPlayerException;
import java.util.List;
import javax.persistence.*;
import org.fofo.dao.exception.InvalidTeamException;
import org.fofo.dao.exception.NotAssignedPlayersToTeamException;
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
    public void addPlayer(Player pl) throws PersistenceException, AlreadyExistingPlayerException {
        
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
    public List<Player> findPlayersByTeam(String teamName) throws InvalidTeamException, NotAssignedPlayersToTeamException {
        
        Team team = null;
        List<Player> players = null;
        
        em.getTransaction().begin();
        team = (Team) em.find(Team.class, teamName);
        if(team == null){
            throw new InvalidTeamException();
        }
        em.getTransaction().commit();
        
        players = team.getPlayers();
        if(players == null){
            throw new NotAssignedPlayersToTeamException();
        }
        
        return players;
    }

    @Override
    public List<Player> getAllPlayers() throws Exception {
        List<Player> players = null;
        Query query;
        try {

            em.getTransaction().begin();
            query = em.createQuery("SELECT p FROM Player p");
            players = (List<Player>) query.getResultList();
            em.getTransaction().commit();

        } catch (Exception e) {
            throw e;
        }

        return players;
    }

    private void checkExistingPlayer(Player pl) throws AlreadyExistingPlayerException {
        if (em.find(Player.class, pl.getNif()) != null) {
            throw new AlreadyExistingPlayerException("This player <"
                    + pl.getNif() + "> already exist in DB");
        }
    }
}
