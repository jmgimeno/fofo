/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.utils;

import java.util.ArrayList;
import java.util.List;
import org.fofo.entity.Referee;

/**
 *
 * @author adr4
 */
public class MailDescriptor {

    List<String> addressee;
    String subject;
    String message;

    public MailDescriptor(List<String> addressee, String subject, String message) {
        this.addressee = new ArrayList<String>();
        this.subject = subject;
        this.message = message;
    }
     
    public List<String> getAddressee() {
        return addressee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "maildescriptor{" + "addressee=" + addressee + ", subject=" + subject + ", message=" + message + '}';
    }
}
