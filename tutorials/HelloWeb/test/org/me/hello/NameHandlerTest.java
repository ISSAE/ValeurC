/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.hello;

import net.cofares.NameHandler;
import junit.framework.TestCase;

/**
 *
 * @author sang
 */
public class NameHandlerTest extends TestCase {
    
    public NameHandlerTest(String testName) {
        super(testName);
    }            

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getName method, of class NameHandler.
     */
    public void testGetName() {
        System.out.println("getName");
        NameHandler instance = new NameHandler();
        String expResult = "";
        String result = instance.getName();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class NameHandler.
     */
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        NameHandler instance = new NameHandler();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addNumbers method, of class NameHandler.
     */
    public void testAddNumbers() {
        System.out.println("addNumbers");
        int x = 5;
        int y = 7;
        NameHandler instance = new NameHandler();
        int expResult = 12;
        int result = instance.addNumbers(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
