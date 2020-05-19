package com.neona.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.neona.todolist.Adapter.ShoppingListAdapter;
import com.neona.todolist.Data.DatabaseHelper;
import com.neona.todolist.Data.ShoppingData;

import org.rabbitconverter.rabbit.Rabbit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNewItemAddedListener{

    // ArrayList of to do items
    private ArrayList<String> toDoItemsArrayList = new ArrayList<>();

    // setting arrayList
    int ID;
    String name;
    int price;
    int bought;

    DatabaseHelper databaseHelper;

    ShoppingListAdapter shoppingListAdapter;
    ShoppingListFragment shoppingListFragment;

    ArrayList<ShoppingData> shoppingDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayData();

/*        // ZawGyi to Unicode Test
        TextView uniTextView = findViewById(R.id.uni_text);
        String uniText = uniTextView.getText().toString();
        String zgText = Rabbit.uni2zg(uniText);

        TextView zgTextView = findViewById(R.id.zg_text);
        zgTextView.setText(zgText);*/

    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all : alertDialogForDeleteAll();break;
            case R.id.settings : startActivity(new Intent(this, SettingsActivity.class));break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onNewItemAdded(String newItem) {
        toDoItemsArrayList.add(newItem);
        shoppingDataArrayList.clear();
        displayData();
    }

    void setShoppingArrayList(){

        Cursor res = databaseHelper.getAllData();

        while (res.moveToNext()){
            ID = res.getInt(0);
            name = res.getString(1);
            price = res.getInt(2);
            bought = res.getInt(3);

            shoppingDataArrayList.add(new ShoppingData(ID,name,price,bought));
        }
    }

    public void displayData(){
        databaseHelper = new DatabaseHelper(this);

        setShoppingArrayList();

        //Get references to the Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        shoppingListFragment = (ShoppingListFragment) fragmentManager.findFragmentById(R.id.ToDoListFragment);

        shoppingListAdapter = new ShoppingListAdapter(this, R.layout.list_row, shoppingDataArrayList);

        // Bind the Array Adapter to the ListView
        shoppingListFragment.setListAdapter(shoppingListAdapter);
    }

    // alert and clear data
    void alertDialogForDeleteAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete all!");
        builder.setMessage("Are you sure you want to clear all?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.clearTable();
                shoppingDataArrayList.clear();
                displayData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
