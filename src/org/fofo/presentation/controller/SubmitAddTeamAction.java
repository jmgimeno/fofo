package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.fofo.presentation.view.*;
import org.fofo.services.management.*;


/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class SubmitAddTeamAction implements ActionListener{
    AddTeamForm addteamform;
    ManagementService services;
    
   public SubmitAddTeamAction(AddTeamForm form){
       addteamform = form;
   } 
    
   public void setServices(ManagementService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
   
       String teamName = addteamform.teamName.getText();
       String competitionName = addteamform.competitionName.getText();
 
       addteamform.parent.getContentPane().removeAll();
      
        try{
          services.addTeam(competitionName, teamName);
  
          AddTeamConfirmation confirm = new AddTeamConfirmation();
          addteamform.parent.getContentPane().add(confirm,BorderLayout.CENTER);
     
        }
        catch(Exception exc){
           exc.printStackTrace(); 
          ExceptionInfo exception = new ExceptionInfo("Problems at add team in competition");
          addteamform.parent.getContentPane().add(exception,BorderLayout.CENTER);
        }
        finally{
            addteamform.parent.revalidate();                        
        }
   }   
}