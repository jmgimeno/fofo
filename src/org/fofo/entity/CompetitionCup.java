
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
@DiscriminatorValue(value="CUP")
public class CompetitionCup extends Competition{
    
    private CompetitionType type;
    private int maxTeams;
    private int minTeams;
    
    public CompetitionCup(){}
    
    public CompetitionCup(CompetitionType type){
        super();
        this.setType(type);
    }

    @Override
    public void setMaxTeams(int maxTeams) {
        if(validMaxTeams(maxTeams)) this.maxTeams = maxTeams;
    }
    
    private boolean validMaxTeams(int teams){
        if (teams <= 64 && isPowerOfTwo(teams)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setMinTeams(int minTeams) {
        if(validMinTeams(minTeams)) this.minTeams = minTeams;
    }
    
    public int getMinTeams(){
        return this.minTeams;
    }
    
    public int getMaxTeams(){
        return this.maxTeams;
    }
    
    private boolean validMinTeams(int teams){
        if (teams >= 2 && teams % 2 == 0 && isPowerOfTwo(teams)) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isPowerOfTwo(int value){
        return (value != 0) && ((value & (value - 1)) == 0);
    }
    
}
