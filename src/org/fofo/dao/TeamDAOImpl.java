/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectTeamException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.fofo.entity.Club;
import org.fofo.entity.Player;
import org.fofo.entity.Team;

/**
 *
 * @author josepma
 */
public class TeamDAOImpl implements TeamDAO {

    private EntityManager em;
    private PlayerDAO playerDB;

    /**
     *
     */
    public TeamDAOImpl() {
    }

    /**
     *
     * @param em
     */
    public void setEM(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @return
     */
    public EntityManager getEM() {
        return this.em;
    }

    /**
     *
     * @return
     */
    public PlayerDAO getPlayerDB() {
        return playerDB;
    }

    /**
     *
     * @param playerDB
     */
    public void setPlayerDB(PlayerDAO playerDB) {
        this.playerDB = playerDB;
    }
    

    /**
     *
     * @param team
     * @throws PersistException
     * @throws IncorrectTeamException
     */
    @Override
    public void addTeam(Team team) throws PersistException,
            IncorrectTeamException {


        try {
            em.getTransaction().begin();

            if (team.getClub() == null || team.getClub().getName() == null) {
                throw new IncorrectTeamException();
            }

            Club club = (Club) em.find(Club.class, team.getClub().getName());
            if (club == null) {
                throw new IncorrectTeamException();
            }

            em.persist(team);

            club.getTeams().add(team);
            System.out.println("****ADDTEAM");
            em.getTransaction().commit();

        } catch (EntityExistsException e) {
            throw new PersistException();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    /**
     *
     * @param name
     */
    public void removeTeam(String name) {
    }

    /**
     *
     * @return
     */
    public List<Team> getTeams() {

        List<Team> teams = new ArrayList<Team>();
        em.getTransaction().begin();
        Query pp = em.createQuery("SELECT t FROM Team t");
        teams = pp.getResultList();
        em.getTransaction().commit();
        return teams;

    }

    /**
     *
     * @param name
     * @return
     * @throws PersistException
     */
    @Override
    public Team findTeamByName(String name) throws PersistException {
        Team team = null;
        try {
            em.getTransaction().begin();

            team = (Team) em.find(Team.class, name);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new PersistException();
        }
        return team;
    }

    /**
     *
     * @param name
     * @return
     */
    public List<Team> findTeamByClub(String name) {

        return null;
    }

//CAL TREURE-LA!!!!!
    /**
     *
     * @param team
     * @return
     */
    @Override
    public boolean findTeam(Team team) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addPlayerToTeam(String teamName, String nif) throws PersistException {

        try {
            Team team = findTeamByName(teamName);
            Player player = playerDB.findPlayerByNif(nif);

            if (team == null || player == null) {
                throw new PersistException();
            }
            player.setTeam(team);
            team.getPlayers().add(player);

        } catch (Exception e) {
            throw new PersistException();
        }
    }

    @Override
    public List<Player> getPlayersOfTeam(String teamName) throws PersistException {
        
        List<Player> players = new ArrayList<Player>();
        
        try {

            players = playerDB.findPlayersByTeam(teamName);

        } catch (Exception e) {
            throw new PersistException();
       }
       return players;
    }
}
