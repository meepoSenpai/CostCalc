package com.the_coon.costcalc.controllers;

import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 9/14/16.
 */
public class ExpenseController {

    //private Set<ExpenseGroup> expenseGroups;

    // MEMBERS & INSTANCES
    // ---------------------------------------------------------------------------------------------
    private HashMap<Integer, Person> persons;
    private HashMap<Integer, ExpenseGroup> expenseGroups;
    private static ExpenseController instance;


    // CONSTRUCTOR
    // ---------------------------------------------------------------------------------------------
    private ExpenseController() {

        //expenseGroups = new HashSet<>();
        expenseGroups = new HashMap<>();
        persons = new HashMap<>();
    }


    // METHODS & FUNCTIONS
    // ---------------------------------------------------------------------------------------------
    public static ExpenseController getInstance() {
        if (instance == null) {
            instance = new ExpenseController();
        }
        return instance;
    }

    public static ExpenseController getTestInstance() {
        return new ExpenseController();
    }


    public void addExpenseToExpenseGroup(int personId, int expenseGroupId, float debt)
            throws NullPointerException, IllegalArgumentException {
        if (debt <= 0) {
            throw new IllegalArgumentException("ERROR: THE DEBT CAN'T BE LESS ZERO");
        }
        if (expenseGroupId < 0) {
            throw new IllegalArgumentException("ERROR: INVALID EXPENSE GROUP ID");
        }

        if (personId < 0) {
            throw new IllegalArgumentException("ERROR: INVALID PERSON ID");

        }

        ExpenseGroup toModify = expenseGroups.get(expenseGroupId);
        if (toModify == null) {
            throw new NullPointerException("The specified ExpenseGroup does not exist.");
        }

        toModify.setExpenseForPerson(personId, debt);

    }

    public Person createNewPerson(String name) {
        Person p = new Person(name);
        persons.put(p.getId(), p);

        return p;
    }

    public boolean addPersonToExpenseGroup(int expenseGroupID, int personID) {
        if (expenseGroupID < 0 || !expenseGroups.containsKey(expenseGroupID)) {
            throw new IllegalArgumentException("ERROR: INVALID EXPENSE GROUP ID. WAS " + Integer.toString(expenseGroupID));
        }
        ExpenseGroup toModify = expenseGroups.get(expenseGroupID);
        Person p;
        /**
         * TESTING: If person is already in the database, use it.
         * Otherwise create a new person, here with dummy data.
         * This will be important, if the user wants to select an existing person or create a new one
         * when modifying an expense group.
         */
        if (persons.containsKey(personID))
            p = persons.get(personID);
        else
            p = createNewPerson("Walter Walter");


        return toModify.addPerson(p.getId());
    }

    public Map<Integer, Map<Integer, Float>> calculateExpense(ExpenseGroup calc) {
        Map<Integer, Float> participants = calc.getParticipants();
        float totalExpense = 0;
        for (Integer personID : participants.keySet()) {
            totalExpense += participants.get(personID);
        }
        Map<Integer, Map<Integer, Float>> result = new HashMap<>();
        Map<Integer, Float> absExpense = new HashMap<>();
        for (Integer personID : participants.keySet()) {
            result.put(personID, null);
            absExpense.put(personID, participants.get(personID) - totalExpense / participants.keySet().size());
        }
        float absPositive = 0;
        for (Integer personID : participants.keySet()) {
            if (absExpense.get(personID) > 0) {
                absPositive += participants.get(personID);
            }
        }
        for (Integer p : absExpense.keySet()) {
            Map<Integer, Float> subResult = new HashMap<>();
            for (Integer person : absExpense.keySet()) {
                if (person == p || absExpense.get(p) == 0 || absExpense.get(person) == 0) {
                    continue;
                } else {
                    float price;
                    if (absExpense.get(p) > 0 && absExpense.get(person) < 0) {
                        price = absExpense.get(person) * (participants.get(p) / absPositive);
                        subResult.put(person, price);
                    } else if (absExpense.get(p) < 0 && absExpense.get(person) > 0) {
                        price = -1 * absExpense.get(p) * (participants.get(person) / absPositive);
                        subResult.put(person, price);
                    }
                }
            }
            result.put(p, subResult);
        }

        return result;
    }

    public void createExpenseGroup(String name) {
        ExpenseGroup group = new ExpenseGroup(name);
        expenseGroups.put(group.getId(), group);
    }

    public ExpenseGroup getExpenseGroupById(int id) {
        ExpenseGroup group = expenseGroups.get(id);
        if (group == null)
            throw new NullPointerException("ERROR: THE EXPENSE GROUP WITH THE FOLLOWING ID WAS NOT " +
                    "FOUND." + Integer.toString(id));
        return group;
    }

    public Person getPersonByID(int id) {
        Person person = persons.get(id);
        if (person == null)
            throw new NullPointerException("ERROR: THE PERSON WITH THE FOLLOWING ID WAS NOT " +
                    "FOUND." + Integer.toString(id));
        return person;
    }

    public HashMap<Integer, ExpenseGroup> getExpenseGroups() {
        return expenseGroups;
    }


}
