/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import org.fofo.entity.Competition;

/**
 *
 * @author josepma
 */
public class ManagementService {

    void addCompetition(Competition comp){
        
        if (comp.getCategory()== null) throw new IncorrectCompetitionData();
        
        //THIS IS THE OPERATION TO IMPLEMENT.....
        
    }
    
    
}
