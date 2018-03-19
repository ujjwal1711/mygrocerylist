package com.example.grocery.mygrocerylist.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.grocery.mygrocerylist.R;

public class DetailActivity extends AppCompatActivity {
     private TextView itemname;
    private TextView quantity;
    private TextView dateadded;
    private int groceryid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        itemname= (TextView) findViewById(R.id.itemnamedet);
        quantity=(TextView) findViewById(R.id.quantitydet);
        dateadded=(TextView) findViewById(R.id.dateddeddet);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
          itemname.setText(bundle.getString("name"));
          quantity.setText(bundle.getString("quantity"));
            dateadded.setText(bundle.getString("date"));
            groceryid=bundle.getInt("id");

        }
    }
}
