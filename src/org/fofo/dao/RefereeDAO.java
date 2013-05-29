/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Referee;

/**
 *
 * @author mohamed, Anatoli
 */
public interface RefereeDAO {
    
    void addReferee(Referee ref) throws Exception;
    
    Referee findRefereeByNif(String nif) throws Exception;
    
    List<Referee> getAllReferees() throws Exception;
}
