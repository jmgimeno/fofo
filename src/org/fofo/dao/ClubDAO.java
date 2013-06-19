/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import java.util.List;
import javax.persistence.PersistenceException;
import org.fofo.dao.exception.AlreadyExistingClubOrTeamsException;
import org.fofo.entity.Club;

/**
 *
 * @author RogerTorra
 */
public interface ClubDAO {
    
    /**
     *
     * @param club
     * @throws AlreadyExistingClubOrTeamsException
     */
    void addClub(Club club) throws AlreadyExistingClubOrTeamsException;
    
    /**
     *
     * @param name
     */
    void removeClub(String name);
    
    /**
     *
     * @return
     */
    List<Club> getClubs();
    
    /**
     *
     * @param name
     * @return
     */
    Club findClubByName(String name);
    
    /**
     *
     * @param name
     * @return
     */
    public Club findClubByTeam(String name); 
    
}
