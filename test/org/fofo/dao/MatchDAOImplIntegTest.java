package org.fofo.dao;

import org.fofo.entity.Category;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
import org.fofo.entity.Team;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class MatchDAOImplIntegTest {

    private MatchDAOImpl matchdao;
    private RefereeDAO refereedao;
    private Match match;
    private Referee referee;
    private Team team1, team2;

    @Before
    public void setUp() throws Exception {

        referee = new Referee("12345678A", "Pepito");

        team1 = new Team("Team1", Category.FEMALE);
        team2 = new Team("Team2", Category.FEMALE);

        match = new Match(team1, team2);

        matchdao = new MatchDAOImpl();
        matchdao.setRefereedb(refereedao);
    }

//addRefereeToMatch TEST    
    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectMatchId() throws Exception {

        matchdao.addRefereeToMatch("AAA", referee.getNif());
    }

    @Test(expected = PersistException.class)
    public void addRefereeToMatch_IncorrectRefereeNif() throws Exception {
        matchdao.addRefereeToMatch(match.getIdMatch(), "11111111A");
    }

    //@Test
    public void addRefereeToMatch_correct() throws Exception {
        matchdao.addRefereeToMatch(match.getIdMatch(), referee.getNif());
    }

//findMatchById TEST    
    //@Test(expected = PersistException.class)
    public void findMatch_IncorrectId() throws Exception {
        matchdao.findMatchById("AAA");
    }

    //@Test
    public void findMatch_CorrectId() throws Exception {
        matchdao.findMatchById(match.getIdMatch());
    }
}