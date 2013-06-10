package org.fofo.services.management;

import org.fofo.dao.*;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public class CalendarCalculatorService {
    
    private CalendarDAO calDao;
    private CalendarGen calLeagueGen;
    private CalendarGen calCupGen;
    
    /**
     *
     */
    public CalendarCalculatorService(){

    }
    
    /**
     *
     * @param calDao
     */
    public void setCalendarDao(CalendarDAO calDao){
        this.calDao = calDao;
    }
    
    /**
     *
     * @param cal
     */
    public void setCalendarLeagueGen(CalendarGen cal){
        this.calLeagueGen = cal;
    }
    
    /**
     *
     * @param cal
     */
    public void setCalendarCupGen(CalendarGen cal){
        this.calCupGen = cal;
    }   
    
    /**
     *
     * @return
     */
    public CalendarDAO getCalendarDao(){
        return calDao;
    }
    
    /**
     *
     * @param comp
     * @throws Exception
     */
    public void calculateAndStoreLeagueCalendar(Competition comp) throws Exception{
        if(calDao==null) throw new InvalidRequisitsException();
        if(calLeagueGen==null) throw new InvalidRequisitsException();           
        FCalendar cal = calLeagueGen.calculateCalendar(comp);

        calDao.addCalendar(cal);
    }
    
    /**
     *
     * @param comp
     * @throws Exception
     */
    public void calculateAndStoreCupCalendar(Competition comp) throws Exception{       
        if(calDao==null) throw new InvalidRequisitsException();      
        if(calCupGen==null) throw new InvalidRequisitsException();  
        
//CalendarCupGen calCupGen = new CalendarCupGen();
        
        FCalendar cal = calCupGen.calculateCalendar(comp);

        calDao.addCalendar(cal);
    }
    
}
