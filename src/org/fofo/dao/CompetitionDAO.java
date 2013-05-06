package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author RogerTorra
 */
public interface CompetitionDAO {

    void addCompetition(Competition competition);

    void removeCompetition(String name);

    void addTeam(Competition competition, Team team);

    List<Competition> getCompetitionms();

    Competition findCompetitionByName(String name);

    List<Competition> findCompetitionByTeam(String name);
}