/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

/**
 *
 * @author Ivan
 */


import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;


@Entity
@DiscriminatorValue(value="LEAGUE")

public class CompetitionLeague extends Competition{

    private CompetitionType type;
    private int maxTeams;
    private int minTeams;
    
    public CompetitionLeague(){
        super();
        this.setType(CompetitionType.LEAGUE);
    }
    
    public CompetitionLeague(CompetitionType type){
        super();
        this.setType(type);
    }

    @Override
    public void setMaxTeams(int maxTeams) {
        if(validMaxTeams(maxTeams)) this.maxTeams = maxTeams;
    }
    
    private boolean validMaxTeams(int teams){
        if (teams <= 20 && teams % 2 == 0) {    
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setMinTeams(int minTeams) {
        if(validMinTeams(minTeams)) 
            this.minTeams = minTeams;
    }
    
    public int getMaxTeams(){
        return this.maxTeams;
    }
    
    public int getMinTeams(){
        return this.minTeams;
    }
    
    private boolean validMinTeams(int teams){
        if (teams >= 2 && teams % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
}
