package com.the_coon.costcalc.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** Class that manages a single debt calculation.
 * Holds information about participants of a debt calculation.
 * Created by ian on 9/14/16.
 */
public class ExpenseGroup {


    private static AtomicInteger idCounter = new AtomicInteger();

    private final int ID;

    private Map<Person, Float> participants;
    private String name;

    public ExpenseGroup(String name){
        this.name = name;
        this.participants = new HashMap<>();
        this.ID = idCounter.getAndIncrement();
    }


    // METHODS & FUNCTIONS
    // ---------------------------------------------------------------------------------------------

    public void addExpense(int personId, float expense)
            throws IllegalArgumentException, NullPointerException{
        if(expense <= 0){
            throw new IllegalArgumentException("Expense is negative or equals zero");
        }
        if(personId < 0){
            throw new IllegalArgumentException("PersonID can't be lower than 0");
        }
        Person person = null;
        for(Person personToFind : participants.keySet()){
            if(personToFind.getId() == personId){
                person = personToFind;
            }
        }
        if(person == null){
            throw new NullPointerException("Person with this ID does not exist");
        }
        float currentExpense;
        if(participants.get(person) == null){
            currentExpense = expense;
        }else{
            currentExpense = participants.get(person) + expense;
        }
        participants.put(person, currentExpense);
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
    // ---------------------------------------------------------------------------------------------

    // GETTER & SETTER
    // ---------------------------------------------------------------------------------------------
    public Map<Person, Float> getParticipants(){
        return participants;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return this.ID;
    }
    // ---------------------------------------------------------------------------------------------

}
