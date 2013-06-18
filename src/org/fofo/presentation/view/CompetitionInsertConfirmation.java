/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;

/**
 *
 * @author imt1
 */
public class CompetitionInsertConfirmation extends JPanel{
    
    public CompetitionInsertConfirmation(){
        JLabel l=new JLabel();
        l.setText(" Competition successfully inserted");
        this.add(l);
        this.setVisible(true);
    }
    
}
