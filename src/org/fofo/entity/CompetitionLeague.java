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


/**
 *
 * @author ruffolution
 */
@Entity
@DiscriminatorValue(value="LEAGUE")

public class CompetitionLeague extends Competition{

    private CompetitionType type;
    private int maxTeams;
    private int minTeams;
    
    /**
     *
     */
    public CompetitionLeague(){
        super();
        this.setType(CompetitionType.LEAGUE);
    }
    
    /**
     *
     * @param type
     */
    public CompetitionLeague(CompetitionType type){
        super();
        this.setType(type);
    }

    /**
     *
     * @param maxTeams
     */
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

    /**
     *
     * @param minTeams
     */
    @Override
    public void setMinTeams(int minTeams) {
        if(validMinTeams(minTeams)) 
            this.minTeams = minTeams;
    }
    
    /**
     *
     * @return
     */
    public int getMaxTeams(){
        return this.maxTeams;
    }
    
    /**
     *
     * @return
     */
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
