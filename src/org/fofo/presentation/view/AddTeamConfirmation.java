package org.fofo.presentation.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class AddTeamConfirmation extends JPanel{
    
    public AddTeamConfirmation(){
     JLabel l=new JLabel();
      l.setText(" Club successfully inserted");
      this.add(l);
      this.setVisible(true);
    }
}
