/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.UUID;

/**
 *
 * @author mohamed
 * @author oriol
 */
public class Match {
    
    private String id;
    private Team local;
    private Team visitant;
    
    public Match(Team local, Team visitant, String id){
        this.local = local;
        this.visitant = visitant;
        this.id = id; 
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
