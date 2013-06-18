/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management.exception;

/**
 *
 * @author avk1
 */
public class IncorrectCompetitionTypeException extends Exception {

    /**
     * Creates a new instance of
     * <code>IncorrectCompetitionTypeException</code> without detail message.
     */
    public IncorrectCompetitionTypeException() {
    }

    /**
     * Constructs an instance of
     * <code>IncorrectCompetitionTypeException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public IncorrectCompetitionTypeException(String msg) {
        super(msg);
    }
}
