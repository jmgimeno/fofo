package org.fofo.services.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.fofo.dao.ClubDAO;
import org.fofo.dao.CompetitionDAO;
import org.fofo.entity.Category;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;
import org.fofo.entity.Type;
import org.joda.time.DateTime;
import org.fofo.dao.PersistException;
import org.fofo.dao.TeamDAO;

/**
 *
 * @author josepma
 */
public class ManagementService {

    private ClubDAO clubDao;
    private CompetitionDAO cDao;
    private Competition competition;
    //private Team team;
    private TeamDAO teamDao;

    public CompetitionDAO getcDao() {
        return cDao;
    }

    public void setcDao(CompetitionDAO cDao) {
        this.cDao = cDao;
    }

    public void setClubDao(ClubDAO clubDao) {
        this.clubDao = clubDao;
    }
    
    public void setTeamDao(TeamDAO teamDao) {
          this.teamDao = teamDao;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

//    public void setTeam(Team team) {
//        this.team = team;
//    }

    public ClubDAO getClubDao() {
        return clubDao;
    }

    public Competition getCompetition() {
        return competition;
    }

//    public Team getTeam() {
//        return team;
//    }
  

    public void addCompetition(Competition comp) throws IncorrectCompetitionData,
            IncorrectTypeData, IncorrectMinNumberOfTeams, IncorrectMaxNumberOfTeams,
            IncorrectDate, PersistException {
        List<Club> clubs = new ArrayList<Club>();
        checkForExceptions(comp);
        clubs = clubDao.getClubs();
//        if(clubs!=null){
//            for (Club c : clubs) {
//                sendEmail(c);
//            }
//        }
        cDao.addCompetition(comp);
        //THIS IS THE OPERATION TO IMPLEMENT.....

    }

    /**
     * Add a team in a competition.
     * @param competetion The competition we want to register a team.
     * @param team The team we want to register.
     * @throws InscriptionTeamException 
     */
    public void addTeam(Competition competetion, Team team) throws Exception{
         
      // checkForExceptions(competition);
        
        if  (team.getName()!=null &&  team.getEmail()!=null && team.getCompetition()!=null &&
              team.getCategory()!=null && /*team.getClub()!=null &&*/
                CompetitionExist(competition) && PeriodOpen(competition) && TeamsSpace(competition)) {
            
            addCompetition(competition);
            
            competition.getTeams().add(team);
            cDao.addTeam(competition, team);
        } else {
            throw new InscriptionTeamException();
        }
    }

    /*
     * 
     * PRIVATE OPERATIONS
     * 
     * 
     * 
     */
    private void sendEmail(Club c) {
         String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "fofo@gmail.com";
        String password = "pass";

        String destino = c.getEmail();
        String asunto = "Prueba!";
        String mensaje = "Este es un mensaje de prueba.";

        Properties props = new Properties();

        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);

        Session session = Session.getInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    destino));
            message.setSubject(asunto);
            message.setSentDate(new Date());
            message.setText(mensaje);

            Transport tr = session.getTransport("smtp");
            tr.connect(servidorSMTP, usuario, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean isPowerOfTwo(int value) {
        return (value != 0) && ((value & (value - 1)) == 0);
    }

    private boolean isValidCategory(Category cat) throws IncorrectCompetitionData{
        if(cat==null) throw new IncorrectCompetitionData();
        if (cat.equals(Category.FEMALE) || cat.equals(Category.MALE)
                || cat.equals(Category.VETERAN)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validMinTeamsInLeague(int teams) {
        if (teams >= 2 && teams % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validMinTeamsInCup(int teams) {
        if (validMinTeamsInLeague(teams) && isPowerOfTwo(teams)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validMaxTeamsInLeague(int teams) {
        if (teams <= 20 && teams % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validMaxTeamsInCup(int teams) {
        if (teams <= 64 && isPowerOfTwo(teams)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidType(Type type) throws IncorrectTypeData{
        if(type==null) throw new IncorrectTypeData();
        if (type.equals(Type.CUP) || type.equals(Type.LEAGUE)) {
            return true;
        } else {
            return false;
        }
    }

    private void checkForExceptions(Competition comp) throws IncorrectCompetitionData,
            IncorrectTypeData, IncorrectMinNumberOfTeams, IncorrectMaxNumberOfTeams,
            IncorrectDate {
        DateTime date = new DateTime();
        date.plusWeeks(2);
        if (!isValidCategory(comp.getCategory())) {
            throw new IncorrectCompetitionData();
        }
        if (!isValidType(comp.getType())) {
            throw new IncorrectTypeData();
        }
        if (comp.getMinTeams() == 0) throw new IncorrectMinNumberOfTeams();
        if (comp.getMaxTeams() == 0) throw new IncorrectMaxNumberOfTeams();
        if (comp.getInici().after(date.toDate())) {
            throw new IncorrectDate();
        }
    }

    /**
     * Check if the period of inscription is valid for a competition.
     * @param competition: The competition to be checked.
     * @return Boolean indicate if it is open or not.
     */
    private boolean PeriodOpen(Competition competition) {
        System.out.println("PERIODE OPEN FUNCTION");
        DateTime currentDate = new DateTime(DateTime.now());
        DateTime finishDate = new DateTime(competition.getInici());
        
        return ((currentDate.getDayOfYear() - finishDate.getDayOfYear()) < 7);
    }

    /**
     * Check if we have reached the maximum number of teams in a competition.
     * @param competition: The competition to be checked.
     * @return Boolean indicate if it is full or not.
     */
    private boolean TeamsSpace(Competition competition) {
        System.out.println("TEAM SPACE FUNCTION");
        return (competition.getMaxTeams() > competition.getTeams().size());
    }

    /**
     * Check if competition exists in DAO
     * @param competitionDAO 
     * @param competition: Competition that we check.
     * @return Boolean indicate if the competition exist.
     */
    private boolean CompetitionExist(Competition competition) {
        System.out.println("COMPETITION EXIST");
        return cDao.getCompetitionms().contains(competition);
    }
}