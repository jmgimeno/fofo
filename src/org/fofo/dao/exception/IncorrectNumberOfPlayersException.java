/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao.exception;

/**
 *
 * @author mohamed
 */
public class IncorrectNumberOfPlayersException extends Exception {

    /**
     * Creates a new instance of
     * <code>IncorrectNumberOfPlayersException</code> without detail message.
     */
    public IncorrectNumberOfPlayersException() {
    }

    /**
     * Constructs an instance of
     * <code>IncorrectNumberOfPlayersException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public IncorrectNumberOfPlayersException(String msg) {
        super(msg);
    }
}
