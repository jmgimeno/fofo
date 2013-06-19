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
    private List<InfoClassTeam> infoClassTeam;
    private Competition competition;
    
    /**
     *
     * @param comp
     */
    public Classification(Competition comp){ 
        this.infoClassTeam = new ArrayList<InfoClassTeam>();
        this.competition = comp;
    }
    
    /**
     *
     * @param comp
     * @param ICT
     */
    public Classification(Competition comp,List<InfoClassTeam> ICT){ 
        this.infoClassTeam = ICT;
        this.competition = comp;
    }    
    
    /**
     *
     * @param ICT
     */
    public void setInfoClassTeam(List<InfoClassTeam> ICT){
        this.infoClassTeam = ICT;        
    }
    
    /**
     *
     * @return
     */
    public List<InfoClassTeam> getInfoClassTeam(){
        return infoClassTeam;
    }
    
    public InfoClassTeam getInformationOfPositionN(int n){
        return infoClassTeam.get(n);
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
     * @return
     */
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
