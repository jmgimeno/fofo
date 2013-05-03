package org.fofo.services.management;

import org.junit.Test;

/**
 *
 * @author David Hernández, Anton Urrea
 */
public class InscriptionTeamTest {

    @Test(expected = InscriptionTeamException.class)
    public void competitionNotExist() {
    }

    @Test(expected = InscriptionTeamException.class)
    public void periodNotOpen() {
    }

    @Test(expected = InscriptionTeamException.class)
    public void notTeamSpace() {
    }

    @Test
    public void correctAddTeam() {
    }
}
