/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

/**
 *
 * @author avk1
 */
public class IncorrectTeamException extends Exception {

    /**
     * Creates a new instance of
     * <code>IncorrectTeamException</code> without detail message.
     */
    public IncorrectTeamException() {
    }

    /**
     * Constructs an instance of
     * <code>IncorrectTeamException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectTeamException(String msg) {
        super(msg);
    }
}
