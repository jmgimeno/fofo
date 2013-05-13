/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

/**
 *
 * @author mohamed, Anatoli
 *
 */

@Entity
@Table (name="Match")
public class Match {

    @Id
    @Column (name="idFCalendar")
    private String idMatch;

    
    @ManyToOne
    @JoinColumn (name="LocalTeamName", referencedColumnName="NAME")
    @Column (name="LocalTeam")
    private Team local;
    
    @ManyToOne
    @JoinColumn (name="VisitTeamName", referencedColumnName="NAME")
    @Column (name="VisitantTeam")
    private Team visitant;
    
    @Column (name="Date")
    private Date date;
    //private Arbitre 
    //private Stadium

    public Match() {
    }

    public Match(Team local, Team visitant) {
        this.local = local;
        this.visitant = visitant;
        this.idMatch = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre
    }

    public String getIdMatch() {
        return idMatch;
    }

    public Team getLocal() {
        return local;
    }

    public void setLocal(Team local) {
        this.local = local;
    }

    public Team getVisitant() {
        return visitant;
    }

    public void setVisitant(Team visitant) {
        this.visitant = visitant;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
