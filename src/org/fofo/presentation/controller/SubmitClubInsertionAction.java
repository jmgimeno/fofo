/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.fofo.presentation.view.*;
import org.fofo.services.management.*;
import org.fofo.entity.*;


/**
 *
 * @author josepma
 * 
 * Controller action that manages the insertion of a club 
 * (Launched by the "Submit" button in the ClubInsertionForm form)
 * 
 */
public class SubmitClubInsertionAction implements ActionListener{
    ClubInsertionForm clubform;
    ManagementService services;
    
   public SubmitClubInsertionAction(ClubInsertionForm form){
       clubform = form;
       
       
   } 
    
   public void setServices(ManagementService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
   
       System.out.println("oioioi");
       
       System.out.println("club form ="+clubform.txtName);
       Club club = new Club(clubform.txtName.getText());
       
       //cal fer validacio email, nom
       club.setEmail(clubform.txtEmail.getText());
 
       clubform.parent.getContentPane().removeAll();
      
        try{
          services.addClub(club);
  
          ClubInsertConfirmation confirm = new ClubInsertConfirmation();
          clubform.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(Exception exc){
           exc.printStackTrace(); 
          ExceptionInfo exception = new ExceptionInfo("Problems at club insertion");
          clubform.parent.getContentPane().add(exception,BorderLayout.CENTER);
                  
        }
        finally{
            clubform.parent.revalidate();                        
        }
       
       
       
   }
    
    
}
