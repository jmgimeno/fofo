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
    
    public Match(Team local, Team visitant, Date date){
        this.local = local;
        this.visitant = visitant;
        this.date = date;  //Assignat per FCalendar
        this.id = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre
    }
    
    public String getId(){
        return id;
    }
    
    public Team getLocal(){
        return local;
    }
    
    public Team getVisitant(){
        return visitant;
    }
    
    public Date getDate(){
        return this.date;
    }
}
