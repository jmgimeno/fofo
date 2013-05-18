package org.fofo.services.management;

import org.fofo.dao.*;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public class CalendarCalculatorService {
    
    CalendarDAO calDao;
    
    public CalendarCalculatorService(){
        this.calDao =null;
    }
    
    public void setCalendarDao(CalendarDAO calDao){
        this.calDao = calDao;
    }
    
    public CalendarDAO getCalendarDao(){
        return calDao;
    }
    
    public void calculateAndStoreLeagueCalendar(Competition comp) throws Exception{
        if(calDao==null) throw new InvalidRequisitsException();
        CalendarLeagueGen calGen = new CalendarLeagueGen();
        FCalendar cal = calGen.calculateCalendar(comp);

        calDao.addCalendar(cal);
    }
    
    public void calculateAndStoreCupCalendar(Competition comp) throws Exception{
        if(calDao==null) throw new InvalidRequisitsException();
        CalendarCupGen calGen = new CalendarCupGen();
        FCalendar cal = calGen.calculateCalendar(comp);

        calDao.addCalendar(cal);
    }    
    
}
