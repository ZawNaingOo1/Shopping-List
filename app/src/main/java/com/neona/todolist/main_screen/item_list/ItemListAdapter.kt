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
import com.neona.todolist.database.ShoppingItem

class ItemListAdapter(itemListViewModel: ItemListViewModel) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var data = listOf<ShoppingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var viewModel: ItemListViewModel = itemListViewModel

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById(R.id.txt_name)
        var checkBox: CheckBox = itemView.findViewById(R.id.cb_select)
        var rowDelete: ImageView = itemView.findViewById(R.id.row_delete)

        fun bind(shoppingItem: ShoppingItem) {
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
        holder.bind(shoppingItem)
        holder.checkBox.setOnClickListener {
            onClickItemCheckbox(holder.checkBox, holder.textViewName, shoppingItem)
        }

        // delete listener
        holder.rowDelete.setOnClickListener {
            deleteItem(shoppingItem)
        }

        // Active or inactive item color
        changeItemColor(holder.checkBox, holder.textViewName, shoppingItem)
    }

    // operation methods
    private fun onClickItemCheckbox(checkBox: CheckBox, textViewName: TextView, shoppingItem: ShoppingItem) {
        if (checkBox.isChecked) {
            textViewName.setTextColor(Color.LTGRAY)
            viewModel.onUpdateIsBought(true, shoppingItem.ID)

        } else {
            textViewName.setTextColor(Color.BLACK)
            viewModel.onUpdateIsBought(false, shoppingItem.ID)

        }
    }

    private fun changeItemColor(checkBox: CheckBox, textViewName: TextView, shoppingItem: ShoppingItem) {

        if (!shoppingItem.isBought) {
            checkBox.isChecked = false
            textViewName.setTextColor(Color.BLACK)
        } else {
            checkBox.isChecked = true
            textViewName.setTextColor(Color.LTGRAY)
        }
    }

    private fun deleteItem(shoppingItem: ShoppingItem) {
        viewModel.onDeleteOneItem(shoppingItem.ID)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}