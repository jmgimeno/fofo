package org.fofo.entity;

import java.util.List;

/**
 * @author jnp2
 */
class CalendarLeagueGen extends CalendarGen{
    private Competition competition;

    //Implementa el CalendarLeagueGen aqui Anatoli
    //Modificar els testos primer

    public CalendarLeagueGen(Competition competition) {
        this.competition = competition;  
    }

    public CalendarLeagueGen() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    
    //Implementar algoritme per generar lligues 
    @Override
    public FCalendar CalculateCalendar() throws InvalidRequisitsException, NonUniqueIdException,
                       TeamCanPlayOnlyOneMatchForAWeekException, UnknownCompetitionTypeException{ 

        FCalendar calendar = new FCalendar(competition);   
        return calendar;
    }
 
    
}
