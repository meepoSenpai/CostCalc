package com.the_coon.costcalc.controllers;

import com.the_coon.costcalc.models.Calculation;
import com.the_coon.costcalc.models.Person;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ian on 9/14/16.
 */
public class CalculationController {

    private Set<Calculation> calculations = new HashSet<>();
    private Calculation currentCalculation = null;
    private static CalculationController instance = null;

    private CalculationController(){

    }

    public static CalculationController getInstance(){
        if(instance == null){
            instance = new CalculationController();
        }
        return instance;
    }

    public void addExpenseToCalculation(Person person, Calculation calc, float price){

    }

    public boolean addPersonToCalculation(Calculation calc, String name){
        return false;
    }

    public Map<Person, Map<Person, Float>> calculateExpense(Calculation calc){
        return null;
    }

    public boolean addCalculation(String name){
        return false;
    }

}
