package org.fofo.utils;

import java.util.ArrayList;
import java.util.List;
import org.fofo.entity.ClassificationTC;
import org.fofo.entity.Team;

/**
 *
 * @author Oriol Capell i Jordi Niubo
 */
public class Classification {

    public static class InfoClassTeam {
        private int points;
        private Team team;

        public InfoClassTeam(Team team) {
            this.team = team;
            this.points = 0;
        }
        
        public void setPoints(int p){
            this.points = p;
        }
        
        public int getPoints(){
            return points;
        }
        
        public Team getTeam(){
            return team;
        }
        @Override
        public boolean equals(Object obj){  
            return (obj instanceof InfoClassTeam) && 
                    ((InfoClassTeam)obj).team.equals(this.team) && 
                    ((InfoClassTeam)obj).points == this.points;
        }
        
        public String toString(){
            String result = team.toString() + " With "+ points + " points.";
            return result;
        }
        
    }
    
    private List<InfoClassTeam> infoClassTeam = new ArrayList<InfoClassTeam>();
    
    public Classification(){        
    }
    
}
