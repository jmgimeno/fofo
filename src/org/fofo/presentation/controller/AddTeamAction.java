package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.fofo.presentation.view.AddTeamForm;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class AddTeamAction implements ActionListener {

    JFrame parent;
    ManagementService services;

    public AddTeamAction(JFrame frame, ManagementService services) {
        parent = frame;
        this.services = services;
    }

    public void actionPerformed(ActionEvent e) {


        parent.getContentPane().removeAll();

        AddTeamForm form = new AddTeamForm(parent, services);
//        parent.getContentPane().add(form, BorderLayout.CENTER);

        parent.revalidate();
    }
}