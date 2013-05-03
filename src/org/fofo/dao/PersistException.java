/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

/**
 *
 * @author josepma
 */
public class PersistException extends Exception {

    /**
     * Creates a new instance of
     * <code>PersistException</code> without detail message.
     */
    public PersistException() {
    }

    /**
     * Constructs an instance of
     * <code>PersistException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PersistException(String msg) {
        super(msg);
    }
}
