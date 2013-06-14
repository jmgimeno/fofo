/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

/**
 *
 * @author mohamed
 */
public class AlreadyExistingPlayerException extends Exception {

    /**
     * Creates a new instance of
     * <code>AlreadyExistingPlayerException</code> without detail message.
     */
    public AlreadyExistingPlayerException() {
    }

    /**
     * Constructs an instance of
     * <code>AlreadyExistingPlayerException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistingPlayerException(String msg) {
        super(msg);
    }
}
