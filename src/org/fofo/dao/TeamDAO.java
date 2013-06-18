/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectTeamException;
import java.util.List;
import org.fofo.entity.Player;
import org.fofo.entity.Team;


/**
 *
 * @author josepma
 * @author RogerTorra
 * 
 * 
 */


public interface TeamDAO {
    
    /**
     *
     * @param team
     * @throws PersistException
     * @throws IncorrectTeamException
     */
    void addTeam(Team team) throws PersistException, 
                                    IncorrectTeamException;
    
    /**
     *
     * @param name
     */
    void removeTeam(String name);
    
    /**
     *
     * @param team
     * @return
     */
    boolean findTeam(Team team);
   
    /**
     *
     * @return
     */
    List<Team> getTeams();
    
    
    /**
     *
     * @param name
     * @return
     * @throws PersistException
     */
    Team findTeamByName(String name) throws PersistException;
    
    /**
     *
     * @param name
     * @return
     */
    List<Team> findTeamByClub(String name);
    
    /**
     *
     * @param teamId
     * @param player
     */
    void addPlayerToTeam(String teamId, String nif) throws PersistException;
    
    /**
     *
     * @param teamId
     * @return
     */
    List<Player> getPlayersOfTeam(String teamId);
    
}
