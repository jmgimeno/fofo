/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.fofo.presentation.view.CompetitionFindForm;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author Anatoli 
 */
public class CompetitionFindAction implements ActionListener{
    ManagementService services;
    JFrame parent;

    public CompetitionFindAction(JFrame frame, ManagementService services) {
        parent = frame;
        this.services = services;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        parent.getContentPane().removeAll();

        CompetitionFindForm form = new CompetitionFindForm(parent, services);

        parent.getContentPane().add(form, BorderLayout.LINE_START);
        parent.revalidate();
    }

}
