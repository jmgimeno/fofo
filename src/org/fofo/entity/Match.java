/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.UUID;

/**
 *
 * @author mohamed
 */
public class Match {
    
    private String id;
    private Team local;
    private Team visitant;
    
    public Match(Team local, Team visitant){
        this.local = local;
        this.visitant = visitant;
        id = UUID.randomUUID().toString();
        /*this.id = id; ?*/
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
}
