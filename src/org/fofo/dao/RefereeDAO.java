/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.fofo.entity.Referee;

/**
 *
 * @author mohamed, Anatoli
 */
public interface RefereeDAO {
    
    /**
     *
     * @param ref
     * @throws Exception
     */
    void addReferee(Referee ref) throws Exception;
    
    /**
     *
     * @param nif
     * @return
     * @throws Exception
     */
    Referee findRefereeByNif(String nif) throws Exception;
    
    /**
     *
     * @param matchId
     * @return
     * @throws Exception
     */
    Referee findRefereeByMatch(String matchId) throws Exception;
    
    /**
     *
     * @return
     * @throws Exception
     */
    List<Referee> getAllReferees() throws Exception;
}
