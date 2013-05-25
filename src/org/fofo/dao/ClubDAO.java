/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.PersistenceException;
import org.fofo.entity.Club;

/**
 *
 * @author RogerTorra
 */
public interface ClubDAO {
    
    public void addClub(Club club) throws PersistenceException;
    
    void removeClub(String name) throws PersistException;
    
    public List<Club> getClubs() throws Exception;
    
    public Club findClubByName(String name) throws PersistenceException;
    
    public Club findClubByTeam(String name) throws PersistenceException; 
    
}
