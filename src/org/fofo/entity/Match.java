/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author mohamed, Anatoli
 *
 */
public class Match {

    private String id;
    private Team local;
    private Team visitant;
    private Date date;
    //private Arbitre 
    //private Stadium

    public Match() {
    }

    public Match(Team local, Team visitant) {
        this.local = local;
        this.visitant = visitant;
        this.id = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre
    }

    public String getId() {
        return id;
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
