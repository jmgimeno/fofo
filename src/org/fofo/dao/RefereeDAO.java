/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.dao.exception.AlreadyExistingRefereeException;
import org.fofo.dao.exception.NotAssignedMatchToRefereeException;
import org.fofo.entity.Referee;

/**
 *
 * @author mohamed, Anatoli
 */
public interface RefereeDAO {
    
    /**
     *
     * @param ref
     * @throws AlreadyExistingRefereeException
     */
    void addReferee(Referee ref) throws AlreadyExistingRefereeException;
    
    /**
     *
     * @param nif
     * @return
     */
    Referee findRefereeByNif(String nif);
    
    /**
     *
     * @param matchId
     * @return
     * @throws NotAssignedMatchToRefereeException
     */
    Referee findRefereeByMatch(String matchId) throws NotAssignedMatchToRefereeException;
    
    /**
     *
     * @return
     */
    List<Referee> getAllReferees();
}
