package com.the_coon.costcalc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.the_coon.costcalc.R;
import com.the_coon.costcalc.controllers.CalculationController;
import com.the_coon.costcalc.dialogs.NewExpenseGroupDialog;
import com.the_coon.costcalc.models.ExpenseGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    private final CalculationController calculationController = CalculationController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        for(int i = 1; i < 11; i++) {
            calculationController.createExpenseGroup("Ausgaben " + i +":  "  + new Date().toString());
            android.os.SystemClock.sleep(100);
        }

        createExpenseListView(loadExpenseGroupList());
        createExpenseGroupAddButton();
        Bundle b = new Bundle();
    }

    private List<ExpenseGroup> loadExpenseGroupList() {
        List<ExpenseGroup> eGroups = new LinkedList<>();
        eGroups.addAll(calculationController.getExpenseGroups());

        Collections.sort(eGroups, new Comparator<ExpenseGroup>() {
            @Override
            public int compare(ExpenseGroup lhs, ExpenseGroup rhs) {
                return Long.compare(lhs.getTimestamp(),rhs.getTimestamp());
            }
        });
        return eGroups;
    }

    private void createExpenseListView(List<ExpenseGroup> eg){
        final ArrayAdapter<ExpenseGroup> nameAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eg);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(nameAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toExpenseGroupActivity = new Intent(MainActivity.this,
                        ExpenseGroupActivity.class);
                Bundle b = new Bundle();
                b.putInt("expenseGroupID", nameAdapter.getItem(position).getId());
                toExpenseGroupActivity.putExtras(b);
                startActivity(toExpenseGroupActivity);
                finish();
            }
        });
    }

    private void createExpenseGroupAddButton(){
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
