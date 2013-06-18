/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.fofo.presentation.controller.FindCompetitionAction;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author Anatoli Veselinov
 */
public class CompetitionFindForm extends JPanel{
    private ManagementService services;
    public JFrame parent;
    public JTextField txtCompName;
    public JTextField txtTeamName;
    public JButton but;

    
    public CompetitionFindForm(JFrame frame, ManagementService services){
        this.parent = frame;
        this.services = services;
        
        JLabel lname=new JLabel();
        lname.setText("By Competition name ");
        txtCompName=new JTextField();
        txtCompName.setText("  comp. name  ");

        JLabel lteam=new JLabel();
        lteam.setText("By Team name ");
        txtTeamName=new JTextField();
        txtTeamName.setText("  team name  ");

        but=new JButton();
        but.setText("Find");
        
        this.add(lname);
        this.add(txtCompName);
        this.add(but);
        //this.add(lteam);
        //this.add(txtTeamName);
        
        
        
        FindCompetitionAction action = new FindCompetitionAction(this);
        action.setServices(services);
      
        but.addActionListener(action);
    }
    
    public void setServices(ManagementService services){
        this.services = services;
    }
}
