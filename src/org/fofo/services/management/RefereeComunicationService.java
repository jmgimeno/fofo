 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.io.IOException;
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
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.MatchDAO;
import org.fofo.dao.RefereeDAO;
import org.fofo.entity.*;
import org.fofo.utils.InfoMatch;
import org.fofo.utils.MailDescriptor;

/**
 *
 * @author imt1
 */
public class RefereeComunicationService {
    
    List<Referee> referees;
    RefereeDAO refDAO;
    MatchDAO matchDAO;
    CompetitionDAO compDAO;
    
    public RefereeComunicationService() {
        this.referees = new ArrayList<Referee>();
    }
    
    public List<Referee> getReferees() {
        return referees;
    }
    
    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }
    
    public MatchDAO getMatchDAO() {
        return matchDAO;
    }
    
    public void setMatchDAO(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }
    
    public RefereeDAO getRefDAO() {
        return refDAO;
    }
    
    public void setRefDAO(RefereeDAO refDAO) {
        this.refDAO = refDAO;
    }

    public CompetitionDAO getCompDAO() {
        return compDAO;
    }

    public void setCompDAO(CompetitionDAO compDAO) {
        this.compDAO = compDAO;
    }
    
    /**
     * 
     * @param nif
     * @param idMatch
     * @param im
     * @throws Exception 
     */
    public void communicateResultMatch(String nif, String idMatch,
            InfoMatch im)
            throws Exception {
        
        Referee ref = refDAO.findRefereeByNif(nif);
        Match match = matchDAO.findMatchById(idMatch);
        Competition comp = compDAO.findCompetitionByName(im.getIdCompetition());
        Match match2 = null;
        if(ref==null) throw new InvalidRefereeException();
        if (comp==null) throw new InvalidCompetitionException();
        if (match == null) {
            throw new InvalidMatchException();
        }
        for (Match m : ref.getMatches()) {
            if (m.getIdMatch().equals(idMatch)) {
                match2 = m;
            }
        }
        if (match2 == null) {
            throw new InvalidMatchException();
        }
        if(im.getMatchDate().before(comp.getInici())){
            throw new MatchOutOfPeriodException();
        }
        match.setMatchDate(im.getMatchDate());
        match.setPlace(im.getPlace());
        match.setGoalsHome(im.getGoalsHome());
        match.setGoalsVisiting(im.getGoalsVisiting());
        match.setObservations(im.getObservations());
    }
    /**
     * funasda
     * @param comp asda
     * @throws IOException  asdas
     */
    
    public void communicateRefereesMatchesComp(Competition comp) throws IOException {
        
        List <String> emailReferee = new ArrayList<String>();
        
                FCalendar cal = comp.getFCalendar();
        List<WeekMatch> wms = cal.getAllWeekMatches();
        for (WeekMatch wm : wms) {
            List<Match> ms = wm.getMatchs();
            for (Match m : ms) {
                Referee ref = m.getReferee();
                referees.add(ref);
                emailReferee.add(ref.getEmail());
                ref.getMatches().add(m);
            }
        }
        
        // Creamos un objeto MailDescriptor
        
        MailDescriptor mailDescriptor = new MailDescriptor(emailReferee,"Subject", "Message");
      
        EmailServices.sendEmail(mailDescriptor);
       
    }
}
