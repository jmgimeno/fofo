/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.utils;

import org.fofo.entity.Category;
import org.fofo.entity.Competition;
import org.fofo.entity.CompetitionType;
import org.fofo.entity.Team;
import org.fofo.utils.InfoClassTeam;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Jordi Niubo i Oriol Capell
 */
public class ClassificationTest {
    Team team1, team2;
    InfoClassTeam classTeam1, classTeam2;
    Classification classification1;
    Competition comp;
    
    public ClassificationTest() {
    }

    @Before
    public void setUp() throws Exception {
        team1 = new Team("Team 1", Category.MALE);
        team2 = new Team("Team 2", Category.FEMALE);
        classTeam1 = new InfoClassTeam(team1);
        classTeam2 = new InfoClassTeam(team2);        
        
        comp = Competition.create(CompetitionType.LEAGUE);
        comp.setName("Competition League"); 
        
        classification1 = new Classification(comp);
        classification1.getInfoClassTeam().add(classTeam1);
        classification1.getInfoClassTeam().add(classTeam2);
    }

    @Test
    public void testCorrectEqualsWith0Points() {
        InfoClassTeam classTeam = new InfoClassTeam(team1);
        assertTrue(classTeam.equals(classTeam1));        
    }
        
    @Test
    public void testIncorrectEqualsWith0Points() {
        InfoClassTeam classTeam2 = new InfoClassTeam(team2);
        assertFalse(classTeam2.equals(classTeam1));        
    }
    
    @Test
    public void testCorrectEqualsWithPoints() {
        InfoClassTeam classTeam = new InfoClassTeam(team1);
        classTeam1.setPoints(10);
        classTeam.setPoints(10);
        assertTrue(classTeam.equals(classTeam1));        
    }    
    
    @Test
    public void testIncorrectEqualsWithPoints() {
        InfoClassTeam classTeam = new InfoClassTeam(team1);
        classTeam1.setPoints(10);
        assertFalse(classTeam.equals(classTeam1));        
    }    
    
    @Test
    public void testCorrectEqualsDiferentsInfoClassTeam() {
        assertFalse(classTeam2.equals(classTeam1));        
    }
    
    @Test
    public void testIncorrectEqualsWithDiferentsPoints() {
        InfoClassTeam classTeam = new InfoClassTeam(team1);
        classTeam1.setPoints(10);  
        classTeam.setPoints(20);
        assertFalse(classTeam.equals(classTeam1));        
    }
    
    @Test
    public void testCorrectInitialitationPoints() {
        assertEquals(classTeam1.getPoints(), 0);        
    }     
    
    @Test
    public void testCorrectInitialSetPoints() {
        classTeam1.setPoints(10);
        assertEquals(classTeam1.getPoints(), 10);         
    }  
    
    @Test
    public void testCorrectSetPoints() {
        classTeam1.setPoints(10);
        classTeam1.setPoints(12);        
        assertEquals(classTeam1.getPoints(), 12);         
    }   
    
    
    @Test
    public void testEqualsCompetition() {
        Classification classification = new Classification(comp);
        classification.getInfoClassTeam().add(classTeam1);
        classification.getInfoClassTeam().add(classTeam2); 
        
        assertEquals(classification, classification1);         
    } 
    
    @Test
    public void testEqualsDiferentsCompetition() {
        Classification classification = new Classification(comp);
        classification.getInfoClassTeam().add(classTeam1);
        
        assertNotSame(classification, classification1);         
    }         
    
    
    @Test
    public void testCompetitionWithDifetentsPoints() {
        Classification classification = new Classification(comp);
        classTeam1.setPoints(10);
        classification.getInfoClassTeam().add(classTeam1);
        classification.getInfoClassTeam().add(classTeam2); 
        
        assertNotSame(classification, classification1);         
    }    
    
    
    
}
