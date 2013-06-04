/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

/**
 *
 * @author avk1
 */
public class NotAssignedMatchToRefereeException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotAssignedMatchToRefereeException</code> without detail message.
     */
    public NotAssignedMatchToRefereeException() {
    }

    /**
     * Constructs an instance of
     * <code>NotAssignedMatchToRefereeException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NotAssignedMatchToRefereeException(String msg) {
        super(msg);
    }
}
