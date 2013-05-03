package org.fofo.services.management;

import java.util.Date;
import org.fofo.dao.CompetitionDAO;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class InscriptionTeam {
    CompetitionDAO competitionDAO;
    Competition competition;
    Team team;

    /**
     * Add a team in a competition.
     * @param competetion The competition we want to register a team.
     * @param team The team we want to register.
     * @throws InscriptionTeamException 
     */
    public void addTeam(CompetitionDAO competitionDAO, Competition competetion, Team team) throws InscriptionTeamException {
        if (CompetitionExist(competitionDAO, competition) && PeriodOpen(competition) && TeamsSpace(competition)) {
            competition.addTeam(team);
        } else {
            throw new InscriptionTeamException();
        }
    }

    /**
     * Check if the period of inscription is valid for a competition.
     * @param competition: The competition to be checked.
     * @return Boolean indicate if it is open or not.
     */
    private boolean PeriodOpen(Competition competition) {
        Date d = new Date();
        d.getDay();
        return (competition.getInici().compareTo(d) < 0);
    }

    /**
     * Check if we have reached the maximum number of teams in a competition.
     * @param competition: The competition to be checked.
     * @return Boolean indicate if it is full or not.
     */
    private boolean TeamsSpace(Competition competition) {
        return (competition.getMaxTeams() >= competition.getSize());
    }

    /**
     * Check if competition exists in DAO
     * @param competitionDAO 
     * @param competition: Competition that we check.
     * @return Boolean indicate if the competition exist.
     */
    private boolean CompetitionExist(CompetitionDAO competitionDAO, Competition competition) {
       return competitionDAO.findCompetitionByName(competition);
    }
}