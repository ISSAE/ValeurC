/*
 * Gestion des utilisateurs par JPA et CRUD
 */
package user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import user.control.UserJpaController;

/**
 *
 * @author Pascal Fares <pfares@cofares.net>
 */
public class User {

   static String sha2hash(String param) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(param.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
        // INIT JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("userPU");

        UserJpaController uc = new UserJpaController(emf);

        user.entity.User u = new user.entity.User("username", "email", "password");

        /*
        try {
            uc.create(u);
        } catch (Exception ex) {
            System.out.println("Il semble y avoir un problème dans create" + ex);
        }

        List<user.entity.User> lu = uc.findUserEntities();

        for (user.entity.User user : lu) {
            System.out.println(user);
        }

        try {
            uc.destroy("username");
        } catch (NonexistentEntityException ex) {
            System.out.println("Il semble y avoir un problème dans destroy" + ex);
        }
*/
        List<user.entity.User> lu = uc.findUserEntities();
        System.out.println("AVANT....");
        for (user.entity.User user : lu) {
            System.out.println(user);
        }
        
        if (args.length < 2) {
            System.out.println("Usage : cru <username> <email> <pass>");
            System.out.println("Usage : dlu <username>");
            return;
        }
        //Au moins 2 paramètres
       if ((args[0].equals("cru")) && (args.length==4)) {
           user.entity.User nu = new user.entity.User(args[1],args[2],sha2hash(args[3]));
           
           uc.create(nu);
       } else if ((args[0].equals("dlu")) && (args.length==2)) {
           uc.destroy(args[1]);
       } else {
           System.out.println("Commande non reconnue ou mauvaise syntaxe...");
           System.out.println("Usage : cru <username> <email> <pass>");
           System.out.println("Usage : dlu <username>");
       }
       System.out.println(".............APRES....");
       lu = uc.findUserEntities();

        for (user.entity.User user : lu) {
            System.out.println(user);
        }
        
    }

}
