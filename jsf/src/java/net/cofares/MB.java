/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cofares;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author pascalfares
 */
@ManagedBean
@SessionScoped
public class MB {

    private int data;
    
    private String nextPage;

    /**
     * Get the value of nextPage
     *
     * @return the value of nextPage
     */
    public String getNextPage() {
        String res=nextPage;
        nextPage="old";
        return res;
    }

    /**
     * Set the value of nextPage
     *
     * @param nextPage new value of nextPage
     */
    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * Get the value of data
     *
     * @return the value of data
     */
    public int getData() {
        return data;
    }

    /**
     * Set the value of data
     *
     * @param data new value of data
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * Creates a new instance of MB
     */
    public MB() {
        data=10;
        nextPage="new";
    }
    
}
