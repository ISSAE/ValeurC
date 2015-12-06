/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcrud;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // TODO code application logic here
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(args[0].getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println(hexString);
        EntityManagerFactory em= Persistence.createEntityManagerFactory("JCrudPU");
        UserJpaController userC = new UserJpaController(em);
        
        try {
            userC.create(new User(args[0],args[0]+"@cofares.net",hexString.toString()));
        } catch (Exception ex) {
            Logger.getLogger(JCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<User> lu = userC.findUserEntities();
        
        for (User u : lu ){
            System.out.println(lu);
                    
        }
    }
    
}
