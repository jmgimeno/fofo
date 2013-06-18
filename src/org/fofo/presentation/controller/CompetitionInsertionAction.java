/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.fofo.presentation.view.CompetitionInsertionForm;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author imt1
 */
public class CompetitionInsertionAction implements ActionListener{

    JFrame parent;
    ManagementService services;
       
    public CompetitionInsertionAction(JFrame frame, ManagementService services){
        parent = frame;
        this.services = services;
        
    }
    
    public void actionPerformed(ActionEvent e){
    
      
        parent.getContentPane().removeAll();
         
        CompetitionInsertionForm form = new CompetitionInsertionForm(parent,services);
        parent.getContentPane().add(form,BorderLayout.CENTER);
     
        //parent.revalidate();                        
    }
}
