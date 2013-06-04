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
         
            match =  findMatchById(idMatch);
            referee = refereedb.findRefereeByNif(RefereeNIF);
          
            if (match == null || referee == null) throw new PersistException();
            
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
        return match;
    }

}
