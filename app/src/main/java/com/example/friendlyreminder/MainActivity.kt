package com.example.friendlyreminder

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.friendlyreminder.R
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendlyreminder.adapters.ReminderRecyclerAdapter
import com.example.friendlyreminder.databinding.ActivityMainBinding
import com.example.friendlyreminder.model.ReminderCardModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null
    private var remindersRecyclerView:RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.toolbar)
        /*val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration!!)*/
        binding!!.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        var editButton:Button = findViewById(R.id.edit_reminder_button)
        editButton.setOnClickListener(listener)

        //Initialize the recycler view
        remindersRecyclerView = findViewById(R.id.reminder_list)

        //create the list of reminders
        populateReminders()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        return (NavigationUI.navigateUp(navController, appBarConfiguration!!)
                || super.onSupportNavigateUp())
    }*/

    val listener = View.OnClickListener { view ->
        when(view.id){
            R.id.edit_reminder_button ->{
                println("This is inside the case for edit button")
            }
        }
    }

    fun populateReminders(){
        var listOfReminders = arrayListOf<ReminderCardModel>()

        listOfReminders.add(ReminderCardModel("Take Medications","Wife - 123 456 7890"))
        listOfReminders.add(ReminderCardModel("Wake up in the morning","Mummy - 321 789 4560"))
        listOfReminders.add(ReminderCardModel("Go to school","kids - 789 465 1230"))
        listOfReminders.add(ReminderCardModel("Do exercise","Doctor - 159 753 7520"))
        listOfReminders.add(ReminderCardModel("Do work","Manager - 456 789 1230"))
        listOfReminders.add(ReminderCardModel("Take Rest","Friend - 789 123 4560"))

        //create the adapter
        val reminderAdapter = ReminderRecyclerAdapter(this,listOfReminders)

        //setting the layoutmanager for the recycler adapter with VERTICAL orientation
        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        //create the recycler view
        remindersRecyclerView?.layoutManager = linearLayoutManager
        remindersRecyclerView?.adapter = reminderAdapter
    }
}