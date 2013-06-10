/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.fofo.dao.MatchDAO;
import org.fofo.dao.RefereeDAO;
import org.fofo.entity.*;
import org.fofo.utils.InfoMatch;

/**
 *
 * @author imt1
 */
public class RefereeComunicationService {

    List<Referee> referees;
    RefereeDAO refDAO;
    MatchDAO matchDAO;
    
    /**
     *
     */
    public RefereeComunicationService(){
        this.referees = new ArrayList<Referee>();
    }

    /**
     *
     * @return
     */
    public List<Referee> getReferees() {
        return referees;
    }

    /**
     *
     * @param referees
     */
    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }

    /**
     *
     * @return
     */
    public MatchDAO getMatchDAO() {
        return matchDAO;
    }

    /**
     *
     * @param matchDAO
     */
    public void setMatchDAO(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    /**
     *
     * @return
     */
    public RefereeDAO getRefDAO() {
        return refDAO;
    }

    /**
     *
     * @param refDAO
     */
    public void setRefDAO(RefereeDAO refDAO) {
        this.refDAO = refDAO;
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
        Match match2 = null;
        if (ref == null || match == null) {
            throw new InvalidMatchException();
        }
        for (Match m : ref.getMatches()){
            if (m.getIdMatch().equals(idMatch)) match2 = m;
        }
        if (match2 == null) throw new InvalidMatchException();
        //match.setInfo(im);
        //if(match.) //Comprovar que un partit ha finalitzat
        //****MALAMENT. MATCH HA DE CANVIAR L'ATRIBUT DE TIPUS INFOMATCH PER 
        //DIVERSOS ATRIBUTS AMB LA INFORMACIO PERTINENT (GOLS LOCAL, GOLS VISITANT...)
        

    }

    /**
     *
     * @param comp
     */
    public void communicateRefereesMatchesComp(Competition comp) {
        FCalendar cal = comp.getFCalendar();
        List<WeekMatch> wms = cal.getAllWeekMatches();
        for (WeekMatch wm : wms) {
            List<Match> ms = wm.getMatchs();
            for (Match m : ms) {
                Referee ref = m.getReferee();
                referees.add(ref);
                ref.getMatches().add(m);
            }
        }
        //sendEmail(referees);
        //Es necessita un email valid
    }
    
    private void sendEmail(List<Referee> refs){
        for (Referee r : refs){
            sendEmail(r);
        }
    }

    private void sendEmail(Referee r) {
        String servidorSMTP = "smtp.gmail.com";
        String port = "587";
        String user = "fofo@gmail.com";
        String password = "pass";

        String destination = r.getEmail();
        String issue = "Issue!";
        String mensaje = "This is a test message.";

        Properties props = new Properties();

        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    destination));
            message.setSubject(issue);
            message.setSentDate(new Date());
            message.setText(mensaje);

            Transport tr = session.getTransport("smtp");
            tr.connect(servidorSMTP, user, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
