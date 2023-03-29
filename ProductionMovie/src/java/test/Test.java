/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Time;

/**
 *
 * @author Ari
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long time=-10800000;
        Time t=new Time(time);
        //t=new Time(0, 0, 0);
        System.err.println(t.getHours());
        System.err.println(t.toString());
        // TODO code application logic here
    }
    
}
