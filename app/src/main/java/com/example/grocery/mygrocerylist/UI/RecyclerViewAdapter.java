package com.example.grocery.mygrocerylist.UI;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocery.mygrocerylist.Activities.DetailActivity;
import com.example.grocery.mygrocerylist.Data.Databasehandler;
import com.example.grocery.mygrocerylist.Model.Grocery;
import com.example.grocery.mygrocerylist.R;

import java.util.List;

/**
 * Created by UjjwalNUtsav on 08-03-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
     private Context context;
    private AlertDialog.Builder alertdialogbuilder;
    private AlertDialog alertDialog;
    private List<Grocery> groceryItems;
    private  LayoutInflater inflator;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
     Grocery grocery = groceryItems.get(position);
        holder.groceryitemname.setText(grocery.getName());
        holder.quantity.setText(grocery.getQuantity());
        holder.dateadded.setText(grocery.getDateitemadded());
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView groceryitemname;
        public TextView quantity;
        public TextView dateadded;
        public Button editbutton;
        public Button deletebutton;
        public int id;

        public ViewHolder(View view,Context ctx ) {
            super(view);
            context = ctx;
            groceryitemname=(TextView) view.findViewById(R.id.name);
            quantity=(TextView) view.findViewById(R.id.quantity);
            dateadded = (TextView) view.findViewById(R.id.dateadded);
            editbutton = (Button) view.findViewById(R.id.editbutton);
            deletebutton = (Button) view.findViewById(R.id.deletebutton);

            editbutton.setOnClickListener(this);
            deletebutton.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 int position = getAdapterPosition();
                    Grocery grocery = groceryItems.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("name",grocery.getName());
                    intent.putExtra("quantity",grocery.getQuantity());
                    intent.putExtra("id",grocery.getId());
                    intent.putExtra("date",grocery.getDateitemadded());
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.editbutton:
                    int position = getAdapterPosition();
                    Grocery grocery= groceryItems.get(position);
                    editItem(grocery);
                    break;
                case R.id.deletebutton:
                    position = getAdapterPosition();
                    grocery= groceryItems.get(position);
                    deleteitam(grocery.getId());
                    break;
            }

        }
        public void deleteitam(final int id)
        {
          alertdialogbuilder = new AlertDialog.Builder(context);
            inflator = LayoutInflater.from(context);
            View view= inflator.inflate(R.layout.confirmtion_dialog,null);
            Button nobutton=(Button) view.findViewById(R.id.nobutton);
            Button yesbutton=(Button) view.findViewById(R.id.yesbutton);
            alertdialogbuilder.setView(view);
            alertDialog=alertdialogbuilder.create();
            alertDialog.show();
            nobutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                alertDialog.dismiss();
                }
            });
            yesbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Databasehandler db = new Databasehandler(context);
                    db.DeleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();

                }
            });
        }
        public void editItem(final Grocery grocery) {

            alertdialogbuilder = new AlertDialog.Builder(context);

            inflator = LayoutInflater.from(context);
            final View view = inflator.inflate(R.layout.popup, null);

            final EditText groceryItem = (EditText) view.findViewById(R.id.groceryItem);
            final EditText quantity = (EditText) view.findViewById(R.id.Groceryqty);
            //inal TextView title = (TextView) view.findViewById(R.id);

            //title.setText("Edit Grocery");
            Button saveButton = (Button) view.findViewById(R.id.saveButton);


            alertdialogbuilder.setView(view);
            alertDialog = alertdialogbuilder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Databasehandler db = new Databasehandler(context);

                    //Update item
                    grocery.setName(groceryItem.getText().toString());
                    grocery.setQuantity(quantity.getText().toString());

                    if (!groceryItem.getText().toString().isEmpty()
                            && !quantity.getText().toString().isEmpty()) {
                        db.UpdateGrocery(grocery);
                        notifyItemChanged(getAdapterPosition(),grocery);
                    }else {
                        Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                    }

                    alertDialog.dismiss();

                }
            });

        }

    }
}
