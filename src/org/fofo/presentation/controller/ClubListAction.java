/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.fofo.presentation.view.ClubListPanel;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author Roger Torra
 */
public class ClubListAction implements ActionListener{
       
    JFrame parent;
    
    ManagementService services;
    
    public ClubListAction(JFrame frame, ManagementService services){
        parent = frame;
        this.services = services;
        
    }

    
    public void actionPerformed(ActionEvent e) {
         parent.getContentPane().removeAll(); //clean frame
         ClubListPanel panel = new ClubListPanel(parent, services);
         parent.getContentPane().add(panel,BorderLayout.CENTER);
         parent.revalidate();
    }
    
}
