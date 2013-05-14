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
        CalendarGen calGen = new CalendarLeagueGen(comp);
        FCalendar cal = calGen.calculateCalendar();

        calDao.addCalendar(cal);
    }
    
    public void calculateAndStoreCupCalendar(Competition comp) throws Exception{
        if(calDao==null) throw new InvalidRequisitsException();
        CalendarGen calGen = new CalendarCupGen(comp);
        FCalendar cal = calGen.calculateCalendar();

        calDao.addCalendar(cal);
    }    
    
}
