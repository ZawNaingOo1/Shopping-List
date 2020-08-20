package com.neona.todolist.main_screen.add_new_item

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.neona.todolist.R
import com.neona.todolist.database.ShoppingItem
import java.util.*

class NewItemFragment : Fragment() {
    var ID = 0
    var name: String? = ""
    var price = 0
    var bought = false
    var shoppingDataArrayList = ArrayList<ShoppingItem>()
    //private var onNewItemAddedListener: OnNewItemAddedListener? = null
    //var databaseHelper: DatabaseHelper? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.new_item_fragment, container, false)
        val editText = view.findViewById<View>(R.id.editText) as EditText

        // Database helper
        //databaseHelper = DatabaseHelper(context)

        // EditText listener
        editText.setOnKeyListener(addItemOnKeyboardEnterKey(editText))

        // enter button listener
        val btnEnter = view.findViewById<ImageButton>(R.id.btn_enter)
        btnEnter.setOnClickListener {
            addItemOnEnterBtn(editText)
        }
        //setShoppingArrayList()
        return view
    }

    private fun addItemOnEnterBtn(editText: EditText) {
        val newItem = editText.text.toString()
        if (TextUtils.isEmpty(newItem)) {
            Toast.makeText(context, "Enter an item", Toast.LENGTH_LONG).show()
            editText.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        } else {
            // insert data to database
            //databaseHelper!!.insertData(newItem, 0, false)
            //onNewItemAddedListener!!.onNewItemAdded(newItem)
            editText.setText("")
            if (shoppingDataArrayList.size != 0) {
                val shoppingData1 = shoppingDataArrayList[0]
                Log.d("SQLite list", shoppingData1.itemName)
            }
        }
    }

    private fun addItemOnKeyboardEnterKey(editText: EditText): View.OnKeyListener {
        return View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) //onNewItemAddedListener!!.onNewItemAdded("newItem")
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val newItem = editText.text.toString()
                if (TextUtils.isEmpty(newItem)) {
                    Toast.makeText(context, "Enter an item", Toast.LENGTH_LONG).show()
                } else {
                    // insert data to database
                    //databaseHelper!!.insertData(newItem, 0, false)
                    //onNewItemAddedListener!!.onNewItemAdded(newItem)
                    editText.setText("")
                    if (shoppingDataArrayList.size != 0) {
                        val shoppingData1 = shoppingDataArrayList[0]
                        Log.d("SQLite list", shoppingData1.itemName)
                    }
                }
                return@OnKeyListener true
            }
            false
        }
    }

}