/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

/**
 *
 * @author josepma
 */
public class IncorrectCompetitionData extends Exception {

    /**
     * Creates a new instance of
     * <code>IncorrectCompetitionData</code> without detail message.
     */
    public IncorrectCompetitionData() {
    }

    /**
     * Constructs an instance of
     * <code>IncorrectCompetitionData</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectCompetitionData(String msg) {
        super(msg);
    }
}
