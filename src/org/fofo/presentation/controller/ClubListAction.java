/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import org.fofo.entity.Club;
import org.fofo.presentation.view.ClubListPanel;
import org.fofo.presentation.view.ExceptionInfo;
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
         
         
         try{
          
          List<Club> clubList = services.getClubDao().getClubs();
          ClubListPanel panel = new ClubListPanel(parent, services,clubList);
          parent.getContentPane().add(panel,BorderLayout.CENTER);  

     
        }
        catch(Exception exc){
           exc.printStackTrace(); 
          ExceptionInfo exception = new ExceptionInfo("Problems at Club List");
          parent.getContentPane().add(exception,BorderLayout.CENTER);
                  
        }
        finally{
            parent.revalidate();                        
        }
    }
    
}
