/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.utils;

import java.util.Date;
import javax.persistence.*;
import org.fofo.entity.Match;

/**
 *
 * @author imt1
 */

public class InfoMatch {
    
    
    private String id;
    private String idCompetition;
   
    private Match match;
    
    private String place;
    
    @Temporal(TemporalType.DATE)
    private Date matchDate;
    
    private int goalsHome;
    private int goalsVisiting;
    private String observations;
//Afegir identificador de la competicio
    /**
     *
     */
    public InfoMatch() {
    }
    
    /**
     *
     * @param match
     */
    public InfoMatch(Match match){
        this.id = match.getIdMatch();
    }

    /**
     *
     * @param place
     * @param matchDate
     * @param goalsHome
     * @param goalsVisiting
     * @param observations
     */
    public InfoMatch(String place, Date matchDate, int goalsHome, int goalsVisiting, String observations,
            String idComp) {
        this.place = place;
        this.matchDate = matchDate;
        this.goalsHome = goalsHome;
        this.goalsVisiting = goalsVisiting;
        this.observations = observations;
        this.id = match.getIdMatch();
        this.idCompetition = idComp;
    }

    /**
     *
     * @return
     */
    public int getGoalsHome() {
        return goalsHome;
    }

    /**
     *
     * @param goalsHome
     */
    public void setGoalsHome(int goalsHome) {
        this.goalsHome = goalsHome;
    }

    /**
     *
     * @return
     */
    public int getGoalsVisiting() {
        return goalsVisiting;
    }

    /**
     *
     * @param goalsVisiting
     */
    public void setGoalsVisiting(int goalsVisiting) {
        this.goalsVisiting = goalsVisiting;
    }

    /**
     *
     * @return
     */
    public Date getMatchDate() {
        return matchDate;
    }

    /**
     *
     * @param matchDate
     */
    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    /**
     *
     * @return
     */
    public String getObservations() {
        return observations;
    }

    /**
     *
     * @param observations
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     *
     * @return
     */
    public String getPlace() {
        return place;
    }

    /**
     *
     * @param place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Match getMatch() {
        return match;
    }

    /**
     *
     * @param match
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "InfoMatch{" + "place=" + place + ", matchDate=" + matchDate + 
                ", goalsHome=" + goalsHome + ", goalsVisiting=" + goalsVisiting + 
                ", observations=" + observations + '}';
    }
    
    @Override
    public boolean equals(Object obj){
        return (obj instanceof InfoMatch)&&((InfoMatch)obj).id.equals(this.id);
    }

    public String getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(String idCompetition) {
        this.idCompetition = idCompetition;
    }
    
}
