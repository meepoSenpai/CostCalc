package com.the_coon.costcalc.controllers;

import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

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
        return null;
    }

    public boolean addCalculation(String name){
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

}
