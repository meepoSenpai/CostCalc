package com.the_coon.costcalc.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** Class that manages a single debt calculation.
 * Holds information about participants of a debt calculation.
 * Created by ian on 9/14/16.
 */
public class ExpenseGroup{


    private static AtomicInteger idCounter = new AtomicInteger();

    private final int ID;
    private final Date dateOfCreation;

   // private Map<Person, Float> participants;
    private Map<Integer, Float>participants;
    private String name;

    private long timestamp;

    public ExpenseGroup(String name){
        this.name = name;
        this.participants = new HashMap<>();
        this.ID = idCounter.getAndIncrement();
        this.dateOfCreation = new Date();
        this.timestamp = System.currentTimeMillis();
    }


    // METHODS & FUNCTIONS
    // ---------------------------------------------------------------------------------------------

    public void setExpenseForPerson(int ID, float expense)
            throws IllegalArgumentException, NullPointerException{
        if(expense <= 0){
            throw new IllegalArgumentException("ERROR: EXPENSE IS NEGATIVE OR ZERO");
        }
        if(ID < 0){
            throw new IllegalArgumentException("ERROR: INVALID PERSON ID");
        }
        /*Person person = null;
        for(Person personToFind : participants.values()){
            if(personToFind.getId() == ID){
                person = personToFind;
            }
        }*/

        /*if(person == null){
            throw new NullPointerException("Person with this ID does not exist");
        }
        float currentExpense;
        if(participants.get(person) == null){
            currentExpense = expense;
        }else{
            currentExpense = participants.get(person) + expense;
        }*/


        participants.put(ID, expense);
    }

    /**
     * Adds a person to the list of participants.
     * @param ID id of the person.
     */
    public boolean addPerson(int ID){
        if(ID <= 0) {
            throw new IllegalArgumentException("ERROR: INVALID PERSON ID. WAS " + Integer.toString(ID));
        }
        participants.put(ID, 0f);
        return true;
    }

    /**
     * Adds a person to the list of participants with predetermined debts.
     * @param ID id of the person
     * @param debt debt of the person
     */
    public boolean addPerson(int ID, float debt) {
        if(ID <= 0)
            throw new IllegalArgumentException("ERROR: INVALID PERSON ID. WAS " + Integer.toString(ID));
        participants.put(ID,debt);
        return true;
    }

    /**
     * TODO: implement this.
     * @param ID
     */
    public void removePersonFromExpenseGroup(int ID){

    }
    // ---------------------------------------------------------------------------------------------

    // GETTER & SETTER
    // ---------------------------------------------------------------------------------------------
    public Map<Integer, Float> getParticipants(){
        return participants;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return this.ID;
    }

    @Override
    public String toString() {
        return name;
    }

    public Date getDateOfCreation(){
        return this.dateOfCreation;
    }

    public long getTimestamp() {
        return timestamp;
    }
    // ---------------------------------------------------------------------------------------------

}
