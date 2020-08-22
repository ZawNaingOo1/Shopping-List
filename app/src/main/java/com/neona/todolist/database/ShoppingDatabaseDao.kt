package com.neona.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDatabaseDao {

    @Insert
    fun insert(item :ShoppingItem)

    @Update
    fun update(item: ShoppingItem)

    @Query("SELECT * FROM shopping_list_table")
    fun getAllRows():LiveData<List<ShoppingItem>>

    @Query("DELETE FROM shopping_list_table WHERE ID = :id")
    fun deleteRow(id: Long)

    @Query("DELETE FROM shopping_list_table")
    fun clearTable()

    @Query("UPDATE shopping_list_table SET is_bought = :isBought WHERE ID = :id")
    fun updateIsBought(isBought:Boolean,id: Long)
}