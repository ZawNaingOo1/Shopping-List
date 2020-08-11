package com.neona.todolist.main_screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.neona.todolist.R
import com.neona.todolist.adapter.ShoppingListAdapter
import com.neona.todolist.database.DatabaseHelper
import com.neona.todolist.database.ShoppingData
import com.neona.todolist.main_screen.item_list.ShoppingListFragment
import java.util.*

//import org.rabbitconverter.rabbit.Rabbit;
class MainActivity : AppCompatActivity(), OnNewItemAddedListener {
    // ArrayList of to do items
    private val toDoItemsArrayList = ArrayList<String?>()

    // setting arrayList
    private var id = 0
    var name: String? = null
    var price = 0
    var bought = 0
    var databaseHelper: DatabaseHelper? = null
    private lateinit var emptyTextV: TextView
    var shoppingListAdapter: ShoppingListAdapter? = null
    var shoppingListFragment: ShoppingListFragment? = null
    var shoppingDataArrayList = ArrayList<ShoppingData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emptyTextV = findViewById(R.id.emptyText)
        displayData()
        if (shoppingDataArrayList.size != 0) {
            emptyTextV.visibility = View.GONE
        }


        //google ads
        MobileAds.initialize(this, "ca-app-pub-2822531422299707~5192523563")
        val mAdView = findViewById<AdView>(R.id.adView1)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Toast.makeText(applicationContext, errorCode, Toast.LENGTH_LONG).show()
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
        mAdView.loadAd(adRequest)
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

    override fun onNewItemAdded(newItem: String?) {
        toDoItemsArrayList.add(newItem)
        shoppingDataArrayList.clear()
        displayData()
        if (shoppingDataArrayList.size == 0) {
            emptyTextV.visibility = View.VISIBLE
        } else {
            emptyTextV.visibility = View.GONE
        }
    }

    private fun setShoppingArrayList() {
        val res = databaseHelper!!.allData
        while (res.moveToNext()) {
            id = res.getInt(0)
            name = res.getString(1)
            price = res.getInt(2)
            bought = res.getInt(3)
            shoppingDataArrayList.add(ShoppingData(id, name, price, bought))
        }
    }

    private fun displayData() {
        databaseHelper = DatabaseHelper(this)
        setShoppingArrayList()

        //Get references to the Fragment
        val fragmentManager = supportFragmentManager
        shoppingListFragment = fragmentManager.findFragmentById(R.id.ToDoListFragment) as ShoppingListFragment?
        shoppingListAdapter = ShoppingListAdapter(this, R.layout.list_row, shoppingDataArrayList)

        // Bind the Array Adapter to the ListView
        shoppingListFragment!!.listAdapter = shoppingListAdapter
    }

    // alert and clear data
    private fun alertDialogForDeleteAll() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Delete all!")
        builder.setMessage("Are you sure you want to clear all?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            databaseHelper!!.clearTable()
            shoppingDataArrayList.clear()
            displayData()
            emptyTextV.visibility = View.VISIBLE
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
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