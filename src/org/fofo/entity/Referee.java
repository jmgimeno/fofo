/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author mohamed, Anatoli
 */
@Entity
@Table(name = "REFEREE")
public class Referee {

    @Id
    @Column(name = "NIF")
    private String nif;
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy="referee", cascade=CascadeType.PERSIST, fetch = FetchType.EAGER) 
    private List<Match> assignedMatches;
    private String email;

    public Referee() {
        this.assignedMatches = new ArrayList<Match>();
    }

    public Referee(String nif, String name) {
        this.assignedMatches = new ArrayList<Match>();
        this.nif = nif;
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getMatches() {
        return assignedMatches;
    }
    
    public void setMatches(List<Match> matches){
        this.assignedMatches = matches;
    }

    
    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Nif: " + this.nif + "";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Referee)
                && ((Referee) obj).nif.equals(this.nif)
                && ((Referee) obj).name.equals(this.name);

    }


}
