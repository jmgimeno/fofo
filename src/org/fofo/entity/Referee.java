/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import javax.persistence.*;

/**
 *
 * @author mohamed, Anatoli
 */
@Entity
@Table (name="Referee")
public class Referee {
    
    @Id
    @Column (name="NIF")
    private String nif;
    
    @Column (name="NAME")
    private String name;
    
    public Referee(){
    }
    
    public Referee(String nif, String name){
        this.nif = nif;
        this.name = name;
    }
    
    public String getNif(){
        return nif;
    }
    
    public void setNif(String nif){
        this.nif = nif;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
   
    
    public String toString(){
        return "<Name: "+this.name+ ", Nif: " +this.nif+">";
    }
}
