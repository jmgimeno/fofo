package org.fofo.entity;

import java.util.List;

/**
 * @author jnp2
 */
class CalendarLeagueGen implements CalendarGen{
    private Competition competition;

//Implementa el CalendarLeagueGen aqui Anatoli
//Tens tot el test comentat, no tenen que sortir les alarmes !!!

    public CalendarLeagueGen(Competition competition) {
        this.competition = competition;  
    }

    @Override
    public FCalendar CalculateCalendar(Competition comp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

 
    
}
