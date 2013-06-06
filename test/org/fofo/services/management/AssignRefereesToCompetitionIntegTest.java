package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.dao.*;
import org.fofo.entity.*;
import org.jmock.Expectations;
import org.joda.time.DateTime;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Jordi Niubó i Oriol Capell
 */
public class AssignRefereesToCompetitionIntegTest {
    AssignReferees  service;
    
    EntityManager em = null;
    
    ClubDAOImpl clubdao = null;
    TeamDAOImpl tdao = null;    
    CalendarDAOImpl calendarDao = null;    
    CompetitionDAOImpl compdao = null;
    RefereeDAOImpl refereeDao = null;
    MatchDAOImpl matchDao = null;
    
    Competition compLeague;      
    Club club;
    List<Team> teams;
    
    public AssignRefereesToCompetitionIntegTest() {
    }

    @Before
    public void setUp() throws Exception {
        service = new AssignReferees();  
        em = getEntityManagerFact(); 
        
        tdao = new TeamDAOImpl();   
        tdao.setEM(em);
        
        calendarDao = new CalendarDAOImpl();    
        calendarDao.setEm(em);
        
        refereeDao = new RefereeDAOImpl();
        refereeDao.setEM(em);
        createFiveReferees("Mr");
        
        compdao = new CompetitionDAOImpl(); 
        compdao.setEM(em);

        clubdao = new ClubDAOImpl(); 
        clubdao.setEM(em);    
        
        matchDao = new MatchDAOImpl(); 
        matchDao.setEm(em);        
        
        compLeague = Competition.create(CompetitionType.LEAGUE);
        compLeague.setName("Competition League"); 
                
        createClub();
        createTeams();
        createCompetition(compLeague);                  
        createCalendarForCompetition(compLeague);
    }

    @After
    public void tearDown() throws Exception{  
        if (em.isOpen()) em.close();
        
        em = getEntityManagerFact();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Team");       
        Query query2=em.createQuery("DELETE FROM Competition");     
        Query query3=em.createQuery("DELETE FROM FCalendar");     
        Query query4=em.createQuery("DELETE FROM WeekMatch");     
        Query query5=em.createQuery("DELETE FROM Match");        
        Query query6=em.createQuery("DELETE FROM Club");
        Query query7=em.createQuery("DELETE FROM Referee");  
         
        int deleteRecords=query.executeUpdate();      //Delete Team
        deleteRecords=query2.executeUpdate();          //Delete Competition
        deleteRecords=query3.executeUpdate();          //Delete FCalendar
        deleteRecords=query4.executeUpdate();          //Delete WeekMatch
        deleteRecords=query5.executeUpdate();           //Delete Match       
        deleteRecords=query6.executeUpdate();           //Delete Club          
        deleteRecords=query7.executeUpdate();           //Delete Referee     
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");       
     
    }

    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoDAO() throws Exception {
        service.assignRefereesToCompetition(compLeague);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoRefereeDAO() throws Exception {
        service.setCalendarDao(calendarDao);
        service.assignRefereesToCompetition(compLeague);
    }    
    
    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoCalendarDAO() throws Exception {
        service.setRefereeDao(refereeDao);
        service.assignRefereesToCompetition(compLeague);
    }
    
    @Test(expected=InvalidRequisitsException.class)
    public void testAssignRefereesToCompetitionWithNoMatchDAO() throws Exception {
        service.setMatchDao(matchDao);
        service.assignRefereesToCompetition(compLeague);
    }
    
    @Test(expected= CompetitionWithoutFCalendarException.class)
    public void testAssignRefereesToCompetitionWithNoFCalendar() throws Exception{    
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        
        Competition comp = Competition.create(CompetitionType.LEAGUE);
        comp.setName("Competition Test"); 
        createCompetition(comp);  
        
        service.assignRefereesToCompetition(comp);
    }    
    
    
    @Test(expected= IncorrectFCalendarException.class)
    public void testAssignRefereesToCompetitionWithFCalendarWithNoWeekMatch() throws Exception{  
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        
        FCalendar calendar = new FCalendar();
        calendarDao.addCalendar(calendar);
        
        Competition comp = Competition.create(CompetitionType.LEAGUE);
        comp.setName("Competition Test"); 
        createCompetition(comp);  
        comp.setFcalendar(calendar);
        
        service.assignRefereesToCompetition(comp);
    }     
    
    @Test(expected= PersistException.class)
    public void testAssignRefereesInWeekMatchWithNoMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
           
        FCalendar calendar = new FCalendar();
        calendarDao.addCalendar(calendar);
        WeekMatch weekMatch = new WeekMatch();
        calendar.getAllWeekMatches().add(weekMatch);
        
        Competition comp = Competition.create(CompetitionType.LEAGUE);
        comp.setName("Competition Test"); 
        createCompetition(comp);  
        comp.setFcalendar(calendar);
        
        service.assignRefereesToCompetition(comp);
    }    
    
    
    @Test(expected= InsuficientRefereesException.class)
    public void testInsuficientRefereesFor1WeekMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);

        service.assignRefereesToCompetition(compLeague);
    } 
    
    
    //@Test
    public void testAllMatchHaveReferee() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        createFiveReferees("Referee");
        
        service.assignRefereesToCompetition(compLeague);
        
        Competition comp = getCompFromDB("Competition League");
        assertTrue(allWeekMatchHaveReferee(comp.getFCalendar()));
    }   
    
    //@Test
    public void testRefereeOnlyParticipateInOneMatchInEachWeekMatch() throws Exception{
        service.setMatchDao(matchDao);
        service.setRefereeDao(refereeDao);    
        service.setCalendarDao(calendarDao);
        createFiveReferees("Referee");
        
        service.assignRefereesToCompetition(compLeague);
        Competition comp = getCompFromDB("Competition League");
        assertTrue(refereeOnlyParticipateInOneMatchInEachWeekMatch(comp.getFCalendar()));
    }      
      
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
      
    private EntityManager getEntityManagerFact() throws Exception{

     try{
         EntityManagerFactory emf = 
                 Persistence.createEntityManagerFactory("fofo");
         return emf.createEntityManager();  
     }
     catch(Exception e){
         System.out.println("ERROR CREATING ENTITY MANAGER FACTORY");
	 throw e;
     }

    }
    
    private void createCompetition(Competition comp) throws Exception{   
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        comp.setInici(new DateTime().minusDays(8).toDate()); 
        comp.setTeams(teams);           
        compdao.addCompetition(comp);
    }

    private void createFiveReferees(String name) throws Exception {
        Referee referee;
        for(int i=0; i<5; i++){
            referee = new Referee(name+" NIF 00000"+i,name +" "+i);
            refereeDao.addReferee(referee);
        }
    }

    private void createClub() throws Exception {    
        club = new Club();
        club.setName("Imaginary club");
        club.setEmail("email@email.com");        
        clubdao.addClub(club);
    }

    private void createTeams() throws Exception {      
        teams = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            Team team = new Team("Team number "+i,club, Category.MALE);       
            teams.add(team);
            tdao.addTeam(team);
        }
    }

    private void createCalendarForCompetition(Competition comp) throws Exception {
        CalendarCalculatorService calc = new CalendarCalculatorService();
        calc.setCalendarDao(calendarDao);
        CalendarGen gen = new CalendarLeagueGen();
        calc.setCalendarLeagueGen(gen);
        calc.calculateAndStoreLeagueCalendar(comp);
    }
    
    private Competition getCompFromDB(String name) throws Exception{
        EntityManager em2 = getEntityManagerFact();
        em2.getTransaction().begin();
        Competition compDB = em2.find(Competition.class, name);
        em2.getTransaction().commit();
        em2.close();
        return compDB;
    }
    
    private boolean allWeekMatchHaveReferee(FCalendar fCalendar) throws Exception{
        boolean result = false;
        List<WeekMatch> listWeekMatch = fCalendar.getAllWeekMatches();
        for(WeekMatch wm : listWeekMatch){
            result = result && weekMatchHaveReferee(wm);
        }
        return result;
    }

    private boolean weekMatchHaveReferee(WeekMatch wm) {
        boolean result = false;
        List<Match> listMatch = wm.getMatchs();
        for(Match match : listMatch){
            result = result && MatchHaveReferee(match);
        }
        return result;
    }

    private boolean MatchHaveReferee(Match match) {
        Referee referee = null;
        referee = match.getReferee();
        if(referee == null) return false;
        return true;
    }
    
    
    private boolean refereeOnlyParticipateInOneMatchInEachWeekMatch(FCalendar fCalendar) throws Exception{
        boolean result = false;
        List<WeekMatch> listWeekMatch = fCalendar.getAllWeekMatches();
        for(WeekMatch wm : listWeekMatch){
            result = result && refereeOnlyParticipateInOneMatch(wm);
        }
        return result;
    }

    private boolean refereeOnlyParticipateInOneMatch(WeekMatch wm) {
        List<Match> listMatch = wm.getMatchs();
        List<Referee> listReferee = new ArrayList<Referee>();
        
        for(Match match : listMatch){            
           listReferee.add(match.getReferee());
        }
        
        return listMatch.size() == listReferee.size();
    }

}
