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
@Entity
@Table (name="INFO_MATCH")
public class InfoMatch {
    
    @Id
    private String id;
    
    @OneToOne (mappedBy="info")
    private Match match;
    
    private String place;
    
    @Temporal(TemporalType.DATE)
    private Date matchDate;
    
    private int goalsHome;
    private int goalsVisiting;
    private String observations;

    public InfoMatch() {
    }
    
    public InfoMatch(Match match){
        this.id = match.getIdMatch();
    }

    public InfoMatch(String place, Date matchDate, int goalsHome, int goalsVisiting, String observations) {
        this.place = place;
        this.matchDate = matchDate;
        this.goalsHome = goalsHome;
        this.goalsVisiting = goalsVisiting;
        this.observations = observations;
        this.id = match.getIdMatch();
    }

    public int getGoalsHome() {
        return goalsHome;
    }

    public void setGoalsHome(int goalsHome) {
        this.goalsHome = goalsHome;
    }

    public int getGoalsVisiting() {
        return goalsVisiting;
    }

    public void setGoalsVisiting(int goalsVisiting) {
        this.goalsVisiting = goalsVisiting;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

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
    
}
