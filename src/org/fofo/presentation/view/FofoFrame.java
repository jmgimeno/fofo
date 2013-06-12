/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.view;


import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;

import org.fofo.presentation.controller.*;
import org.fofo.services.management.*;

/**
 *
 * @author josepma
 * 
 * The main frame of the FOFO application
 * 
 */
public class FofoFrame extends JFrame{
    
    private JMenuBar menubar;
    private JPanel content;
    private ManagementService services;
    
    
    public FofoFrame(ManagementService services){
        
        super("FOFO competition management application");
        this.services = services;
     
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(400,400);
     
        createMenuBar();
        createContents();
     
        
        this.setVisible(true);

        
    }
  
    
    private void createContents(){

        content = new JPanel();
        
        content.add(new JLabel("Welcome to FOFO"));
        
        this.getContentPane().add(content, BorderLayout.CENTER);

    }
    
    private void createMenuBar(){
      menubar = new JMenuBar();
      JMenu clubmenu = new JMenu("Clubs");
      clubmenu.add(new JSeparator());
      JMenu teammenu = new JMenu("Teams");
      teammenu.add(new JSeparator());
        
      JMenu competmenu = new JMenu("Competitions");
      competmenu.add(new JSeparator());

      JMenuItem clubItem1 = new JMenuItem("New club");
      clubItem1.addActionListener(new ClubInsertionAction(this, services));
        //We associate the "New club" button of the menu to the 
        // ClubInsertionAction controller action.
        //This is not ideal. It would be better to decouple completely the 
        //view (e.g., this FofoFrame class) and the controller (ClubInsertionAction).
        //However, the overall design would be a bit more complicated (but more reusable).
      
      JMenuItem clubItem2 = new JMenuItem("Retrieve club");
   
  
     JMenuItem teamItem1 = new JMenuItem("New team");
 
     clubmenu.add(clubItem1);
     clubmenu.add(clubItem2);
     teammenu.add(teamItem1);

     menubar.add(clubmenu);
     menubar.add(teammenu);
     menubar.add(competmenu);
     this.setJMenuBar(menubar);
     
     
     
    }
    
    
}
