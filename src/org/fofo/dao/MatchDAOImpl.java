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

    /**
     *
     */
    public MatchDAOImpl() {
    }

    /**
     *
     * @param em
     */
    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @return
     */
    @Override
    public EntityManager getEm() {
        return this.em;
    }

    /**
     *
     * @param refereedb
     */
    @Override
    public void setRefereedb(RefereeDAO refereedb) {
        this.refereedb = refereedb;
    }

    /**
     *
     * @return
     */
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
    public Match findMatchById(String id) throws PersistException, IncorrectMatchException {

        Match match = null;
        //System.out.println("--------------------->ID: "+id);
        try {
            em.getTransaction().begin();
            //System.out.println("OOOOOOOOOO"+match.getIdMatch());
            match = (Match) em.find(Match.class, id);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new PersistException();
        }
        if (match == null) {
            //    System.out.println("GGGGGGGGGGGGGG");
            throw new IncorrectMatchException();
        }
        return match;
    }

    /**
     * Insert a match in BBDD.
     * @param match: The match to insert
     * @throws IncorrectMatchException 
     */
    @Override
    public void insertMatch(Match match) throws IncorrectMatchException {
        try {
            em.getTransaction().begin();
            checkMatch(match);
            em.persist(match);
            em.getTransaction().commit();

        } catch (Exception e) {
            throw new IncorrectMatchException();
        }
    }

    private void checkMatch(Match match) throws IncorrectMatchTeamsException {

        if (match.getHome() == null || match.getVisitor() == null) {
            throw new IncorrectMatchTeamsException();
        }
    }
}
