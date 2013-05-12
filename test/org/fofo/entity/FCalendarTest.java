/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.joda.time.*;
import static org.junit.Assert.*;

/**
 *
 * @author ??
 */


public class FCalendarTest {
    
    private FCalendar calendar;
    private Competition compOK;     
    
    public FCalendarTest() {
    }
    
    @Before
    public void setUp() throws Exception { 
        setUpCompOK();        
         
        
    }    
    
    
    
    

    private void setUpCompOK() {
        compOK = Competition.create(Type.LEAGUE);
        compOK.setCategory(Category.MALE);
        compOK.setMinTeams(2);
        compOK.setMaxTeams(20);
        compOK.setInici(new DateTime().minusDays(8).toDate());
        List<Team> teams = new ArrayList<Team>();
        for(int i=1; i<=10; i++)
            teams.add(new Team("Team "+i,Category.MALE));
        compOK.setTeams(teams);
    }
    
    
    
    
}