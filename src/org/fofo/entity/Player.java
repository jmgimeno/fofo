/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import javax.persistence.*;

/**
 *
 * @author mohamed
 */
@Entity
@Table(name = "PLAYER")
public class Player {
    
    @Id
    @Column(name = "NIF")
    private String nif;
    
    @Column(name = "NAME")
    private String name;
    
    @OneToOne
    @JoinColumn (name = "TEAM_NAME", referencedColumnName = "NAME")
    private Team team;
    
    /**
     *
     */
    public Player() {
        this.nif="";
        this.name="";
    }
    
    /**
     *
     * @param nif
     * @param name
     */
    public Player(String nif, String name) {
        this.nif = nif;
        this.name = name;
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
    public Team getTeam() {
        return team;
    }

    /**
     *
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
    }
    
    @Override
    public String toString() {
        return "Name: " + this.name + ", Nif: " + this.nif;
    }
}
