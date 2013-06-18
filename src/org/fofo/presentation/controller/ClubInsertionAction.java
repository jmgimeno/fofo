/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.fofo.presentation.view.ClubInsertionForm;
import org.fofo.services.management.*;
import javax.swing.JFrame;

/**
 *
 * @author josepma
 * 
 * CLubInsertionAction is the controller action that launches the form to
 * insert a club.
 * 
 */
   public class ClubInsertionAction implements ActionListener{

       JFrame parent;
       ManagementService services;
       
    public ClubInsertionAction(JFrame frame, ManagementService services){
        parent = frame;
        this.services = services;
        
    }
    
    public void actionPerformed(ActionEvent e){
    
      
        parent.getContentPane().removeAll();
         
          ClubInsertionForm form = new ClubInsertionForm(parent,services);
          
          parent.getContentPane().add(form,BorderLayout.CENTER);
          parent.revalidate();                        
        }
     }
