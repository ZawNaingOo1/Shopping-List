package com.neona.todolist.main_screen.item_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neona.todolist.R
import com.neona.todolist.database.ShoppingDatabaseDao
import com.neona.todolist.database.ShoppingListDatabase
import com.neona.todolist.databinding.ItemListFragmentBinding

class ShoppingListFragment : Fragment() {

    //var databaseHelper: DatabaseHelper? = null

    // setting arrayList
    private var itemId = 0
    var name: String? = null
    var price = 0
    var bought = 0
    //var shoppingDataArrayList = ArrayList<ShoppingData>()
    private lateinit var itemListViewModel:ViewModel

    lateinit var recyclerView: RecyclerView

    lateinit var binding: ItemListFragmentBinding

    lateinit var dataSource: ShoppingDatabaseDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = inflate(inflater, R.layout.item_list_fragment,container,false)

        recyclerView = binding.itemListRecyclerView

        val application = requireNotNull(this.activity).application
        dataSource = ShoppingListDatabase.getInstance(application).shoppingDatabaseDao
        val viewModelFactory = ItemListViewModelFactory(dataSource, application)

        itemListViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory
                ).get(ItemListViewModel::class.java)

        binding.itemListViewModel = itemListViewModel as ItemListViewModel

        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.i("inflateError", "fragment inflated")
        // Database helper
        //databaseHelper = DatabaseHelper(context)
        var adapter:RecyclerView.Adapter<ItemListAdapter.ViewHolder>

        // recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.itemListViewModel!!.items.observe(viewLifecycleOwner, Observer {
            itemList ->
            adapter = ItemListAdapter(requireContext(), itemList,dataSource)
            recyclerView.adapter = adapter
            Log.i("ListSize",itemList.size.toString())
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}