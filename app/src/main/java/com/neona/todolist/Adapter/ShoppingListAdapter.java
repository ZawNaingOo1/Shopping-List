package com.neona.todolist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neona.todolist.Data.DatabaseHelper;
import com.neona.todolist.Data.ShoppingData;
import com.neona.todolist.MainActivity;
import com.neona.todolist.OnNewItemAddedListener;
import com.neona.todolist.R;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingData>{

    private Context context;
    private int resource;
    private List<ShoppingData> shoppingDataList;

    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
    private OnNewItemAddedListener onNewItemAddedListener;

    public ShoppingListAdapter(@NonNull Context context, int resource, @NonNull List<ShoppingData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.shoppingDataList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null);

        final TextView textViewName = view.findViewById(R.id.txt_name);
        //TextView textViewPrice = view.findViewById(R.id.txt_price);
        final CheckBox checkBox = view.findViewById(R.id.cb_select);
        final ImageView rowDelete = view.findViewById(R.id.row_delete);

        final ShoppingData shoppingData = shoppingDataList.get(position);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    textViewName.setTextColor(Color.LTGRAY);
                    databaseHelper.updateIsBought(shoppingData.getName().toString(), 1);
                }else {
                    textViewName.setTextColor(Color.BLACK);
                    databaseHelper.updateIsBought(shoppingData.getName(), 0);
                }
            }
        });

        // delete listener
        rowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteRow(shoppingData.getName());
                onNewItemAddedListener = (OnNewItemAddedListener) context;
                onNewItemAddedListener.onNewItemAdded(shoppingData.getName());

                Toast.makeText(context,shoppingData.getName() + " is deleted" ,Toast.LENGTH_SHORT).show();
            }
        });

        if(shoppingData.isBought() == 0){

            textViewName.setTextColor(Color.BLACK);

        }else {
            checkBox.setChecked(true);
            textViewName.setTextColor(Color.LTGRAY);

        }
        textViewName.setText(shoppingData.getName());
        //textViewPrice.setText(String.valueOf(shoppingData.getPrice()));

        return view;
    }

}
