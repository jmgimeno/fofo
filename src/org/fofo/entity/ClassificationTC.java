package org.fofo.entity;

/**
 *
 * @author Oriol Capell i Jordi Niubó
 */
public class ClassificationTC {
    
    Competition competition;
    Team team;
    int points;
    
    public ClassificationTC(Competition competition, Team team){
        this.team = team;
        this.competition = competition;
        this.points = 0;
    }
    
    public int getPoints(){
        return points;
    }
    
    public void setPoints(int points){
        this.points = points;
    }
    
}
