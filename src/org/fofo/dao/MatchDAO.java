package org.fofo.dao;

import java.util.List;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public interface MatchDAO {
    void addMatch(Match match);
    
    void addRefereeToMatch(String idMatch, String idReferee);
}
