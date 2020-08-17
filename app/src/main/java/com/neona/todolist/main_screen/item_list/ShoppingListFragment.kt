package com.neona.todolist.main_screen.item_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neona.todolist.R
import com.neona.todolist.database.DatabaseHelper
import com.neona.todolist.database.ShoppingData
import java.util.ArrayList

class ShoppingListFragment : Fragment(){

    var databaseHelper: DatabaseHelper? = null
    // setting arrayList
    private var itemId = 0
    var name: String? = null
    var price = 0
    var bought = 0
    var shoppingDataArrayList = ArrayList<ShoppingData>()

    lateinit var recyclerView:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("inflateError", "fragment inflating")
        var view = inflater.inflate(R.layout.item_list_fragment,container,false)
        recyclerView = view.findViewById(R.id.item_list_recycler_view)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.i("inflateError", "fragment inflated")
        // Database helper
        databaseHelper = DatabaseHelper(context)
        setShoppingArrayList()

        // recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = ItemListAdapter(requireContext(),shoppingDataArrayList)

        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setShoppingArrayList() {
        val res = databaseHelper!!.allData
        while (res.moveToNext()) {
            itemId = res.getInt(0)
            name = res.getString(1)
            price = res.getInt(2)
            bought = res.getInt(3)
            shoppingDataArrayList.add(ShoppingData(id, name, price, bought))
        }
    }
}