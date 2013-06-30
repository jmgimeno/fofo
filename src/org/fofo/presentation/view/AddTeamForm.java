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
        
/* Codi no validat */
//Object[][] competitionsdata = {
//{"Competition1", FEMALE, 05/08/13, new Integer(4), "123456789", 11, 2, CUP, new Integer(5)};
//{"Competition2", FEMALE, 05/09/13, new Integer(4), "123456790", 11, 2, CUP, new Integer(5)};
//{"Competition3", FEMALE, 15/10/13, new Integer(4), "123456791", 11, 2, CUP, new Integer(5)};
//{"Competition4", FEMALE, 05/11/13, new Integer(4), "123456792", 11, 2, CUP, new Integer(5)};
//{"Competition5", MALE, 05/12/13, new Integer(4), "123456793", 11, 2, CUP, new Integer(5)};
//{"Competition6", MALE, 05/12/13, new Integer(4), "123456794", 11, 2, CUP, new Integer(5)};
//{"Competition7", MALE, 15/12/13, new Integer(4), "123456795", 11, 2, CUP, new Integer(5)};
//{"Competition8", MALE, 05/12/13, new Integer(4), "123456796", 11, 2, CUP, new Integer(5)};
//};
//        
//        
//        
//Object[][] teamsdata = {
//{"Team1", "club1", FEMALE, "team1@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team2", "club1", FEMALE, "team2@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team3", "club2", MALE, "team3@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team4", "club2", MALE, "team4@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team5", "club3", FEMALE, "team5@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team6", "club3", FEMALE, "team6@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team7", "club4", VETERAN, "team7@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//{"Team8", "club4", VETERAN, "team8@mail.com", new Integer(2), new Integer(2), new Integer(2), 10, 2};
//    };
    

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
