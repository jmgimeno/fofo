/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao.exception;

/**
 *
 * @author mohamed, Anatoli
 */
public class AlreadyExistingRefereeException extends Exception {

    /**
     * Creates a new instance of
     * <code>AlreadyExistingRefereeException</code> without detail message.
     */
    public AlreadyExistingRefereeException() {
    }

    /**
     * Constructs an instance of
     * <code>AlreadyExistingRefereeException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistingRefereeException(String msg) {
        super(msg);
    }
}
