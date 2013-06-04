package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class MatchDAOImpl implements MatchDAO {

    private EntityManager em;
    private CalendarDAO calendardb;
    private Match match;
    private RefereeDAO refereedb;
    private Referee referee;

    public MatchDAOImpl() {
    }

    @Override
    public EntityManager getEm() {
        return this.em;
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addRefereeToMatch(String idMatch, String RefereeNIF) throws PersistException {
        try {
            if (checkForExceptions(idMatch, RefereeNIF)) {
                match = calendardb.findMatchById(idMatch);
                referee = refereedb.findRefereeByNif(RefereeNIF);
                match.setNif(RefereeNIF);
                referee.getMatches().add(match);
                addMatch(match);
                refereedb.addReferee(referee);

            } else {
                throw new PersistException();
            }
        } catch (Exception ex) {
            throw new PersistException();
        }
    }

    private boolean checkForExceptions(String idMatch, String RefereeNIF) throws Exception {
        return (calendardb.findMatchById(idMatch) != null
                && refereedb.findRefereeByNif(RefereeNIF) != null);
    }

    private void addMatch(Match match) {
        em.getTransaction().begin();
        em.persist(match);
        em.getTransaction().commit();
    }

    /*
    @Override
    public void addRefereeToMatch(String idMatch, String idReferee) throws PersistException {

        try {
            em.getTransaction().begin();

            matchdb = (Match) em.find(Match.class, matchdb.getIdMatch());
            refereedb = (Referee) em.find(Referee.class, refereedb.getNif());

            if (checkForExceptions(matchdb, refereedb)) {

                matchdb.setReferee(refereedb);

                em.getTransaction().commit();

            } else {
                throw new PersistException();
            }


        } catch (PersistenceException e) {
            throw new PersistException();
        }
        // Posar anotacions orm a les entitats math i referee. MATCH (n)---->(1)REFEREE
    }

    private boolean checkForExceptions(Match matchdb, Referee refereedb) {

        return matchdb != null && refereedb != null;
    }
}*/

    @Override
    public Match findMatchById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}