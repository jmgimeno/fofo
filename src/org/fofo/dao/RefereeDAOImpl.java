/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;

/**
 *
 * @author mohamed, Anatoli
 */
public class RefereeDAOImpl implements RefereeDAO {

    private EntityManager em;

    public RefereeDAOImpl() {
    }

    public void setEM(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEM() {
        return this.em;
    }

    @Override
    public void addReferee(Referee ref) throws AlreadyExistingRefereeException {

        try {
            em.getTransaction().begin();
            em.persist(ref);
            em.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new AlreadyExistingRefereeException("This referee <"
                    + ref.getNif() + "> already exist in DB");
        }
    }

    @Override
    public Referee findRefereeByNif(String nif) throws IllegalArgumentException {
        Referee referee = null;
        try {

            em.getTransaction().begin();
            referee = (Referee) em.find(Referee.class, nif);
            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            throw e;
        }

        return referee;
    }
    
    @Override
    public Referee findRefereeByMatch(String matchId) throws NotAssignedMatchToRefereeException,IllegalArgumentException{
        Match m = null;
        try{
            em.getTransaction().begin();
            m = (Match) em.find(Match.class, matchId);
            em.getTransaction().commit();
        }catch(IllegalArgumentException e){
            throw e;
        }        
        Referee r = m.getReferee();
        
        if(!r.getMatches().contains(m)){
            throw new NotAssignedMatchToRefereeException("The referee "
                    +r.toString()+" don't have assigned the match "+m.toString());
        }
        
        return findRefereeByNif(r.getNif());
    }
    
    @Override
    public List<Referee> getAllReferees() throws Exception {
        List<Referee> referees = null;
        Query query;
        try {
            em.getTransaction().begin();
            query = em.createQuery("SELECT x FROM Referee x");
            referees = (List<Referee>) query.getResultList();
            em.getTransaction().commit();  
        } catch (Exception e) {
            throw e;
        }

        return referees;
    }

}