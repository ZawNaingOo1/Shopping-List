package com.neona.todolist.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.neona.todolist.R
import com.neona.todolist.database.DatabaseHelper
import com.neona.todolist.database.ShoppingData
import com.neona.todolist.main_screen.OnNewItemAddedListener

class ShoppingListAdapter(context: Context, private val resource: Int, private val shoppingDataList: List<ShoppingData>) : ArrayAdapter<ShoppingData?>(context, resource, shoppingDataList) {

    var databaseHelper = DatabaseHelper(getContext())
    private var onNewItemAddedListener: OnNewItemAddedListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(resource, null)
        val textViewName = view.findViewById<TextView>(R.id.txt_name)
        //TextView textViewPrice = view.findViewById(R.id.txt_price);
        val checkBox = view.findViewById<CheckBox>(R.id.cb_select)
        val rowDelete = view.findViewById<ImageView>(R.id.row_delete)
        val shoppingData = shoppingDataList[position]

        onNewItemAddedListener = context as OnNewItemAddedListener
        checkBox.setOnClickListener {
            onClickItemCheckbox(checkBox, textViewName, shoppingData)
        }

        // delete listener
        rowDelete.setOnClickListener {
            deleteItem(shoppingData)
        }

        // Active or inactive item color
        changeItemColor(shoppingData, checkBox, textViewName)

        textViewName.text = shoppingData.name

        //textViewPrice.setText(String.valueOf(shoppingData.getPrice()));
        return view
    }

    private fun onClickItemCheckbox(checkBox: CheckBox, textViewName: TextView, shoppingData: ShoppingData) {
        if (checkBox.isChecked) {
            textViewName.setTextColor(Color.LTGRAY)
            databaseHelper.updateIsBought(shoppingData.name, 1)
            onNewItemAddedListener!!.onNewItemAdded(shoppingData.name)
            Toast.makeText(context, shoppingData.name + " is checked", Toast.LENGTH_LONG).show()
        } else {
            textViewName.setTextColor(Color.BLACK)
            databaseHelper.updateIsBought(shoppingData.name, 0)
            onNewItemAddedListener!!.onNewItemAdded(shoppingData.name)
            Toast.makeText(context, shoppingData.name + " is unchecked", Toast.LENGTH_LONG).show()
        }
    }

    private fun changeItemColor(shoppingData: ShoppingData, checkBox: CheckBox, textViewName: TextView) {
        if (shoppingData.isBought == 0) {
            checkBox.isChecked = false
            textViewName.setTextColor(Color.BLACK)
        } else {
            checkBox.isChecked = true
            textViewName.setTextColor(Color.LTGRAY)
        }
    }

    private fun deleteItem(shoppingData: ShoppingData) {
        databaseHelper.deleteRow(shoppingData.name)
        onNewItemAddedListener!!.onNewItemAdded(shoppingData.name)
        Toast.makeText(context, shoppingData.name + " is deleted", Toast.LENGTH_SHORT).show()
    }

}