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

}
