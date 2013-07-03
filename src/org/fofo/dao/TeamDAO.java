/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectTeamException;
import java.util.List;
import org.fofo.dao.exception.NotAssignedTeamsToClubException;
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
    List<Team> findTeamsByClub(String name) throws NotAssignedTeamsToClubException;
    
    /**
     *
     * @param teamName
     * @param player
     */
    void addPlayerToTeam(String teamName, String nif) throws PersistException;
    
    /**
     *
     * @param teamName
     * @return
     */
    List<Player> getPlayersOfTeam(String teamName) throws PersistException;
    
    /**
     *
     * @return
     */
    int getNumberOfPlayers() throws Exception;
    
}
