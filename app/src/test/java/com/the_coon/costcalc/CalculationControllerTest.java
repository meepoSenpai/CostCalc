package com.the_coon.costcalc;

import com.the_coon.costcalc.controllers.CalculationController;
import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

<<<<<<< e14d2a1e74f3430a9b2f8d17876352e96b782fc3
=======
import java.util.LinkedList;
import java.util.List;
>>>>>>> Completet Calculationcontroller and added tests for it
import java.util.Map;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CalculationControllerTest {

    private CalculationController calcCont;

    @Before
    public void setUp(){
        calcCont = CalculationController.getTestInstance();
        calcCont.addCalculation("one");
    }

    @After
    public void tearDown(){
        calcCont = null;
    }

    @Test
    public void addCalculationTest(){
        assertTrue(calcCont.addCalculation("Calculation Two"));
        assertTrue(calcCont.addCalculation("Calculation Three"));
        assertTrue(calcCont.addCalculation("Calculation Four"));
        assertTrue(calcCont.addCalculation("Calculation Five"));
        assertEquals(calcCont.getExpenseGroups().size(), 5);
    }

    @Test
    public void singletonTest(){
        assertTrue(CalculationController.getInstance() == CalculationController.getInstance());
    }

    @Test
    public void addPersonToExpenseGroupTest(){
        ExpenseGroup expG = null;
        for(ExpenseGroup eg : calcCont.getExpenseGroups()){
            expG = eg;
            break;
        }
        assertTrue(calcCont.addPersonToCalculation(expG.getId(), "Walter"));
        assertEquals(expG.getParticipants().keySet().size(), 1);
    }

    @Test
    public void addExpenseToCalculationTest(){
        ExpenseGroup expG = null;
        for(ExpenseGroup eg : calcCont.getExpenseGroups()){
            expG = eg;
            break;
        }
        calcCont.addPersonToCalculation(expG.getId(), "Walter");
        calcCont.addPersonToCalculation(expG.getId(), "Walter2");
        for(Person p : expG.getParticipants().keySet()){
            calcCont.addExpenseToCalculation(p.getId(), expG.getId(), expG.getId());
        }
        float price = expG.getId();
        for(Person p : expG.getParticipants().keySet()){
            assertEquals(Float.valueOf(expG.getParticipants().get(p)), Float.valueOf(price));
        }
        Map<Person, Map<Person, Float>> calculation = calcCont.calculateExpense(expG);
        for(Person key1 : calculation.keySet()){
            Map<Person, Float> toCheck = calculation.get(key1);
            for(Person key2 : toCheck.keySet()){
                assertEquals(Float.valueOf(toCheck.get(key2)), Float.valueOf(0));
            }

        }
    }
    @Test
    public void expenseCalculationTest(){
        ExpenseGroup expG = null;
        for(ExpenseGroup eg : calcCont.getExpenseGroups()){
            expG = eg;
            break;
        }
        List<Person> participantList = new LinkedList<>();
        for(int i = 0; i < 4; i++){
            calcCont.addPersonToCalculation(expG.getId(), "" + i * i);
        }
        participantList.addAll(expG.getParticipants().keySet());
        for(Person p : participantList){
            calcCont.addExpenseToCalculation(p.getId(), expG.getId(), Float.valueOf(p.getName()) + 1);
        }
        Map<Person, Map<Person, Float>> result = calcCont.calculateExpense(expG);
        float totalGross = 0;
        for(Person p : result.keySet()){
            for(Person p2 : result.get(p).keySet()){
                totalGross += result.get(p).get(p2);
            }
        }
        assertTrue(totalGross < 0.005);
    }

}