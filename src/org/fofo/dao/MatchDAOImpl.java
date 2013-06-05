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

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

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

            //addMatch(match);
            //refereedb.addReferee(referee);
        } catch (Exception ex) {
            throw new PersistException();
        }
    }

    private void addMatch(Match match) {
        em.getTransaction().begin();
        em.persist(match);
        em.getTransaction().commit();
    }

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
}
