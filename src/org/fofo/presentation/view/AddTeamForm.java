package org.fofo.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class AddTeamForm {

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
        this.addTeam(teamName, competitionName);
    }
}
