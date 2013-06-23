/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.presentation.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fofo.entity.Competition;

/**
 *
 * @author Anatoli Veselinov
 */
public class CompetitionInfo extends JPanel{
    
    public CompetitionInfo(Competition comp){        
        JLabel l1=new JLabel();
        JLabel l2=new JLabel();
        JLabel l3=new JLabel();
        String compName = comp.getName();
        String compType = comp.getType().name();
        String compDate = comp.getInici().toString();
        
        l1.setText("Name: "+compName);
        l2.setText("Type: "+compType);
        l3.setText("Start date: "+compDate);
        this.add(l1);
        this.add(l2);
        this.add(l3);
        this.setVisible(true);
    }
    
}
