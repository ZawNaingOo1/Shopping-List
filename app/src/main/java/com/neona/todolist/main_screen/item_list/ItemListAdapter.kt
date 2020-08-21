package com.neona.todolist.main_screen.item_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neona.todolist.R
import com.neona.todolist.database.ShoppingDatabaseDao
import com.neona.todolist.database.ShoppingItem

class ItemListAdapter() : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var data =  listOf<ShoppingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById(R.id.txt_name)
        var checkBox: CheckBox = itemView.findViewById(R.id.cb_select)
        var rowDelete: ImageView = itemView.findViewById(R.id.row_delete)

        fun bind(shoppingItem: ShoppingItem){
            textViewName.text = shoppingItem.itemName
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_row, parent, false)



        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val shoppingItem = data[position]
/*
        holder.checkBox.setOnClickListener {
            //onClickItemCheckbox(holder.checkBox, holder.textViewName, shoppingItem, dataSource)
        }

        // delete listener
        holder.rowDelete.setOnClickListener {
            //deleteItem(shoppingItem)
        }

        // Active or inactive item color
        //changeItemColor(shoppingItem, holder.checkBox, holder.textViewName)
*/
        holder.bind(shoppingItem)
    }

    // operation methods
    private fun onClickItemCheckbox(checkBox: CheckBox, textViewName: TextView, shoppingItem: ShoppingItem,dataSource: ShoppingDatabaseDao) {
        if (checkBox.isChecked) {
            textViewName.setTextColor(Color.LTGRAY)
            //databaseHelper.updateIsBought(shoppingItem.itemName, 1)
            shoppingItem.isBought = true
            dataSource.update(shoppingItem)

        } else {
            textViewName.setTextColor(Color.BLACK)
            shoppingItem.isBought = true
            dataSource.update(shoppingItem)
        }
    }

    private fun changeItemColor(shoppingItem: ShoppingItem, checkBox: CheckBox, textViewName: TextView) {
        if (!shoppingItem.isBought) {
            checkBox.isChecked = false
            textViewName.setTextColor(Color.BLACK)
        } else {
            checkBox.isChecked = true
            textViewName.setTextColor(Color.LTGRAY)
        }
    }

    /*private fun deleteItem(shoppingItem: ShoppingItem) {
        dataSource.deleteRow(shoppingItem.ID)
    }*/

    override fun getItemCount(): Int {
        return data.size
    }
}