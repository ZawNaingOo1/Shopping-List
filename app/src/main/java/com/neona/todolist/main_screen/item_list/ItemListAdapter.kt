package com.neona.todolist.main_screen.item_list

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neona.todolist.R
import com.neona.todolist.database.DatabaseHelper
import com.neona.todolist.database.ShoppingData
import com.neona.todolist.main_screen.OnNewItemAddedListener

class ItemListAdapter(context: Context, private val shoppingDataList: List<ShoppingData>) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var databaseHelper = DatabaseHelper(context)
    private var onNewItemAddedListener: OnNewItemAddedListener? = null



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById(R.id.txt_name)
        var checkBox: CheckBox = itemView.findViewById(R.id.cb_select)
        var rowDelete: ImageView = itemView.findViewById(R.id.row_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_row, parent, false)
        onNewItemAddedListener = parent.context as OnNewItemAddedListener

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val shoppingData = shoppingDataList[position]

        holder.checkBox.setOnClickListener {
            onClickItemCheckbox(holder.checkBox, holder.textViewName, shoppingData)
        }

        // delete listener
        holder.rowDelete.setOnClickListener {
            deleteItem(shoppingData)
        }

        // Active or inactive item color
        changeItemColor(shoppingData, holder.checkBox, holder.textViewName)

        holder.textViewName.text = shoppingData.name
    }

    // operation methods
    private fun onClickItemCheckbox(checkBox: CheckBox, textViewName: TextView, shoppingData: ShoppingData) {
        if (checkBox.isChecked) {
            textViewName.setTextColor(Color.LTGRAY)
            databaseHelper.updateIsBought(shoppingData.name, 1)
            onNewItemAddedListener!!.onNewItemAdded(shoppingData.name)
            //Toast.makeText(getContext(), shoppingData.name + " is checked", Toast.LENGTH_LONG).show()
        } else {
            textViewName.setTextColor(Color.BLACK)
            databaseHelper.updateIsBought(shoppingData.name, 0)
            onNewItemAddedListener!!.onNewItemAdded(shoppingData.name)
            //Toast.makeText(context, shoppingData.name + " is unchecked", Toast.LENGTH_LONG).show()
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
        //Toast.makeText(this, shoppingData.name + " is deleted", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return shoppingDataList.size
    }
}