package net.cofares.facade;

import javax.persistence.EntityManagerFactory;


/**
 *
 * @author Pascal Fares <pfares@cofares.net>
 */

public class ObjectFacade extends AbstractFacade<Object> {

    
    private EntityManagerFactory emf;

    @Override
    protected EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public ObjectFacade() {
        super(Object.class);
    }
    
}
