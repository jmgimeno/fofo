package org.fofo.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.fofo.presentation.controller.SubmitAddTeamAction;
import org.fofo.services.management.ManagementService;

/**
 * Contains the form to add a team in a competition.
 * @author David Hernández
 * @author Anton Urrea
 */
public class AddTeamForm extends JPanel {

    public JTextField teamName;
    public JTextField competitionName;
    public JButton but;
    public JFrame parent;
    private ManagementService services;

    public AddTeamForm(JFrame frame, ManagementService services) {

        this.services = services;
        this.parent = frame;

        JLabel lteam = new JLabel();
        lteam.setText(" Team name :");
        teamName = new JTextField();
        teamName.setText("Team name");

        JLabel lcompetition = new JLabel();
        lcompetition.setText(" Competition name :");
        competitionName = new JTextField();
        competitionName.setText("Competition name");

        but = new JButton();
        but.setText("Submit");

        this.add(lteam);
        this.add(teamName);
        this.add(lcompetition);
        this.add(competitionName);
        this.add(but);

        SubmitAddTeamAction action = new SubmitAddTeamAction(this);
        action.setServices(services);

        but.addActionListener(action);
    }

    public void setServices(ManagementService services) {
        this.services = services;
    }
}
