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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Match> Matchs = new ArrayList<Match>();

    public Referee() {
    }

    public Referee(String nif, String name) {
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

    public List<Match> getMatchs() {
        return Matchs;
    }

    public void setMatchs(List<Match> Matchs) {
        this.Matchs = Matchs;
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
