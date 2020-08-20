package com.neona.todolist.main_screen.item_list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.neona.todolist.database.ShoppingDatabaseDao
import com.neona.todolist.database.ShoppingItem
import kotlinx.coroutines.*

class ItemListViewModel(dataSource: ShoppingDatabaseDao, application: Application) : ViewModel() {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    init {
        insertRow(dataSource)
        //_items = getItems(dataSource)
    }


    val items= dataSource.getAllRows()


    private fun insertRow(dataSource: ShoppingDatabaseDao) {
        uiScope.launch {
            insertOneRow(dataSource)
        }
    }

    private suspend fun insertOneRow(dataSource: ShoppingDatabaseDao) {

        withContext(Dispatchers.IO) {
            val item = ShoppingItem()
            item.itemName = "balling"
            dataSource.insert(item)
        }
    }

    private suspend fun getItems(dataSource: ShoppingDatabaseDao): LiveData<List<ShoppingItem>> {

        return withContext(Dispatchers.IO) {
            val itemList = dataSource.getAllRows()
            itemList
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}