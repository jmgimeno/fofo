/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Josep M. Ribo.....
 */
@Entity
@Table(name = "TEAM")
public class Team implements Serializable {

    @Id
    @Column(name = "NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name = "CLUB_NAME", referencedColumnName = "NAME")
    private Club club;
    private Category category;
    private String email;
    @ManyToMany(mappedBy = "teams")
    private List<Competition> competitions;
    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<ClassificationTC> classificationsTC = new ArrayList<ClassificationTC>();
    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<Player>();
    
    private int minPlayers;
    private int maxPlayers;

    /**
     *
     * @return
     */
    public List<Competition> getCompetitions() {
        return competitions;
    }

    /**
     *
     * @param competitions
     */
    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     */
    public Team() {

        name = "";
        category = null;
        competitions = new ArrayList<Competition>();
        club = null;
        players = new ArrayList<Player>();
    }

    /**
     *
     * @param name
     * @param club
     * @param cat
     */
    public Team(String name, Club club, Category cat) {

        this.name = name;
        this.club = club;
        this.category = cat;
        competitions = new ArrayList<Competition>();
        players = new ArrayList<Player>();

    }

    /**
     *
     * @param name
     * @param cat
     */
    public Team(String name, Category cat) {

        this.name = name;
        this.category = cat;
        this.club = null;
        competitions = new ArrayList<Competition>();
        players = new ArrayList<Player>();

    }

    /**
     *
     * @param name
     */
    public Team(String name) {
        this.name = name;
        this.club = null;
        this.competitions = new ArrayList<Competition>();
        players = new ArrayList<Player>();
    }

    /**
     *
     * @return
     */
    public Club getClub() {
        return club;
    }

    /**
     *
     * @param club
     */
    public void setClub(Club club) {
        this.club = club;
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
    public List<ClassificationTC> getClassificationsTC() {
        return classificationsTC;
    }

    /**
     *
     * @param classTC
     */
    public void setClassificationsTC(List<ClassificationTC> classTC) {
        this.classificationsTC = classTC;
    }

    /**
     *
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     *
     * @return
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     *
     * @param minPlayers
     */
    public void setMinPlayers(int minPlayers) {
        if (validMinPlayers(minPlayers)) {
            this.minPlayers = minPlayers;
        }
    }

    private boolean validMinPlayers(int numPlayers) {
        if (numPlayers >= 11) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     *
     * @param maxPlayers
     */
    public void setMaxPlayers(int maxPlayers) {
        if (validMaxPlayers(maxPlayers)) {
            this.maxPlayers = maxPlayers;
        }
    }

    private boolean validMaxPlayers(int numPlayers) {
        if (numPlayers <= 18) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        String result = "TEAM: Team name: " + name + " Category: " + category + " Email: " + email
                + " Club:";
        if (club == null) {
            return result + "null";
        } else {
            return result + club.getName();
        }
    }

    @Override
    public boolean equals(Object obj) {

        return (obj instanceof Team) && ((Team) obj).name.equals(this.name)
                && ((((Team) obj).club == null && this.club == null)
                || ((Team) obj).club.equals(this.club));
    }
}
