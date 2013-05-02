/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author jnp2
 */
public class CalendarCupTest {
    CalendarCup calendar;
    Competition comp;
    
    public CalendarCupTest() {
        comp = new Competition();
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        comp.setType(Type.CUP);
        List<Team> list = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            list.add(new Team());
        }
        comp.setTeams(list);
        
        int año = 2013; int mes = 4; int dia = 22; //Fecha anterior 
        GregorianCalendar cal = new GregorianCalendar(año, mes-1, dia);
        Date fecha = new java.sql.Date(cal.getTimeInMillis());
        comp.setInici(fecha);
    }



    @Test(expected=InvalidRequisitsException.class)
    public void testDateException() throws InvalidRequisitsException {
        comp.setInici(new Date());
        calendar = new CalendarCup(comp);        
    }
    
    @Test(expected=InvalidRequisitsException.class)    
    public void testMinTeamsException() throws InvalidRequisitsException {
        comp.setMinTeams(20);
        calendar = new CalendarCup(comp);        
    }   
    
    @Test(expected=InvalidRequisitsException.class)    
    public void testMaxTeamsException() throws InvalidRequisitsException{
        comp.setMaxTeams(10);
        calendar = new CalendarCup(comp);        
    }      
    
    @Test   
    public void testNumWeekMatchesException() throws InvalidRequisitsException{
        calendar = new CalendarCup(comp);  
        int nj = calendar.getNumWeekMatches();
        assertEquals(4,nj);            
        }
        
        
    }    
