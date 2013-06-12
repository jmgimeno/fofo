/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;

import org.fofo.presentation.controller.*;
import org.fofo.services.management.*;
/**
 *
 * @author josepma
 * 
 * CLubInsertionForm contains the form to insert the information to 
 * insert a club.
 * 
 */
public class ClubInsertionForm extends JPanel{

    public JTextField txtName;
    public JTextField txtEmail;
    public JButton but;
    public JFrame parent;
    private ManagementService services;
    
    public ClubInsertionForm(JFrame frame, ManagementService services){
    

      this.services = services;  
      this.parent = frame;
        
      JLabel lname=new JLabel();
      lname.setText(" Club name :");
      txtName=new JTextField();
      txtName.setText("Club name");
      
      JLabel lemail=new JLabel();
      lemail.setText(" Club email :");
      txtEmail=new JTextField();
      txtEmail.setText("Club email");
      
      but=new JButton();
      but.setText("Submit");
      this.add(lname);
      this.add(txtName);
      this.add(lemail);
      this.add(txtEmail);
      this.add(but);
      
      SubmitClubInsertionAction action = new SubmitClubInsertionAction(this);
      action.setServices(services);
      
      but.addActionListener(action);
        //The SubmitClubInsertionAction controller action is associated to
        //the "Submit" button. 
 

    }
    
    public void setServices(ManagementService services){
        this.services = services;
    }
            
            
    
    
}
