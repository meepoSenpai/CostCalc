package com.the_coon.costcalc.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.the_coon.costcalc.R;
import com.the_coon.costcalc.controllers.CalculationController;
import com.the_coon.costcalc.dialogs.NewExpenseGroupDialog;
import com.the_coon.costcalc.models.ExpenseGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    // Inflating main menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.add_new_expenses:
                NewExpenseGroupDialog dialog = new NewExpenseGroupDialog();
                dialog.show(getFragmentManager(),"test");
                break;
            default:
                return super.onOptionsItemSelected(menuItem);

        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final CalculationController calculationController = CalculationController.getInstance();
        calculationController.getExpenseGroups().clear();

        for(int i = 1; i < 11; i++) {
            calculationController.createExpenseGroup("Ausgaben " + i +":  "  + new Date().toString());
            android.os.SystemClock.sleep(100);
        }
        ArrayList<ExpenseGroup> expenseGroupData = new ArrayList<>();

        for (ExpenseGroup g : calculationController.getExpenseGroups()) {
            Log.d("TIMESTAMP", Long.toString(g.getTimestamp()));
            expenseGroupData.add(g);
        }

        Collections.sort(expenseGroupData, new Comparator<ExpenseGroup>() {
            @Override
            public int compare(ExpenseGroup lhs, ExpenseGroup rhs) {
                return Long.compare(lhs.getTimestamp(),rhs.getTimestamp());
            }
        });
        ArrayAdapter<ExpenseGroup> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseGroupData);

        ListView view = (ListView) findViewById(R.id.listView);
        view.setAdapter(nameAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                NewExpenseGroupDialog dialog = new NewExpenseGroupDialog();
                dialog.show(getFragmentManager(),"haha");
            }
        });
    }

}
