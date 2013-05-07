/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

/**
 *
 * @author Ivan
 */
public class CompetitionCup extends Competition{
    
    private Type type;
    private int maxTeams;
    private int minTeams;
    
    public CompetitionCup(Type type){
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
    
    private boolean validMinTeams(int teams){
        if (teams >= 2 && teams % 2 != 0 && isPowerOfTwo(teams)) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isPowerOfTwo(int value){
        return (value != 0) && ((value & (value - 1)) == 0);
    }
    
}
