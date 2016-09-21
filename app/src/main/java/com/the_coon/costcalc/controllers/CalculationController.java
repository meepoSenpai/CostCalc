package com.the_coon.costcalc.controllers;

import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ian on 9/14/16.
 */
public class CalculationController {

    private Set<ExpenseGroup> expenseGroups;
    private ExpenseGroup currentExpenseGroup;
    private static CalculationController instance = null;

    private CalculationController(){
        expenseGroups = new HashSet<>();
        currentExpenseGroup = null;
    }

    public static CalculationController getInstance(){
        if(instance == null){
            instance = new CalculationController();
        }
        return instance;
    }

    public static CalculationController getTestInstance(){
        return new CalculationController();
    }

    public void addExpenseToCalculation(int personId, int calcId, float price)
            throws NullPointerException, IllegalArgumentException{
        if(price <= 0){
            throw new IllegalArgumentException("The expense has to be larger than 0");
        }
        if(calcId < 0 || personId < 0){
            throw new IllegalArgumentException("IDs have to be greater or equals to zero");
        }
        ExpenseGroup toModify = null;
        for(ExpenseGroup calc : this.expenseGroups){
            if(calc.getId() == calcId){
                toModify = calc;
                break;
            }
        }
        if(toModify == null){
            throw new NullPointerException("The specified ExpenseGroup does not exist.");
        }

        toModify.addExpense(personId, price);

    }

    public boolean addPersonToCalculation(int expID, String name){
        if(expID < 0){
            throw new IllegalArgumentException("IDs have to be greater or equals to zero");
        }
        ExpenseGroup toModify = null;
        for(ExpenseGroup expGroup : expenseGroups){
            if(expGroup.getId() == expID){
                toModify = expGroup;
                break;
            }
        }
        if(toModify == null){
            return false;
        }
        return toModify.addPerson(name);
    }

    public Map<Person, Map<Person, Float>> calculateExpense(ExpenseGroup calc){
        Map<Person, Float> participants = calc.getParticipants();
        float totalExpense = 0;
        for(Person person : participants.keySet()){
            totalExpense += participants.get(person);
        }
        Map<Person, Map<Person, Float>> result = new HashMap<>();
        Map<Person, Float> absExpense = new HashMap<>();
        for(Person person : participants.keySet()){
            result.put(person, null);
            absExpense.put(person, participants.get(person) - totalExpense / participants.keySet().size());
        }
        float absPositive = 0;
        for(Person p : participants.keySet()){
            if(absExpense.get(p) > 0){
                absPositive += participants.get(p);
            }
        }
        for(Person p : absExpense.keySet()){
            Map<Person, Float> subResult = new HashMap<>();
            for(Person person : absExpense.keySet()){
                if(person == p || absExpense.get(p) == 0 || absExpense.get(person) == 0){
                    continue;
                }else{
                    float price;
                    if(absExpense.get(p) > 0 && absExpense.get(person) < 0){
                        price = absExpense.get(person) * (participants.get(p) / absPositive);
                        subResult.put(person, price);
                    }else if(absExpense.get(p) < 0 && absExpense.get(person) > 0){
                        price = -1 * absExpense.get(p) * (participants.get(person) / absPositive);
                        subResult.put(person, price);
                    }
                }
            }
            result.put(p, subResult);
        }

        return result;
    }

    public boolean createExpenseGroup(String name){
        try{
            ExpenseGroup expGroup = new ExpenseGroup(name);
            return this.expenseGroups.add(expGroup);
        }catch(Exception e){
            return false;
        }
    }


    public void setCurrentExpenseGroup(ExpenseGroup expenseGroup) {
        this.currentExpenseGroup = expenseGroup;
    }

    public Set<ExpenseGroup> getExpenseGroups(){
        return this.expenseGroups;
    }

}
