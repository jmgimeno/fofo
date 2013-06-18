package org.fofo.presentation.view;
import org.fofo.presentation.controller.CompetitionInsertionAction;
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
public class FofoFrame extends JFrame {

    private JMenuBar menubar;
    private JPanel content;
    private ManagementService services;

    public FofoFrame(ManagementService services) {

        super("FOFO competition management application");
        this.services = services;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(400, 400);

        createMenuBar();
        createContents();


        this.setVisible(true);


    }

    private void createContents() {

        content = new JPanel();

        content.add(new JLabel("Welcome to FOFO"));

        this.getContentPane().add(content, BorderLayout.CENTER);

    }

    private void createMenuBar() {
        menubar = new JMenuBar();
        /*--------------------------------------------------------------*/
        JMenu clubmenu = new JMenu("Clubs");
        clubmenu.add(new JSeparator());
        
        JMenuItem clubItem1 = new JMenuItem("New club");
        clubItem1.addActionListener(new ClubInsertionAction(this, services));
        //We associate the "New club" button of the menu to the 
        // ClubInsertionAction controller action.
        //This is not ideal. It would be better to decouple completely the 
        //view (e.g., this FofoFrame class) and the controller (ClubInsertionAction).
        //However, the overall design would be a bit more complicated (but more reusable).

        JMenuItem clubItem2 = new JMenuItem("Retrieve club");

        JMenuItem clubItem3 = new JMenuItem("List clubs");
        clubItem3.addActionListener(new ClubListAction(this, services));
        
        clubmenu.add(clubItem1);
        clubmenu.add(clubItem2);
        clubmenu.add(clubItem3);
        menubar.add(clubmenu);
        /*--------------------------------------------------------------*/
        JMenu teammenu = new JMenu("Teams");
        teammenu.add(new JSeparator());

        JMenuItem teamItem1 = new JMenuItem("New team");             
        
        teammenu.add(teamItem1);
        menubar.add(teammenu);
        /*--------------------------------------------------------------*/
        createCompetitionMenu();
        
       
        this.setJMenuBar(menubar);
    }

    private void createCompetitionMenu() {
        JMenu competmenu = new JMenu("Competitions");
        competmenu.add(new JSeparator());
        
        JMenuItem compItem1 = new JMenuItem("New competition");
        compItem1.addActionListener(new CompetitionInsertionAction(this, services));
        
        JMenuItem addTeam = new JMenuItem("Add team to competition");
        addTeam.addActionListener(new AddTeamAction(this, services));
                
        JMenuItem findComp = new JMenuItem("Find competition");
        findComp.addActionListener(new CompetitionFindAction(this,services));
        
        competmenu.add(compItem1);
        competmenu.add(addTeam);
        competmenu.add(findComp);
        
        menubar.add(competmenu);
    }
}
