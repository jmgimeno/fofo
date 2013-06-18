/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import java.util.List;
import javax.persistence.PersistenceException;
import org.fofo.entity.Club;

/**
 *
 * @author RogerTorra
 */
public interface ClubDAO {
    
    /**
     *
     * @param club
     * @throws Exception
     */
    public void addClub(Club club) throws Exception;
    
    /**
     *
     * @param name
     * @throws PersistException
     */
    void removeClub(String name) throws PersistException;
    
    /**
     *
     * @return
     * @throws Exception
     */
    public List<Club> getClubs() throws Exception;
    
    /**
     *
     * @param name
     * @return
     * @throws PersistenceException
     */
    public Club findClubByName(String name) throws PersistenceException;
    
    /**
     *
     * @param name
     * @return
     * @throws PersistenceException
     */
    public Club findClubByTeam(String name) throws PersistenceException; 
    
}
