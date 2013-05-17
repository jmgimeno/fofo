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
@Table (name ="TEAM")
public class Team implements Serializable {
    @Id
    @Column (name="NAME")
    private String name;
    
    @ManyToOne
    @JoinColumn (name="CLUB_NAME", referencedColumnName="NAME")
    private Club club;
    private Category category;
    private String email;
    
    @ManyToMany(mappedBy="teams")
    
    private Competition competition;

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    public Team() {

	name = "";

    }

    public Team (String name, Club club, Category cat){
        
        this.name= name;
        this.club = club;
        this.category = cat;
        
    }
    
    public Team (String name, Category cat){
        
        this.name= name;
        this.category = cat;
        
    }
    
    public Team (String name){
        this.name = name;
    }
    
    
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Team name"+name;
    }
    
    @Override
    public boolean equals(Object obj){
        
        return (obj instanceof Team) && ((Team)obj).name.equals(this.name) && 
                  ( ( ((Team)obj).club == null && this.club == null) ||
                      ((Team)obj).club.equals(this.club)
                    );
    }

}



