/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.Date;

/**
 *
 * @author jnp2
 */
public class CalendarCupTest {
    CalendarCup calendar;
    
    public CalendarCupTest() {
       

        
    }



    //@Test(expected=InvalidRequisitsException.class)
    public void testDateException() throws InvalidRequisitsException {
        Date init = new Date();
        calendar = new CalendarCup(null);        
    }
    
    //@Test(expected=InvalidRequisitsException.class)    
    public void testMinTeamsException() throws InvalidRequisitsException {
        calendar = new CalendarCup(null);        
    }   
    
    //@Test(expected=InvalidRequisitsException.class)    
    public void testMaxTeamsException() throws InvalidRequisitsException {
        calendar = new CalendarCup(null);        
    }      
  
   
    
}
