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
    
    @OneToMany (mappedBy="competition", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();   
    
    /*public Competition(){
        create(this.type);
    }*/
    
    /**
     *
     * @param type
     * @return
     */
    public static Competition create(CompetitionType type){
        if(type.equals(CompetitionType.LEAGUE)){
            return new CompetitionLeague(type);
        }
        else return new CompetitionCup(type);
    }
   
    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     *
     * @param team
     */
    public void addTeam(Team team){
        teams.add(team);
    }

    /**
     *
     * @return
     */
    public Category getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }


    /**
     *
     * @param fcalendar
     */
    public void setFcalendar(FCalendar fcalendar) {
        this.fcalendar = fcalendar;
    }

    
    /**
     *
     * @return
     */
    public Date getInici() {
        return inici;
    }

    /**
     *
     * @param inici
     */
    public void setInici(Date inici) {
        this.inici = inici;
    }

    /**
     *
     * @return
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    /**
     *
     * @return
     */
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     *
     * @param maxTeams
     */
    public abstract void setMaxTeams(int maxTeams);

    /**
     *
     * @return
     */
    public int getMinTeams() {
        return minTeams;
    }

    /**
     *
     * @param minTeams
     */
    public abstract void setMinTeams(int minTeams);

    /**
     *
     * @return
     */
    public CompetitionType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(CompetitionType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public int getNumberOfTeams() {
        return teams.size();
    }
    
    /**
     *
     * @return
     */
    public FCalendar getFCalendar(){
        return fcalendar;
    }
    
    /**
     *
     * @return
     */
    public List<ClassificationTC> getClassificationsTC(){
        return classificationsTC;
    }
    
    /**
     *
     * @param classTC
     */
    public void setClassificationsTC(List<ClassificationTC> classTC){
        this.classificationsTC = classTC;        
    }


    
    @Override
    public String toString(){
        
        return "Competition name: "+name+ "  Category: "+category+ " Teams: "+teams ;
        
    }
    
    @Override
    public boolean equals (Object obj){
      
           //comp.teams is never null since it is initialized to an
           //empty list.
        
        return (obj instanceof Competition)&&((Competition)obj).name.equals(this.name)
                && equals(((Competition)obj).teams);
    }
    
    /**
     *
     * @param teams
     * @return
     */
    public boolean equals(List<Team> teams){
        return (this.teams.size() == teams.size())
                && (this.teams.containsAll(teams));
    }
    
}
