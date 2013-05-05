/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author josepma
 */
public class Competition {
    
    private Category category;
    private Date inici;
    public List<Team> teams = new ArrayList<Team>();
    private int maxTeams;
    private int minTeams;
    private Type type;
    
    public Competition(){
        
    }
    
    public void addTeam(Team team){
        teams.add(team);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getInici() {
        return inici;
    }

    public void setInici(Date inici) {
        this.inici = inici;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    public int getMinTeams() {
        return minTeams;
    }

    public void setMinTeams(int minTeams) {
        this.minTeams = minTeams;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getNumberOfTeams() {
        return teams.size();
    }
    
    
    
}
