package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.Competition;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
import javax.persistence.PersistenceException;
import org.fofo.entity.FCalendar;
import org.fofo.services.management.InscriptionTeamException;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class MatchDAOImpl implements MatchDAO {

    private EntityManager em;
    private Match matchdb;
    private Referee refereedb;

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
    public void addMatch(Match match) {
    }

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
}
