/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import org.fofo.utils.InfoMatch;

/**
 *
 * @author mohamed, Anatoli
 *
 */
@Entity
@Table(name = "Match_")
public class Match {

    @Id
    @Column(name = "ID_MATCH")
    private String idMatch;
    @OneToOne
    @JoinColumn(name = "HOME_T_NAME", referencedColumnName = "NAME")
    private Team home;
    @OneToOne
    @JoinColumn(name = "VISITOR_T_NAME", referencedColumnName = "NAME")
    private Team visitor;
    @Temporal(TemporalType.DATE)
    @Column(name = "MATCH_DATE")
    private Date matchDate;
    @ManyToOne
    private String nif;
    
    @OneToOne
    @Column (name="INFO_MATCH")
    private InfoMatch info;
    //Alejandro+Ivan: Hem afegit el atribut referee ja que 
    //Match i Referee han d'estar relacionats, pero aquesta tasca no 
    //l'hem de fer nosaltres. Us falta fer el ORM.
    private Referee referee;

    //private Stadium
    public Match() {
        this.idMatch = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre

    }

    public Match(Team home, Team visitor) {
        this.home = home;
        this.visitor = visitor;
        this.idMatch = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre
    }

    public String getIdMatch() {
        return idMatch;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getVisitor() {
        return visitor;
    }

    public void setVisitor(Team visitor) {
        this.visitor = visitor;
    }

    public Date getMatchDate() {
        return this.matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Match)) {
            return false;
        }

        Match m = (Match) obj;

        return m.home.equals(this.home) && m.visitor.equals(this.visitor)
                && m.referee.equals(this.referee);
        //getName().equals(home.getName()) && 
        //m.visitor.getName().equals (visitor.getName());


    }

    public String toString() {

        return "<Match:" + home.getName() + "-" + visitor.getName() + ">";
    }

    public Referee getReferee() {
        return this.referee;
    }

    public void setInfo(InfoMatch im) {
        this.info = im;
    }
    
    public InfoMatch getInfo(){
        return this.info;
    }

    public void setReferee(Referee ref) {
        this.referee = ref;
    }
}