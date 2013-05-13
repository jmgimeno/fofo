/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    
    @OneToMany (mappedBy="club")
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
        
        return (obj instanceof Club) && ((Club) obj).name.equals(this.name);
    }
}
