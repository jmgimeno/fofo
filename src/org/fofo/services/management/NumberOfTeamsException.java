/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

/**
 *
 * @author avk1
 */
public class NumberOfTeamsException extends Exception {

    /**
     * Creates a new instance of
     * <code>NumberOfTeamException</code> without detail message.
     */
    public NumberOfTeamsException() {
    }

    /**
     * Constructs an instance of
     * <code>NumberOfTeamException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NumberOfTeamsException(String msg) {
        super(msg);
    }
}
