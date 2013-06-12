package org.fofo.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.fofo.presentation.view.*;
import org.fofo.services.management.*;
import org.fofo.dao.*;

public class Main{

    public static void main(String[] args) throws Exception{
        
        System.out.println("hello world");
 
        ManagementService services = getManagementServiceInstance();
            //We get an instance of ManagementService in order to be able to call
            //the operations of the business layer.
        
        FofoFrame fofo = new FofoFrame(services);
        

        
    }

    private static ManagementService getManagementServiceInstance() throws Exception{
        ClubDAOImpl clubdao = new ClubDAOImpl();
        CompetitionDAOImpl cdao = new CompetitionDAOImpl();
        TeamDAOImpl teamdao = new TeamDAOImpl();
   
        EntityManager em = getEntityManager();
      
        cdao.setEM(em);
        clubdao.setEM(em);
        teamdao.setEM(em);
        
        
        ManagementService services = new ManagementService();
        
        services.setcDao(cdao);
        services.setClubDao(clubdao);
        services.setTeamDao(teamdao);
     
        return services; 
        
    }
    
    private static EntityManager getEntityManager() throws Exception {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("fofo");

        return emf.createEntityManager();
    }

    
    
}