package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public interface MatchDAO {

    void addMatch(Match match);

    public void addRefereeToMatch(String idMatch, String idReferee) throws PersistException;

    EntityManager getEm();

    void setEm(EntityManager em);
}
