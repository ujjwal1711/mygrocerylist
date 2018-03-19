package com.example.grocery.mygrocerylist.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.grocery.mygrocerylist.Data.Databasehandler;
import com.example.grocery.mygrocerylist.Model.Grocery;
import com.example.grocery.mygrocerylist.R;
import com.example.grocery.mygrocerylist.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listitems;
    private Databasehandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        db=new Databasehandler(this);
        recyclerView =(RecyclerView) findViewById(R.id.recyclerviewerID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryList=new ArrayList<>();
        listitems=new ArrayList<>();
        groceryList=db.GetallGrocery();
        for(Grocery c: groceryList) {
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Qty :"+c.getQuantity());
            grocery.setId(c.getId());
             grocery.setDateitemadded("Item added on :"+c.getDateitemadded());
             listitems.add(grocery);
            }
            recyclerViewAdapter = new RecyclerViewAdapter(this,listitems);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
        }

}
