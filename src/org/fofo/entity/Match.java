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
    
    //@OneToOne
    //@Column (name="INFO_MATCH")
    //private InfoMatch info;
    
    //HEU DE SUBSTITUIR AQUEST INFO PELS ATRIBUTS QUE CONSTITUEIXEN LA INFO DEL MATCH.
    
    @ManyToOne
    @JoinColumn(name = "REFEREE", referencedColumnName = "NIF")
    private Referee referee;

    //private Stadium
    /**
     *
     */
    public Match() {
        this.idMatch = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre

    }

    /**
     *
     * @param home
     * @param visitor
     */
    public Match(Team home, Team visitor) {
        this.home = home;
        this.visitor = visitor;
        this.idMatch = UUID.randomUUID().toString(); //L'ha de generar Match, ningu altre
    }

    /**
     *
     * @return
     */
    public String getIdMatch() {
        return idMatch;
    }

    /**
     *
     * @return
     */
    public Team getHome() {
        return home;
    }

    /**
     *
     * @param home
     */
    public void setHome(Team home) {
        this.home = home;
    }

    /**
     *
     * @return
     */
    public Team getVisitor() {
        return visitor;
    }

    /**
     *
     * @param visitor
     */
    public void setVisitor(Team visitor) {
        this.visitor = visitor;
    }

    /**
     *
     * @return
     */
    public Date getMatchDate() {
        return this.matchDate;
    }

    /**
     *
     * @param matchDate
     */
    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
/*
    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }
*/
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

    @Override
    public String toString() {

        return "<Match:" + home.getName() + "-" + visitor.getName() + ">";
    }

    /**
     *
     * @return
     */
    public Referee getReferee() {
        return this.referee;
    }


    /**
     *
     * @param ref
     */
    public void setReferee(Referee ref) {
        this.referee = ref;
    }
}