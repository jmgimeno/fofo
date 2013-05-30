/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author portatil
 */
@Entity
@Table (name="CLUB")
public class Club {
    @Id
    @Column (name="NAME")
    private String name;
    private String email;
     
    @OneToMany (mappedBy="club", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Team> teams = new ArrayList<Team>();
    
    public Club(){
        
    }
    
    public Club(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    
    public boolean equals(Object obj){
        
        return (obj instanceof Club) && 
                ((Club) obj).name.equals(this.name) &&
                ((Club) obj).email.equals(this.email) &&
                ((Club) obj).teams.size() == this.teams.size() &&
                 equalTeamNames(((Club) obj).teams,this.teams);
                
    }
    
    public String toString(){
     
        return "Club name="+name + "teams="+teams;
        
    }




    private boolean equalTeamNames(List<Team> teams1, List<Team> teams2){
 
      for (Team team: teams1){
          
          if (! findName(team.getName(),teams2)) return false;
          
      }    
      return true;
      
          
    }

    private boolean findName(String name, List<Team> teams){
     
        for (Team team : teams){
          if ( name.equals(team.getName())) return true;
                   
        }    
        return false;
    
    }   

 
}