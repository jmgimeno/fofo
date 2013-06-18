/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.presentation.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.fofo.entity.Competition;


import org.fofo.presentation.view.CompetitionFindForm;
import org.fofo.presentation.view.CompetitionInfo;
import org.fofo.presentation.view.ExceptionInfo;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author Anatoli Veselinov
 */
public class FindCompetitionAction implements ActionListener{

    CompetitionFindForm compForm;
    ManagementService services;

    public FindCompetitionAction(CompetitionFindForm form){
        compForm = form;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String compName = compForm.txtCompName.getText();
        String teamName = compForm.txtTeamName.getText();
        Competition comp = null;
        try {
            
            
            if(compName!=null){
                comp = services.getcDao().findCompetitionByName(compName);
            }/*else if(teamName != null){
                comp = services.getcDao().findCompetitionByTeam(teamName);
            }*/
                        
    
            CompetitionInfo foundComp = new CompetitionInfo(comp);
            compForm.parent.getContentPane().add(foundComp,BorderLayout.CENTER);
            
            
        } catch (Exception ex) {
            ex.printStackTrace(); 
            ExceptionInfo exception = new ExceptionInfo("Competition not found");
            compForm.parent.getContentPane().add(exception,BorderLayout.SOUTH);            
        }finally{
            compForm.parent.revalidate();
        }
    }
    
    public void setServices(ManagementService serv){
        this.services = serv;
    }

}
