package org.fofo.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.fofo.entity.ClassificationTC;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.fofo.entity.WeekMatch;

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
            String result = team.getName() + " With "+ points + " points.";
            return result;
        }
        
        
        public static final Comparator orderByPoints = new Comparator() {            
            public int compare(Object o1, Object o2) {
                if (o1 == o2 || !(o1 instanceof InfoClassTeam ) || 
                                !(o2 instanceof InfoClassTeam )) {
                    return 0;
                } else {
                    InfoClassTeam r1 = (InfoClassTeam) o1;
                    InfoClassTeam r2 = (InfoClassTeam ) o2;
                    int points1 = r1.getPoints();
                    int points2 = r2.getPoints();
                    return points1 <= points2 ? ((int) (points1 >= points2 ? 0: 1)): -1;
                }
            }
        };        
        
        
        
    }
    
    private List<InfoClassTeam> infoClassTeam;
    private Competition competition;
    
    public Classification(Competition comp){ 
        this.infoClassTeam = new ArrayList<InfoClassTeam>();
        this.competition = comp;
    }
    
    public Classification(Competition comp,List<InfoClassTeam> ICT){ 
        this.infoClassTeam = ICT;
        this.competition = comp;
    }    
    
    public void setInfoClassTeam(List<InfoClassTeam> ICT){
        this.infoClassTeam = ICT;        
    }
    
    public List<InfoClassTeam> getInfoClassTeam(){
        return infoClassTeam;
    }
    
    public Competition getCompetition(){
        return competition;    
    }
    
    public String getName(){
        return "Classification of Competition "+competition.getName();
    }
    
    @Override
    public boolean equals(Object obj){  
        if (! (obj instanceof Classification) ) return false;
        
        Classification classif = (Classification) obj;
        
        return  this.competition.equals(classif.competition) &&
                this.infoClassTeam.size() == classif.infoClassTeam.size() && 
                this.infoClassTeam.containsAll(classif.infoClassTeam);
    }
        
    public String toString(){
            String result = getName()+"\n";
            for(int i=0; i<infoClassTeam.size();i++){ 
                result = result + "Position " +(i+1)+ ":"+ infoClassTeam.get(i).toString()+"\n";                             
            }
            return result;
        }
    
    
}
