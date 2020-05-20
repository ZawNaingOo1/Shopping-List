package com.neona.todolist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neona.todolist.Data.DatabaseHelper;
import com.neona.todolist.Data.ShoppingData;

import java.util.ArrayList;

public class NewItemFragment extends Fragment {

    int ID;
    String name;
    int price;
    int bought;

    ArrayList<ShoppingData> shoppingDataArrayList = new ArrayList<>();

    private OnNewItemAddedListener onNewItemAddedListener;
    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_item_fragment,container,false);
        final EditText editText = (EditText) view.findViewById(R.id.editText);

        // Database helper
        databaseHelper = new DatabaseHelper(getContext());

        // EditText listener
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                    onNewItemAddedListener.onNewItemAdded("newItem");
                    if( keyCode == KeyEvent.KEYCODE_ENTER){
                        String newItem = editText.getText().toString();
                        if(TextUtils.isEmpty(newItem)){
                            Toast.makeText(getContext(),"Enter an item",Toast.LENGTH_LONG).show();
                        }else {
                            // insert data to database
                            databaseHelper.insertData(newItem, 0,false);
                            onNewItemAddedListener.onNewItemAdded(newItem);

                            editText.setText("");

                            if(shoppingDataArrayList.size() != 0){
                                ShoppingData shoppingData1 = shoppingDataArrayList.get(0);
                                Log.d("SQLite list", shoppingData1.getName());
                            }
                        }

                        return true;
                    }
                return false;
            }
        });

        // enter button
        ImageButton btnEnter = view.findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString();

                if(TextUtils.isEmpty(newItem)){
                    Toast.makeText(getContext(),"Enter an item",Toast.LENGTH_LONG).show();
                }else {
                    // insert data to database
                    databaseHelper.insertData(newItem, 0,false);
                    onNewItemAddedListener.onNewItemAdded(newItem);

                    editText.setText("");

                    if(shoppingDataArrayList.size() != 0){
                        ShoppingData shoppingData1 = shoppingDataArrayList.get(0);
                        Log.d("SQLite list", shoppingData1.getName());
                    }
                }

            }
        });

        setShoppingArrayList();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onNewItemAddedListener = (OnNewItemAddedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnNewItemAddedListener");
        }

    }

    void setShoppingArrayList(){

        Cursor res = databaseHelper.getAllData();

        while (res.moveToNext()){
            ID = res.getInt(0);
            name = res.getString(1);
            price = res.getInt(2);
            bought = res.getInt(3);

            shoppingDataArrayList.add(0,new ShoppingData(ID,name,price,bought));
        }
    }
}
