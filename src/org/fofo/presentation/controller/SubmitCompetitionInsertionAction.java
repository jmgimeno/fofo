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
import org.joda.time.DateTime;


/**
 *
 * @author josepma
 * 
 * Controller action that manages the insertion of a club 
 * (Launched by the "Submit" button in the ClubInsertionForm form)
 * 
 */
public class SubmitCompetitionInsertionAction implements ActionListener{
    CompetitionInsertionForm compform;
    ManagementService services;
    
   public SubmitCompetitionInsertionAction(CompetitionInsertionForm form){
       compform = form;
   } 
    
   public void setServices(ManagementService services){
       
       this.services = services;
   }
   
   public void actionPerformed(ActionEvent e){
   
       System.out.println("oioioi");
       Competition comp = new CompetitionLeague();
       
       System.out.println("comp form ="+compform.txtName);
       if(compform.button4.isSelected()){
            comp = new CompetitionLeague();
            comp.setName(compform.txtName.getText());
       }else if(compform.button5.isSelected()){
            comp = new CompetitionCup();
            comp.setName(compform.txtName.getText());
       }
       
       //cal fer validacio email, nom
       if(compform.button1.isSelected()){
           comp.setCategory(Category.MALE);
       }else if(compform.button2.isSelected()){
           comp.setCategory(Category.FEMALE);
       }else{
           comp.setCategory(Category.VETERAN);
       }
       DateTime date = new DateTime();
       date = date.withDate((Integer)compform.years.getSelectedItem(), 
               (Integer)compform.months.getSelectedItem(), 
               (Integer)compform.days.getSelectedItem());
       comp.setInici(date.toDate());
       
       comp.setMinTeams((Integer)compform.boxMin.getSelectedItem());
       comp.setMaxTeams((Integer)compform.boxMax.getSelectedItem());
 
       compform.parent.getContentPane().removeAll();
      
        try{
          services.addCompetition(comp);
          CompetitionInsertConfirmation confirm = new CompetitionInsertConfirmation();
          compform.parent.getContentPane().add(confirm, BorderLayout.CENTER);
     
        }
        catch(Exception exc){
           exc.printStackTrace(); 
          ExceptionInfo exception = new ExceptionInfo("Problems at competition insertion");
          compform.parent.getContentPane().add(exception,BorderLayout.CENTER);
                  
        }
        finally{
            //compform.parent.revalidate();                        
        }
       
       
       
   }
    
    
}
