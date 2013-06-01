package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.Match;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class MatchDAOImpl implements MatchDAO {

    private EntityManager em;

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
    public void addRefereeToMatch(String idMatch, String idReferee) {
        em.getTransaction().begin();
        //<--Implementacio
        em.getTransaction().commit();
    }
}
