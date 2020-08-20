package com.neona.todolist

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.neona.todolist.database.ShoppingDatabaseDao
import com.neona.todolist.database.ShoppingItem
import com.neona.todolist.database.ShoppingListDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ShoppingDatabaseTest {
    private lateinit var shoppingDatabaseDao: ShoppingDatabaseDao
    private lateinit var db: ShoppingListDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context,ShoppingListDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        shoppingDatabaseDao = db.shoppingDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetItems(){
        val item = ShoppingItem(10,200,"ice",false)
        shoppingDatabaseDao.insert(item)
        shoppingDatabaseDao.getAllRows()
    }
}