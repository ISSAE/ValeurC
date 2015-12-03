/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcrud;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jcrud.control.UserJpaController;

/**
 *
 * @author Pascal Fares <pfares@cofares.net>
 */
public class JCrud {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory em= Persistence.createEntityManagerFactory("JCrudPU");
        UserJpaController userC = new UserJpaController(em);
        
        try {
            userC.create(new User("pf","pfares@cofares.net","pf"));
        } catch (Exception ex) {
            Logger.getLogger(JCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<User> lu = userC.findUserEntities();
        
        for (User u : lu ){
            System.out.println(lu);
                    
        }
    }
    
}
