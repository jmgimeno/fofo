package org.fofo.utils;

import java.util.ArrayList;
import java.util.List;
import org.fofo.entity.ClassificationTC;
import org.fofo.entity.Team;

/**
 *
 * @author Oriol Capell i Jordi Niubo
 */
public class Classification {

    public static class InfoClassTeam {
        int points;
        Team team;

        public InfoClassTeam() {
        }
    }
    
    private List<InfoClassTeam> infoClassTeam = new ArrayList<InfoClassTeam>();
    
    public Classification(){        
    }
    
}
