/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author Josep M. Ribo.....
 */

@Entity
public class Team implements Serializable {

    private String name;
    private Club club;
    private String email;
    private Category category;
   
    public Team() {

	name = "";

    }

    public Team (String name, Club club, String email, Category cat){
        
        this.name= name;
        this.club = club;
        this.email = email;
        this.category = cat;
        
    }
    
    public Team (String name, String email, Category cat){
        
        this.name= name;
        this.email = email;
        this.category = cat;
        
    }
    
    
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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



