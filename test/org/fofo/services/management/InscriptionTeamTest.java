package org.fofo.services.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.PersistException;
import org.fofo.entity.Category;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.fofo.entity.Type;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

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
      
      Cdao  = context.mock(CompetitionDAO.class); //Fem mock de una classe amb interface
      
      service.setcDao(Cdao);
      
      team = new Team();
      team1 = new Team();
      team2 = new Team();
      team3 = new Team();
      team4 = new Team();
      team5 = new Team();

      
      
      Comp = Competition.create(Type.CUP);
      
      Comp.setCategory(Category.FEMALE);
      Comp.setInici(new Date()); //Conte la data actual
      Comp.setMaxTeams(4);
      Comp.setMinTeams(2);
      Comp.setName("Lleida");
      
      List<Team> teams = new ArrayList();
      teams.add(team);
      teams.add(team1);

      Comp.setTeams(teams);
      Comp.setType(Type.CUP);
    }
    
    //@Test(expected=IncorrectDate.class)
    public void correctAddTeamToCategory() throws Exception{

        service.addCompetition(Comp);
        
        
         team2.setCategory(null);
         service.addTeam(Comp, team2);    
    }
    
    @Test(expected = InscriptionTeamException.class)
    public void periodNotOpen() throws InscriptionTeamException, Exception {

      Calendar cal = Calendar.getInstance();
      cal.set(2013, Calendar.MAY, 11);
      
      Comp.setInici(cal.getTime());
      service.addTeam(Comp, team);
    }

    //@Test(expected = InscriptionTeamException.class)
    public void notTeamSpace() throws Exception {
        //El maxim d'equips es 4
        service.addTeam(Comp, team1);
        service.addTeam(Comp, team2);
        service.addTeam(Comp, team3);
        service.addTeam(Comp, team4);
        service.addTeam(Comp, team5);    
    }

   //@Test
    public void correctAddTeamToList() throws Exception {
        
        service.addTeam(Comp, team);
        
        
        context.checking(new Expectations() {{
            
            oneOf (Cdao).addCompetition(Comp);
            oneOf(Cdao).getCompetitionms(); will(returnValue(new ArrayList<Competition>(){{ //Esprem que la crida a la funció getCompetitionms, ens retorni un array amb les competicions.
                add(Comp);}}
                ));
        }});
                
        assertTrue(Comp.getTeams().contains(team));
    }
    
    
    //@Test
    public void TeamInsertedIntoTheDB() throws Exception {  
        
        context.checking(new Expectations() {{
            
            oneOf (Cdao).addTeam(Comp, team);   
        }});

        service.addTeam(Comp, team);        
    }    
}
