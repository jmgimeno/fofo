/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.fofo.entity.Referee;
import org.fofo.utils.MailDescriptor;

/**
 *
 * @author adr4
 */
public  class EmailServices {

    public static void sendEmail(MailDescriptor descriptor) throws IOException {

       

        /*
         * String servidorSMTP = "smtp.gmail.com"; String port = "587"; String
         * user = "fofo@gmail.com"; String password = "pass";
         */
        
         // Lo pillamos todo del properties;

        Properties props = new Properties();
        
        props.load(new FileInputStream("email.properties"));
        String servidorSMTP = props.getProperty("servidorSMTP");
        String port = props.getProperty("port");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        
        List<String> destination_list = descriptor.getAddressee();
        String issue = descriptor.getSubject();
        String mensaje = descriptor.getMessage();



        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, null);

        Iterator iter = destination_list.iterator();

        try {

            while (iter.hasNext()) {

                String destination = (String) iter.next();
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

            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
