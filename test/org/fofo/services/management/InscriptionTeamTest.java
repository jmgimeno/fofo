package org.fofo.services.management;

import java.util.Calendar;
import org.fofo.dao.CompetitionDAO;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */

@RunWith(JMock.class)
public class InscriptionTeamTest {
    
    
    Mockery context = new JUnit4Mockery();

    ManagementService service;
    
    private CompetitionDAO Cdao;
    private Competition Comp;
    private Team team;
    private Team team1;
    private Team team2;
    private Team team3;
    private Team team4;
    private Team team5;
    
    
    @Before
    public void setup(){
    
      service = new ManagementService();  
     
      Cdao  = context.mock(CompetitionDAO.class);
      Comp = context.mock(Competition.class);
      team = context.mock(Team.class);
           
      Comp = new Competition();
    
             
    }
    

    //@Test(expected = InscriptionTeamException.class)
    public void competitionNotExist() throws InscriptionTeamException {
        //Not yet implemented
    }

    @Test(expected = InscriptionTeamException.class)
    public void periodNotOpen() throws InscriptionTeamException {

      Calendar cal = Calendar.getInstance();
      cal.set(2013, Calendar.MAY, 8);
      Comp.setInici(cal.getTime());
            
      service.addTeam(Comp, team);
    }

    @Test(expected = InscriptionTeamException.class)
    public void notTeamSpace() throws InscriptionTeamException {
        Comp.setMaxTeams(4);
        service.addTeam(Comp, team1);
        service.addTeam(Comp, team2);
        service.addTeam(Comp, team3);
        service.addTeam(Comp, team4);
        service.addTeam(Comp, team5);    
    }

    @Test
    public void correctAddTeamToList() throws InscriptionTeamException {
        service.addTeam(Comp, team);
        assertTrue(Comp.getTeams().contains(team));
    }
    
    @Test
    public void TeamInsertedIntoTheDB() throws Exception {  
        
        context.checking(new Expectations() {{
            
            oneOf (Cdao).addTeam(Comp, team);   
        }});

        service.addTeam(Comp, team);        
    }    
}
