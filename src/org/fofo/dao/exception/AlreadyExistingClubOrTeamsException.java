/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao.exception;

/**
 *
 * @author Anatoli
 */
public class AlreadyExistingClubOrTeamsException extends Exception {

    /**
     * Creates a new instance of
     * <code>AlreadyExistingClubOrTeams</code> without detail message.
     */
    public AlreadyExistingClubOrTeamsException() {
    }

    /**
     * Constructs an instance of
     * <code>AlreadyExistingClubOrTeams</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistingClubOrTeamsException(String msg) {
        super(msg);
    }
}
