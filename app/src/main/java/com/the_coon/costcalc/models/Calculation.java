package com.the_coon.costcalc.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 9/14/16.
 */
public class Calculation {

    private Map<Person, Float> participants;
    private String name;

    public Calculation (String name){
        this.name = name;
        this.participants = new HashMap<Person, Float>();
    }

    public boolean addExpense(Person person, float expense) throws IllegalArgumentException, NullPointerException{
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
        return false;
    }

    public Map<Person, Float> getParticipants(){
        return participants;
    }

}
