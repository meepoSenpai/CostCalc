package com.the_coon.costcalc.models;

/**
 * Created by ian on 9/14/16.
 */
public class Person {

    private static int globalId = 0;
    private int id;
    private String name;

    public Person(String name){

    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

}
