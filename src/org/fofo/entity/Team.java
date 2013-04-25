/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author Josep M. Ribo
 */

@Entity
public class Team implements Serializable {

    private String name;


    public Team() {

	name = "";

    }


    public Team(String name){
	this.name = name;
    }
    
    

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
    
    @Override
    public boolean equals(Object obj){
        
        return (obj instanceof Team) && ((Team)obj).name.equals(this.name);
        
    } 

}



