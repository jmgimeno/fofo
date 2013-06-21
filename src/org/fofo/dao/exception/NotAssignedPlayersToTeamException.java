/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao.exception;

/**
 *
 * @author mohamed
 */
public class NotAssignedPlayersToTeamException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotAssignedPlayersToTeamException</code> without detail message.
     */
    public NotAssignedPlayersToTeamException() {
    }

    /**
     * Constructs an instance of
     * <code>NotAssignedPlayersToTeamException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NotAssignedPlayersToTeamException(String msg) {
        super(msg);
    }
}
