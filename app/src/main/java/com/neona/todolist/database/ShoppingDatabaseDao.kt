package com.neona.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDatabaseDao {

    //TODO Insert row
    @Insert
    fun insert(item :ShoppingItem)

    //TODO Update row
    @Update
    fun update(item: ShoppingItem)

    //TODO Getting all data
    @Query("SELECT * FROM shopping_list_table")
    fun getAllRows():LiveData<List<ShoppingItem>>

    //TODO Delete one row
    @Query("DELETE FROM shopping_list_table WHERE ID = :id")
    fun deleteRow(id: Long)

    //TODO Delete table
    @Query("DELETE FROM shopping_list_table")
    fun clearTable()
}