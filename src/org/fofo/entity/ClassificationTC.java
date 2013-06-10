package org.fofo.entity;

import java.util.UUID;
import javax.persistence.*;

/**
 *
 * @author Oriol Capell i Jordi Niubó
 */
@Entity
@Table (name ="CLASSIFICATIONTC")
public class ClassificationTC {
    @Id
    @Column (name="ID")
    private String id;
    
    @ManyToOne
    @JoinColumn (name="COMPETITION_NAME", referencedColumnName="NAME")
    private Competition competition;
    
    @ManyToOne
    @JoinColumn (name="TEAM_NAME", referencedColumnName="NAME")
    private Team team;
    
    private int points;

    /**
     *
     */
    public ClassificationTC() {    
        this.id = UUID.randomUUID().toString();
        this.points = 0;
    }
    
    /**
     *
     * @param competition
     * @param team
     */
    public ClassificationTC(Competition competition, Team team){
        this.id = UUID.randomUUID().toString();
        this.team = team;
        this.competition = competition;
        this.points = 0;
    }
    
    /**
     *
     * @param points
     */
    public void setPoints(int points){
        this.points = points;
    }
    
    /**
     *
     * @return
     */
    public int getPoints(){
        return points;
    }
    
    /**
     *
     * @return
     */
    public Competition getCompetition(){
        return competition;
    }
    
    /**
     *
     * @param comp
     */
    public void setCompetition(Competition comp){
        this.competition = comp;
    }
    
    /**
     *
     * @return
     */
    public Team getTeam(){
        return team;
    }
    
    /**
     *
     * @param team
     */
    public void setTeam(Team team){
        this.team = team;
    }
    
    /**
     *
     * @return
     */
    public String getId(){
        return id;
    }
}
