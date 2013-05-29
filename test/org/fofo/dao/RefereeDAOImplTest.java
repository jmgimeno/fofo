/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.fofo.entity.Referee;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 *
 * @author mohamed, Anatoli
 */
@RunWith(JMock.class)
public class RefereeDAOImplTest {

    private Mockery context = new JUnit4Mockery();
    private EntityManager em;
    private EntityTransaction transaction;
    private RefereeDAOImpl rdao;
    private Query query;

    public RefereeDAOImplTest() {
    }

    @Before
    public void setUp() {
        rdao = new RefereeDAOImpl();
        em = context.mock(EntityManager.class);
        rdao.setEM(em);
        transaction = context.mock(EntityTransaction.class);
        query = context.mock(Query.class);
    }

    @Test
    public void testAddReferee() throws Exception {

        final Referee referee = new Referee("NifReferee1", "Referee1");
        transactionExpectations();
        context.checking(new Expectations() {
            {
                oneOf(em).find(Referee.class, referee.getNif());
                will(returnValue(null));
                oneOf(em).persist(referee);
            }
        });

        rdao.addReferee(referee);
    }

    @Test(expected = AlreadyExistingRefereeException.class)
    public void testAlreadyExistReferee() throws Exception {

        final Referee referee = new Referee("NifReferee1", "Referee1");
        transactionExpectations();
        context.checking(new Expectations() {
            {
                oneOf(em).find(Referee.class, referee.getNif());
                will(returnValue(referee));
            }
        });

        rdao.addReferee(referee);
    }
    
    @Test
    public void testFindRefereeByNif() throws Exception {
        final Referee referee = new Referee("NifReferee1", "Referee1");
        transactionExpectations();
        context.checking(new Expectations(){
            {
                oneOf(em).find(Referee.class, referee.getNif());
                will(returnValue(referee));
            }
        });
        
        assertEquals(referee, rdao.findRefereeByNif(referee.getNif()));
    }
    
    @Test
    public void testGetAllReferees() throws Exception {
        final Referee referee = new Referee("NifReferee1", "Referee1");
        final List<Referee> expected = new ArrayList<Referee>();
        expected.add(referee);
        
        transactionExpectations();
        context.checking(new Expectations(){
            {
                oneOf(em).createQuery("SELECT x FROM Referee x");
                will(returnValue(query));
                oneOf(query).getResultList();
                will(returnValue(expected));
            }
        });
        
        assertEquals(expected, rdao.getAllReferees());
    }

    private void transactionExpectations() {
        context.checking(new Expectations() {
            {
                allowing(em).getTransaction();
                will(returnValue(transaction));
                allowing(transaction).begin();
                allowing(transaction).commit();
                allowing(em).isOpen();
                will(returnValue(true));
                allowing(em).close();
            }
        });
    }
}