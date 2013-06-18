/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.presentation.view;

import java.text.SimpleDateFormat;
import javax.swing.*;
import org.fofo.presentation.controller.SubmitClubInsertionAction;
import org.fofo.presentation.controller.SubmitCompetitionInsertionAction;
import org.fofo.services.management.ManagementService;

/**
 *
 * @author imt1
 */
public class CompetitionInsertionForm extends JPanel{

    public JTextField txtName;
    public JButton but;
    public JRadioButton button1;
    public JRadioButton button2;
    public JRadioButton button3;
    public JRadioButton button4;
    public JRadioButton button5;
    public JComboBox days, months, years, boxMin, boxMax;
    public JFrame parent;
    private ManagementService services;
    
    public CompetitionInsertionForm(JFrame frame, ManagementService services){
    

      this.services = services;  
      this.parent = frame;
      Object[] objLliga = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
      Object[] objCopa = {2, 4, 8, 16, 32, 64};
      Object[] obj = {};
      Object[] objDays = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
      11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
      24, 25, 26, 27, 28, 29, 30, 31};
      Object[] objMonths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
      11, 12};
      Object[] objYears = {2013, 2012, 2011, 2010, 2009, 2008, 2007, 2006};
        
      JLabel lname=new JLabel();
      lname.setText(" Competition name :");
      txtName=new JTextField();
      txtName.setText("Competition name");
      
      //3 opcions per Category
      button1 = new JRadioButton("Masculi");
      button2 = new JRadioButton("Femeni");
      button3 = new JRadioButton("Vetera");
      //1 Date inici, fer-ho amb COMBOBOX
      /*JFormattedTextField date = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
      date.setValue("  /  /    ");*/
      days = new JComboBox(objDays);
      months = new JComboBox(objMonths);
      years = new JComboBox(objYears);
      //2 opcions per CompetitionType
      button4 = new JRadioButton("Lliga");
      button5 = new JRadioButton("Copa");
      
      if(button4.isSelected()){
          obj = objLliga;
      }else if (button5.isSelected()){
          obj = objCopa;
      }
      
      boxMin = new JComboBox(objLliga);
      boxMax = new JComboBox(objLliga);
      
      but=new JButton();
      but.setText("Submit");
      this.add(lname);
      this.add(txtName);
      this.add(button1);
      this.add(button2);
      this.add(button3);
      this.add(days);
      this.add(months);
      this.add(years);
      this.add(button4);
      this.add(button5);
      this.add(boxMin);
      this.add(boxMax);
      this.add(but);
      
      SubmitCompetitionInsertionAction action = new SubmitCompetitionInsertionAction(this);
      action.setServices(services);
      
      but.addActionListener(action);
        //The SubmitClubInsertionAction controller action is associated to
        //the "Submit" button. 
 

    }
    
    public void setServices(ManagementService services){
        this.services = services;
    }
            
            
    
      
}
