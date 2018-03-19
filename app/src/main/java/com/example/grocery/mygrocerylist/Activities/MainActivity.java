package com.example.grocery.mygrocerylist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.grocery.mygrocerylist.Data.Databasehandler;
import com.example.grocery.mygrocerylist.Model.Grocery;
import com.example.grocery.mygrocerylist.R;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button savebutton;
    private Databasehandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Databasehandler(this);
        bypassactivity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                createpopupdialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void createpopupdialog(){
      dialogbuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        groceryItem = (EditText) view.findViewById(R.id.groceryItem);
        quantity=(EditText) view.findViewById(R.id.Groceryqty);
        savebutton = (Button) view.findViewById(R.id.saveButton);
        dialogbuilder.setView(view);
        dialog=dialogbuilder.create();
        dialog.show();
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryItem.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty())
                {savegrocerytodb(v);}
            }


        });
    }
    private void savegrocerytodb(View v) {
        Grocery grocery = new Grocery();
        String newgrocery = groceryItem.getText().toString();
        String newgroceryitem = quantity.getText().toString();

        grocery.setName(newgrocery);
        grocery.setQuantity(newgroceryitem);
        db.addGrocery(grocery);
        Log.d("function implemented", "data saved");

        Snackbar.make(v, "Item saved!", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Log.d("handler", "handler implemented");
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
    }

    public void bypassactivity()
    {
        if(db.Getgrocerycount()>0)
        {
            Log.d("bypassactivity","started");

            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }

    }
    }

