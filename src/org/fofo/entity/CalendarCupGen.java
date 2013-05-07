package org.fofo.entity;

import java.util.List;

/**
 *
 * @author jnp2
 */
class CalendarCupGen implements CalendarGen{   
    private Competition competition;

    CalendarCupGen(Competition competition) {
        this.competition = competition;
    }

    @Override
    public FCalendar CalculateCalendar(Competition comp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

    
    
}
