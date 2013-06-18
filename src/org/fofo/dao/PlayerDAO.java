/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Player;

/**
 *
 * @author mohamed
 */
public interface PlayerDAO {

    /**
     *
     * @param pl
     * @throws Exception
     */
    void addPlayer(Player pl) throws Exception;

    /**
     *
     * @param nif
     * @return
     * @throws Exception
     */
    Player findPlayerByNif(String nif) throws Exception;

    /**
     *
     * @param teamName
     * @return
     * @throws Exception
     */
    Player findPlayerByTeam(String teamName) throws Exception;

    /**
     *
     * @return @throws Exception
     */
    List<Player> getAllPlayers() throws Exception;
}
