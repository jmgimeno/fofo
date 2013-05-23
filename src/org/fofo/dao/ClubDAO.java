/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Club;

/**
 *
 * @author RogerTorra
 */
public interface ClubDAO {
    
    void addClub(Club club) throws PersistException;
    
    void removeClub(String name) throws PersistException;
    
    List<Club> getClubs();
    
    Club findClubByName(String name);
    
    Club findClubByTeam(String name); //un equip només està asociat a un club, no?
    
}
