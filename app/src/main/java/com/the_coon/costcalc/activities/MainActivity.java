package com.the_coon.costcalc.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.the_coon.costcalc.R;
import com.the_coon.costcalc.controllers.CalculationController;
import com.the_coon.costcalc.models.ExpenseGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        CalculationController calculationController = CalculationController.getInstance();

        calculationController.createExpenseGroup("Ausgaben 21.09.2016");
        calculationController.createExpenseGroup("Ausgaben 22.09.2016");


        ArrayList<ExpenseGroup> expenseGroupData = new ArrayList<>();

        for(ExpenseGroup g : calculationController.getExpenseGroups()) {
            expenseGroupData.add(g);
        }
        ArrayAdapter<ExpenseGroup> nameAdapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseGroupData);

        Collections.sort(expenseGroupData, new Comparator<ExpenseGroup>() {
            @Override
            public int compare(ExpenseGroup lhs, ExpenseGroup rhs) {
                
            }
        });

        ListView view = (ListView) findViewById(R.id.listView);
        view.setAdapter(nameAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
