package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public interface MatchDAO {

    EntityManager getEm();

    void setEm(EntityManager em);
    
    public void setRefereedb(RefereeDAO refereedb);
    
    public RefereeDAO getRefereedb();
    
    public void addRefereeToMatch(String idMatch, String idReferee) throws PersistException;

    public Match findMatchById(String id) throws PersistException, IncorrectMatchException;
    
    public void insertMatch(Match match) throws PersistException;
}
