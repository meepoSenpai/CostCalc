package com.the_coon.costcalc.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.the_coon.costcalc.R;
import com.the_coon.costcalc.controllers.CalculationController;
import com.the_coon.costcalc.models.ExpenseGroup;
import com.the_coon.costcalc.models.Person;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sinthu on 21.09.2016.
 */
public class ExpenseGroupActivity extends AppCompatActivity {

    private final CalculationController calculationController = CalculationController.getInstance();
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
            currentExpenseGroup = calculationController.getExpenseGroupById(b.getInt("key"));
        }
        try{
            calculationController.addPersonToCalculation(currentExpenseGroup.getId(), "Walter");
        }catch(NullPointerException e){

        }
    }

    private void createParticipantListView(){
        if(currentExpenseGroup != null) {
            List<Person> participants = new LinkedList<>();
            participants.addAll(currentExpenseGroup.getParticipants().keySet());
            final ArrayAdapter<Person> nameAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, participants);
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(nameAdapter);
        }
    }

}
