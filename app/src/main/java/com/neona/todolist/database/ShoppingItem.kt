package com.neona.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_table")
data class ShoppingItem(
        @PrimaryKey(autoGenerate = true)
        val ID: Long=0L,
        @ColumnInfo(name = "price")
        var price: Int = 0,
        @ColumnInfo(name = "item_name")
        var itemName: String? = null,
        @ColumnInfo(name = "is_bought")
        var isBought: Boolean = false
)