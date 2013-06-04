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
    private Match matchdb;

    public MatchDAOImpl() {
    }

    @Override
    public EntityManager getEm() {
        return this.em;
    }

    public void setCalendardb(CalendarDAO calendardb) {
        this.calendardb = calendardb;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setMatchdb(Match matchdb) {
        this.matchdb = matchdb;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public void setRefereedb(RefereeDAO refereedb) {
        this.refereedb = refereedb;
    }

    public CalendarDAO getCalendardb() {
        return calendardb;
    }

    public Match getMatch() {
        return match;
    }

    public Match getMatchdb() {
        return matchdb;
    }

    public Referee getReferee() {
        return referee;
    }

    public RefereeDAO getRefereedb() {
        return refereedb;
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

    
//        @Override
//    public void addRefereeToMatch(String idMatch, String idReferee) throws PersistException {
//
//        try {
//            em.getTransaction().begin();
//
//            matchdb = (Match) em.find(Match.class, idMatch); //Busco Match a la bd, a partir del idMatch
//            refereedb = (Referee) em.find(Referee.class, idReferee); //Busco referee a la bd a traves de la idReferee
//
//            if (checkForExceptions(matchdb, refereedb)) { //Comprovo si realment s'ha trobat el partit i l'arbitre a la BD
//
//                matchdb.setReferee(refereedb); //Si s'ha trobat li assigno un aribre.
//
//                em.getTransaction().commit(); //Actualitzo els canvis
//                
//            } else {
//                throw new PersistException();
//            }
//
//
//        } catch (PersistenceException e) {
//            throw new PersistException();
//        }
//    }
//
//    private boolean checkForExceptions(Match matchdb, Referee refereedb) {
//
//        return matchdb.getIdMatch() != null && refereedb.getNif() != null;
//    }
    
    private void addMatch(Match match) {
        em.getTransaction().begin();
        em.persist(match);
        em.getTransaction().commit();
    }


    @Override
    public Match findMatchById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setRefereedb(Referee referee1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}