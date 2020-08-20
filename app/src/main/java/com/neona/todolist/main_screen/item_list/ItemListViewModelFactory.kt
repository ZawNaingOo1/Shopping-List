package com.neona.todolist.main_screen.item_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neona.todolist.database.ShoppingDatabaseDao
import java.lang.IllegalArgumentException

class ItemListViewModelFactory (
        private val dataSource: ShoppingDatabaseDao,
        private val application: Application
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemListViewModel::class.java)){
            return ItemListViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}