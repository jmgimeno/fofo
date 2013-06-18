package org.fofo.utils;

import java.util.Comparator;
import org.fofo.entity.Team;

/**
 *
 * @author jnp2
 */
public  class InfoClassTeam {
    private int points;
    private Team team;

    /**
        *
        * @param team
        */
    public InfoClassTeam(Team team) {
        this.team = team;
        this.points = 0;
    }

    /**
        *
        * @param p
        */
    public void setPoints(int p){
        this.points = p;
    }

    /**
        *
        * @return
        */
    public int getPoints(){
        return points;
    }

    /**
        *
        * @return
        */
    public Team getTeam(){
        return team;
    }
    @Override
    public boolean equals(Object obj){  
        return (obj instanceof InfoClassTeam) && 
                ((InfoClassTeam)obj).team.equals(this.team) && 
                ((InfoClassTeam)obj).points == this.points;
    }

    public String toString(){
        String result = team.getName() + " With "+ points + " points.";
        return result;
    }


    /**
        *
        */
    public static final Comparator orderByPoints = new Comparator() {            
        public int compare(Object o1, Object o2) {
            if (o1 == o2 || !(o1 instanceof InfoClassTeam ) || 
                            !(o2 instanceof InfoClassTeam )) {
                return 0;
            } else {
                InfoClassTeam r1 = (InfoClassTeam) o1;
                InfoClassTeam r2 = (InfoClassTeam ) o2;
                int points1 = r1.getPoints();
                int points2 = r2.getPoints();
                return points1 <= points2 ? ((int) (points1 >= points2 ? 0: 1)): -1;
            }
        }
    };        



}
