package org.fofo.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author josepma
 */
@Entity
@Table (name="COMPETITION")
public abstract class Competition {
    @Id
    @Column (name = "NAME")
    private String name;
    
    private Category category;
    private Date inici;
    @ManyToMany
    @JoinTable(name = "COMPETITION_TEAM", 
            joinColumns = @JoinColumn(name="CT_NAME_COMP", referencedColumnName="NAME"),
            inverseJoinColumns = @JoinColumn(name="CT_NAME_TEAM",
            referencedColumnName = "NAME"))
    private List<Team> teams = new ArrayList<Team>();
    
    
    @OneToOne(mappedBy="competition")
    private FCalendar fcalendar;
    
    
    private int maxTeams;
    private int minTeams;
    private Type type;
    
    /*public Competition(){
        create(this.type);
    }*/
    
    public static Competition create(Type type){
        if(type.equals(Type.LEAGUE)){
            return new CompetitionLeague(type);
        }
        else return new CompetitionCup(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public abstract void setMaxTeams(int maxTeams);

    public int getMinTeams() {
        return minTeams;
    }

    public abstract void setMinTeams(int minTeams);

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
