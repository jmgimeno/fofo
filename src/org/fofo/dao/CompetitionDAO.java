package org.fofo.dao;

import java.util.List;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author RogerTorra
 */
public interface CompetitionDAO {

    void addCompetition(Competition competition) throws PersistException;

    void removeCompetition(String name);

    void addTeam(Competition competition, Team team) throws Exception;

    List<Competition> getCompetitionms();

    Competition findCompetitionByName(String name) throws Exception;

    List<Competition> findCompetitionByTeam(String name) throws Exception;
}