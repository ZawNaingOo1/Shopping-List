package com.neona.todolist.main_screen

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neona.todolist.R
import com.neona.todolist.database.ShoppingListDatabase
import com.neona.todolist.databinding.ActivityMainBinding
import com.neona.todolist.main_screen.item_list.ItemListAdapter
import com.neona.todolist.main_screen.item_list.ItemListViewModel
import com.neona.todolist.main_screen.item_list.ItemListViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var itemListViewModel:ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil
                .setContentView(this,R.layout.activity_main)
        val application = this.application
        val dataSource = ShoppingListDatabase.getInstance(application).shoppingDatabaseDao
        val viewModelFactory = ItemListViewModelFactory(dataSource, application)

        itemListViewModel =
                ViewModelProvider(
                        this, viewModelFactory
                ).get(ItemListViewModel::class.java)

        binding.itemListViewModel = itemListViewModel

        // adapter
        val adapter = ItemListAdapter(itemListViewModel)
        binding.itemListRecyclerView.hasFixedSize()
        binding.itemListRecyclerView.adapter = adapter

        itemListViewModel.items.observe(this, Observer {
            it?.let {
                adapter.data = it
                //Log.i("ListSize",it.size.toString())
            }

        })

        binding.btnEnter.setOnClickListener {
            itemListViewModel.onNewItemInsert(binding.editText.text.toString())
        }

        binding.lifecycleOwner = this
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> alertDialogForDeleteAll()
            R.id.shareApp -> shareApp()
            R.id.rateApp -> rateApp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogForDeleteAll() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Delete all list")
                .setMessage("Are you sure you want to delete all list?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    itemListViewModel.onDeleteShoppingListTable()
                }
                .setNegativeButton("No"){_,_->

                }
        alertDialog.create().show()
    }


    //share app
    private fun shareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.neona.todolist")
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, "Contribute good app")
        startActivity(shareIntent)
    }

    /*
     * Start with rating the app
     * Determine if the Play Store is installed on the device
     *
     * */
    private fun rateApp() {
        try {
            val rateIntent = rateIntentForUrl("market://details")
            startActivity(rateIntent)
        } catch (e: ActivityNotFoundException) {
            val rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details")
            startActivity(rateIntent)
        }
    }

    private fun rateIntentForUrl(url: String?): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, "com.neona.todolist")))
        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        intent.addFlags(flags)
        return intent
    }
}