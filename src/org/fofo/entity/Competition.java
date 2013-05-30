package org.fofo.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
       

/**
 *
 * @author josepma
 */

@Entity
@Table (name="COMPETITION")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="COMP_TYPE",
                     discriminatorType=DiscriminatorType.STRING,
                     length=6)
public abstract class Competition {
    @Id
    @Column (name = "NAME")
    private String name;
    
    private Category category;
    
     @Temporal(TemporalType.DATE)
    private Date inici;
     
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "COMPETITION_TEAM", 
            joinColumns = @JoinColumn(name="CT_NAME_COMP", referencedColumnName="NAME"),
            inverseJoinColumns = @JoinColumn(name="CT_NAME_TEAM",
            referencedColumnName = "NAME"))
    private List<Team> teams = new ArrayList<Team>();
    
    
    @OneToOne(mappedBy="competition")
    private FCalendar fcalendar;
    
    
    private int maxTeams;
    private int minTeams;
    private CompetitionType type;
    
    /*public Competition(){
        create(this.type);
    }*/
    
    public static Competition create(CompetitionType type){
        if(type.equals(CompetitionType.LEAGUE)){
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


    public void setFcalendar(FCalendar fcalendar) {
        this.fcalendar = fcalendar;
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

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }

    public int getNumberOfTeams() {
        return teams.size();
    }
    
    public FCalendar getFCalendar(){
        return fcalendar;
    }


    
    @Override
    public String toString(){
        
        return "Competition name: "+name+ "  Category: "+category+ " Teams: "+teams ;
        
    }
    
    @Override
    public boolean equals (Object obj){
        
        if(((Competition)obj).getTeams() == null || this.getTeams() == null) return false;
        
        return (obj instanceof Competition)&&((Competition)obj).name.equals(this.name)
                && equals(((Competition)obj).teams);
    }
    
    public boolean equals(List<Team> teams){
        return (this.teams.size() == teams.size())
                && (this.teams.containsAll(teams));
    }
    
}
