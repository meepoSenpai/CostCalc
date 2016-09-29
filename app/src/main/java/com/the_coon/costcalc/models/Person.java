package com.the_coon.costcalc.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that holds data about persons. Contains name and unique id.
 * Created by ian on 9/14/16.
 */
public class Person {


    // MEMBERS & INSTANCES
    // ---------------------------------------------------------------------------------------------

    private static AtomicInteger idCounter = new AtomicInteger();

    private final int ID;
    private String name;

    public Person(String name){
        this.ID = idCounter.getAndIncrement();
        this.name = name;

    }
    // ---------------------------------------------------------------------------------------------


    // GETTER & SETTER
    // ---------------------------------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    public int getId(){
        return ID;
    }

    public String toString(){
        return name;
    }
    //----------------------------------------------------------------------------------------------

}
