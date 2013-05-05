/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

/**
 *
 * @author avk1
 */
public class MinimumDaysException extends Exception {

    /**
     * Creates a new instance of
     * <code>MinimumDaysException</code> without detail message.
     */
    public MinimumDaysException() {
    }

    /**
     * Constructs an instance of
     * <code>MinimumDaysException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MinimumDaysException(String msg) {
        super(msg);
    }
}
