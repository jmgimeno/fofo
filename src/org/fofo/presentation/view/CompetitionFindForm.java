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
    

    
    public CompetitionFindForm(JFrame frame, ManagementService services){
        this.parent = frame;
        this.services = services;
        
        createGUI();
    }
    
    public void setServices(ManagementService services){
        this.services = services;
    }

    private void createGUI() {
        JLabel lname=new JLabel();
        lname.setText("By Competition name ");
        txtCompName=new JTextField(10);


        JLabel lteam=new JLabel();
        lteam.setText("By Team name ");
        txtTeamName=new JTextField(10);

        
        FindCompetitionAction action = new FindCompetitionAction(this);
        action.setServices(services);        
        
        JButton but1 = new JButton();
        but1.setText("Find");      
        but1.addActionListener(action);    
        
        this.add(lname);
        this.add(txtCompName);
        this.add(but1);
        
        JButton but2 = new JButton();
        but2.setText("Find");      
        but2.addActionListener(action);
        this.add(lteam);
        this.add(txtTeamName);
        this.add(but2);
    }
}
