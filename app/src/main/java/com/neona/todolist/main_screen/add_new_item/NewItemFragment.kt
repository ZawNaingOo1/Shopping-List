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
import com.neona.todolist.database.DatabaseHelper
import com.neona.todolist.database.ShoppingData
import com.neona.todolist.main_screen.OnNewItemAddedListener
import java.util.*

class NewItemFragment : Fragment() {
    var ID = 0
    var name: String? = null
    var price = 0
    var bought = 0
    var shoppingDataArrayList = ArrayList<ShoppingData>()
    private var onNewItemAddedListener: OnNewItemAddedListener? = null
    var databaseHelper: DatabaseHelper? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.new_item_fragment, container, false)
        val editText = view.findViewById<View>(R.id.editText) as EditText

        // Database helper
        databaseHelper = DatabaseHelper(context)

        // EditText listener
        editText.setOnKeyListener(addItemOnKeyboardEnterKey(editText))

        // enter button listener
        val btnEnter = view.findViewById<ImageButton>(R.id.btn_enter)
        btnEnter.setOnClickListener {
            addItemOnEnterBtn(editText)
        }
        setShoppingArrayList()
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
            databaseHelper!!.insertData(newItem, 0, false)
            onNewItemAddedListener!!.onNewItemAdded(newItem)
            editText.setText("")
            if (shoppingDataArrayList.size != 0) {
                val shoppingData1 = shoppingDataArrayList[0]
                Log.d("SQLite list", shoppingData1.name)
            }
        }
    }

    private fun addItemOnKeyboardEnterKey(editText: EditText): View.OnKeyListener {
        return View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) onNewItemAddedListener!!.onNewItemAdded("newItem")
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val newItem = editText.text.toString()
                if (TextUtils.isEmpty(newItem)) {
                    Toast.makeText(context, "Enter an item", Toast.LENGTH_LONG).show()
                } else {
                    // insert data to database
                    databaseHelper!!.insertData(newItem, 0, false)
                    onNewItemAddedListener!!.onNewItemAdded(newItem)
                    editText.setText("")
                    if (shoppingDataArrayList.size != 0) {
                        val shoppingData1 = shoppingDataArrayList[0]
                        Log.d("SQLite list", shoppingData1.name)
                    }
                }
                return@OnKeyListener true
            }
            false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNewItemAddedListener = try {
            context as OnNewItemAddedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnNewItemAddedListener")
        }
    }

    private fun setShoppingArrayList() {
        val res = databaseHelper!!.allData
        while (res.moveToNext()) {
            ID = res.getInt(0)
            name = res.getString(1)
            price = res.getInt(2)
            bought = res.getInt(3)
            shoppingDataArrayList.add(0, ShoppingData(ID, name, price, bought))
        }
    }
}