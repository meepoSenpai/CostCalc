package com.the_coon.costcalc;

import android.util.Log;

import com.the_coon.costcalc.controllers.ExpenseController;
import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CalculationControllerTest {

    private ExpenseController calcCont;

    @Before
    public void setUp() {
        calcCont = ExpenseController.getTestInstance();
    }

    @Test
    public void addCalculationTest() {
        for (int i = 0; i < 5; i++) {
            calcCont.createExpenseGroup("EXPENSE GROUP " + i);
            System.out.println("EVENT: ADDED EXPENSE GROUP " + i);
            System.out.println(calcCont.getExpenseGroups().size());
            assertTrue(calcCont.getExpenseGroups().size() == (i + 1));
        }

    }

    @Test
    public void singletonTest() {
        assertTrue(ExpenseController.getInstance() == ExpenseController.getInstance());
    }

    @Test
    public void addPersonToExpenseGroupTest() {
        for (ExpenseGroup eg : calcCont.getExpenseGroups().values()) {
            Person p = calcCont.createNewPerson("Walter Lewis");

            assertTrue(calcCont.addPersonToExpenseGroup(eg.getId(), p.getId()));
            assertEquals(eg.getParticipants().keySet().size(), 1);
        }
    }

    @Test
    public void addExpenseToCalculationTest() {
        for (ExpenseGroup e : calcCont.getExpenseGroups().values()) {


            float price = 7.63f;

            Person km = calcCont.createNewPerson("Kevin Magnussen");
            Person jv = calcCont.createNewPerson("Jos Verstappen");

            calcCont.addPersonToExpenseGroup(e.getId(), km.getId());
            calcCont.addPersonToExpenseGroup(e.getId(), jv.getId());


            for (int personID : e.getParticipants().keySet()) {
                calcCont.addExpenseToExpenseGroup(personID, e.getId(), price);
            }

            for (int personID : e.getParticipants().keySet()) {
                assertEquals(Float.valueOf(e.getParticipants().get(personID)), Float.valueOf(price * e.getParticipants().size()));
            }


            Map<Integer, Map<Integer, Float>> calculation = calcCont.calculateExpense(e);
            for (Integer key1 : calculation.keySet()) {
                Map<Integer, Float> toCheck = calculation.get(key1);
                for (Integer key2 : toCheck.keySet()) {
                    assertEquals(Float.valueOf(toCheck.get(key2)), Float.valueOf(0));
                }

            }
        }
    }

    @Test
    public void expenseCalculationTest() {
        for (ExpenseGroup eg : calcCont.getExpenseGroups().values()) {

            List<Integer> participantList = new LinkedList<>();
            for (int i = 0; i < 4; i++) {
                Person p = calcCont.createNewPerson("" + i * i);
                calcCont.addPersonToExpenseGroup(eg.getId(), p.getId());
            }
            participantList.addAll(eg.getParticipants().keySet());
            for (Integer ID : participantList) {
                calcCont.addExpenseToExpenseGroup(ID, eg.getId(), Float.valueOf(calcCont.getPersonByID(ID).getName()) + 1);
            }
            Map<Integer, Map<Integer, Float>> result = calcCont.calculateExpense(eg);
            float totalGross = 0;
            for (Integer p : result.keySet()) {
                for (Integer p2 : result.get(p).keySet()) {
                    totalGross += result.get(p).get(p2);
                }
            }
            assertTrue(totalGross < 0.00005);
        }
    }
}