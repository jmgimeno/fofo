/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management.exception;

/**
 *
 * @author Mohamed
 */
public class UnknownCompetitionTypeException extends Exception {

    /**
     * Creates a new instance of
     * <code>UnknownCompetitionTypeException</code> without detail message.
     */
    public UnknownCompetitionTypeException() {
    }

    /**
     * Constructs an instance of
     * <code>UnknownCompetitionTypeException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public UnknownCompetitionTypeException(String msg) {
        super(msg);
    }
}
