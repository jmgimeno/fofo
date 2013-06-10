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
    private String email;

    @OneToMany(mappedBy="referee", cascade=CascadeType.PERSIST, fetch = FetchType.EAGER) 
    private List<Match> assignedMatches;

    /**
     *
     */
    public Referee() {
        this.assignedMatches = new ArrayList<Match>();
        this.nif="";
        this.name="";
        this.email="";
    }

    /**
     *
     * @param nif
     * @param name
     */
    public Referee(String nif, String name) {
        this.assignedMatches = new ArrayList<Match>();
        this.nif = nif;
        this.name = name;
        this.email = "";
    }

    /**
     *
     * @return
     */
    public String getNif() {
        return nif;
    }

    /**
     *
     * @param nif
     */
    public void setNif(String nif) {
        this.nif = nif;
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
     * @return
     */
    public List<Match> getMatches() {
        return assignedMatches;
    }
    
    /**
     *
     * @param matches
     */
    public void setMatches(List<Match> matches){
        this.assignedMatches = matches;
    }

    
    /**
     *
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Nif: " + this.nif + ", Email: "+this.email;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Referee)
                && ((Referee) obj).nif.equals(this.nif)
                && ((Referee) obj).name.equals(this.name)
                && ((Referee) obj).email.equals(this.email);

    }


}
