package com.the_coon.costcalc.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.the_coon.costcalc.R;
import com.the_coon.costcalc.controllers.ExpenseController;
import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sinthu on 21.09.2016.
 */
public class ExpenseGroupActivity extends AppCompatActivity {

    private final ExpenseController expenseController = ExpenseController.getInstance();
    private ExpenseGroup currentExpenseGroup;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setCurrentExpenseGroup();
        this.setTitle(currentExpenseGroup.getName());
        createParticipantListView();

    }

    private void setCurrentExpenseGroup(){
        Bundle b = getIntent().getExtras();
        if(b.get("key")!= null){
            currentExpenseGroup = expenseController.getExpenseGroupById(b.getInt("key"));
        }
        try{
            Person p = expenseController.createNewPerson("Herbert Hohlbein");
            expenseController.addPersonToExpenseGroup(currentExpenseGroup.getId(), p.getId());
        }catch(NullPointerException e){

        }
    }

    private void createParticipantListView(){
        if(currentExpenseGroup != null) {
            ArrayList<Person> participants = new ArrayList<>();
            for(Integer personID : currentExpenseGroup.getParticipants().keySet()) {
                participants.add(expenseController.getPersonByID(personID));
            }

            final ArrayAdapter<Person> nameAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, participants);
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(nameAdapter);
        }
    }

}
