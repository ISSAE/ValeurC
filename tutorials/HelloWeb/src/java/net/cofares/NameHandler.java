/*
 * NameHandler.java
 *
 * Created on October 28, 2006, 9:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package net.cofares;

/**
 *
 * @author sang
 */
public class NameHandler {

    private String username;
    private String prenom;
    
    private int toto;

    /**
     * Get the value of toto
     *
     * @return the value of toto
     */
    public int getToto() {
        return toto;
    }

    /**
     * Set the value of toto
     *
     * @param toto new value of toto
     */
    public void setToto(int toto) {
        this.toto = toto;
    }


    /** Creates a new instance of NameHandler */
    public NameHandler() {
        setName(null);
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public int addNumbers(int x, int y) {
        return (x + y);
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}
