package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectMatchException;
import javax.persistence.EntityManager;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public interface MatchDAO {

    /**
     *
     * @return
     */
    EntityManager getEm();

    /**
     *
     * @param em
     */
    void setEm(EntityManager em);
    
    /**
     *
     * @param refereedb
     */
    public void setRefereedb(RefereeDAO refereedb);
    
    /**
     *
     * @return
     */
    public RefereeDAO getRefereedb();
    
    /**
     *
     * @param idMatch
     * @param idReferee
     * @throws PersistException
     */
    public void addRefereeToMatch(String idMatch, String idReferee) throws PersistException;

    /**
     *
     * @param id
     * @return
     * @throws PersistException
     * @throws IncorrectMatchException
     */
    public Match findMatchById(String id) throws PersistException, IncorrectMatchException;
    
    /**
     *
     * @param match
     * @throws IncorrectMatchException
     */
    public void insertMatch(Match match) throws IncorrectMatchException;
}
