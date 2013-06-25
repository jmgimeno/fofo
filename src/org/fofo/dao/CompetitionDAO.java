package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import java.util.List;
import org.fofo.entity.ClassificationTC;
import org.fofo.entity.Competition;
import org.fofo.entity.Team;

/**
 *
 * @author RogerTorra, Jordi Niubo i Oriol Capell
 */
public interface CompetitionDAO {

    /**
     *
     * @param competition
     * @throws PersistException
     */
    void addCompetition(Competition competition) throws PersistException;

    /**
     *
     * @param name
     * @throws Exception
     */
    void removeCompetition(String name) throws Exception;

    /**
     *
     * @param competition
     * @param team
     * @throws Exception
     */
    void addTeam(Competition competition, Team team) throws Exception;

    /**
     *
     * @param teamName
     * @param compName
     * @throws Exception
     */
  
    void addClassificationTC(String teamName, String compName) throws Exception;

    
    /**
     *
     * @return
     */
    List<Competition> getCompetitionms();

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    Competition findCompetitionByName(String name) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    List<Competition> findCompetitionByTeam(String name) throws Exception;
    
    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    List<ClassificationTC> findClassificationsTC(String name) throws Exception;

    /**
     *
     * @param teamName
     * @param compName
     * @param npoints
     * @throws Exception
     */
    void addPointsToClassificationTC(String teamName, String compName, int npoints)
                             throws Exception;
  
    
}