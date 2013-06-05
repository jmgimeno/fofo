package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class MatchDAOImpl implements MatchDAO {

    private EntityManager em;
    private RefereeDAO refereedb;

    public MatchDAOImpl() {
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEm() {
        return this.em;
    }

    @Override
    public void setRefereedb(RefereeDAO refereedb) {
        this.refereedb = refereedb;
    }

    @Override
    public RefereeDAO getRefereedb() {
        return refereedb;
    }

    /**
     * Assign a referee to a match.
     * @param idMatch: The id of the match which want to assign a referee.
     * @param refereeNIF: The nif of the referee which want to assign a match.
     * @throws PersistException 
     */
    @Override
    public void addRefereeToMatch(String idMatch, String refereeNIF) throws PersistException {
        try {
            Match match = findMatchById(idMatch);
            Referee referee = refereedb.findRefereeByNif(refereeNIF);
            if (match == null || referee == null) {
                throw new PersistException();
            }
            match.setReferee(referee);
            referee.getMatches().add(match);
        } catch (Exception ex) {
            throw new PersistException();
        }
    }

    /**
     * Find a match by id.
     * @param id: The id to find.
     * @return The Match wich have the id.
     * @throws PersistException 
     */
    @Override
    public Match findMatchById(String id) throws PersistException {
        Match match = null;
        try {

            em.getTransaction().begin();
            match = (Match) em.find(Match.class, id);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new PersistException();
        }
        if (match == null) {
            throw new PersistException();
        }
        return match;
    }

    //PRIVATE FUNCTIONS
    private void addMatch(Match match) {
        em.getTransaction().begin();
        em.persist(match);
        em.getTransaction().commit();
    }
}
