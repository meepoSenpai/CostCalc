package com.the_coon.costcalc.models;

import java.util.HashMap;
import java.util.Map;

/** Class that manages a single debt calculation.
 * Holds information about participants of a debt calculation.
 * Created by ian on 9/14/16.
 */
public class Calculation {


    private Map<Person, Float> participants;
    private String name;

    public Calculation (String name){
        this.name = name;
        this.participants = new HashMap<>();
    }


    // METHODS & FUNCTIONS
    // ---------------------------------------------------------------------------------------------

    public boolean addExpense(Person person, float expense)
            throws IllegalArgumentException, NullPointerException{
        if(expense <= 0){
            throw new IllegalArgumentException("Expense is negative or equals zero");
        }
        if(person == null){
            throw new NullPointerException("The referenced Person is null");
        }
        if(participants.containsKey(person)){
            float currentExpense;
            if(participants.get(person) == null){
                currentExpense = expense;
            }else{
                currentExpense = participants.get(person) + expense;
            }
            participants.put(person, currentExpense);
            return true;
        }else{
            return false;
        }
    }

    public boolean addPerson(String name){
        if(name != null){
            Person newPerson = new Person(name);
            this.participants.put(newPerson, 0f);
            return true;
        }else{
            return false;
        }
    }


    // GETTER & SETTER
    // ---------------------------------------------------------------------------------------------
    public Map<Person, Float> getParticipants(){
        return participants;
    }

    public String getName(){
        return name;
    }

}
