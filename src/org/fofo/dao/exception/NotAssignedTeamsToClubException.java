/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao.exception;

/**
 *
 * @author mohamed
 */
public class NotAssignedTeamsToClubException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotAssignedTeamsToClubException</code> without detail message.
     */
    public NotAssignedTeamsToClubException() {
    }

    /**
     * Constructs an instance of
     * <code>NotAssignedTeamsToClubException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NotAssignedTeamsToClubException(String msg) {
        super(msg);
    }
}
