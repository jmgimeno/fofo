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
    @Column (name="ID_MATCH")
    private String idMatch;

    
    @OneToOne
    @JoinColumn (name="LOCAL_T_NAME", referencedColumnName="NAME")
    private Team local;
    
    @OneToOne
    @JoinColumn (name="VISITANT_T_NAME", referencedColumnName="NAME")
    private Team visitant;
    
    @Temporal(TemporalType.DATE)
    @Column (name="MATCH_DATE")
    private Date matchDate;
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

    public Date getMatchDate() {
        return this.matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
    
    public boolean equals(Object obj){
        
        if (!(obj instanceof Match) ) return false;
        
        Match m = (Match) obj;
        
        return m.local.getName().equals(local.getName()) && 
               m.visitant.getName().equals (visitant.getName());
        
        
    }

    
    public String toString(){
        
        return local.getName()+"-"+visitant.getName();
    }
}
