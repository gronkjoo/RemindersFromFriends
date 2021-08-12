package com.example.friendlyreminder

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.os.Bundle
import android.text.BoringLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.friendlyreminder.R
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendlyreminder.adapters.ReminderRecyclerAdapter
import com.example.friendlyreminder.constants.Constants
import com.example.friendlyreminder.databinding.ActivityMainBinding
import com.example.friendlyreminder.model.ReminderCardModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null
    private var remindersRecyclerView:RecyclerView? = null
    private var outAnimation:Animation? = null
    private var inAnimation:Animation? = null
    private var fab:FloatingActionButton? = null
    private var selectedReminders:ArrayList<String> = ArrayList<String>()
    private var editButton:Button? = null
    private var deleteButton:Button? = null

    //This will indicate the current state of the fab button. The fab button is used to either add a new reminder or cancel selection mode
    private var fabButtonMode:Int = Constants.FabButtonState.ADD_REMINDER_MODE.value

    /**
     * This is the main function that gets called when the main page is called.
     * */
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

        fab = binding!!.fab
        fab!!.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            if(fabButtonMode == Constants.FabButtonState.EXIT_REMINDER_SELECT_MODE.value) {
                renderListOfReminders(false, "-1")
                changeFabButton(Constants.FabButtonState.ADD_REMINDER_MODE.value)
            }
            clearSelectedReminders()
            enableOrDisableEditButton()
            enableOrDisableDeleteButton()
        }

        editButton = findViewById(R.id.edit_reminder_button)
        editButton!!.setOnClickListener(listener)
        deleteButton = findViewById(R.id.delete_reminder_button)
        deleteButton!!.setOnClickListener(listener)

        //Initialize the recycler view
        remindersRecyclerView = findViewById(R.id.reminder_list)

        renderListOfReminders(false,"0")

        //iniate the animators
        outAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fade_out)
        outAnimation!!.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {            }

            override fun onAnimationEnd(p0: Animation?) {
                if(fabButtonMode==Constants.FabButtonState.ADD_REMINDER_MODE.value)
                    fab!!.setImageResource(android.R.drawable.ic_delete)
                else if(fabButtonMode==Constants.FabButtonState.EXIT_REMINDER_SELECT_MODE.value)
                    fab!!.setImageResource(android.R.drawable.ic_input_add)

                fab!!.startAnimation(inAnimation)

            }

            override fun onAnimationRepeat(p0: Animation?) {            }

        })
        inAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fade_in)
        inAnimation!!.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {            }

            override fun onAnimationEnd(p0: Animation?) {
                if(fabButtonMode==Constants.FabButtonState.ADD_REMINDER_MODE.value)
                    fabButtonMode=Constants.FabButtonState.EXIT_REMINDER_SELECT_MODE.value
                else if(fabButtonMode==Constants.FabButtonState.EXIT_REMINDER_SELECT_MODE.value)
                    fabButtonMode=Constants.FabButtonState.ADD_REMINDER_MODE.value
            }

            override fun onAnimationRepeat(p0: Animation?) {            }

        })

    }

    /**
     * Returning false to hide the three dots for the menu.
     * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return false
    }

    /**
     * Function to handle clicks on the menu.
     * Menu is hidden via false return on the onCreateOptionsMenu function.
     * */
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


    /**
     * Function to handle clicks for the edit reminder Button.
     * */
    val listener = View.OnClickListener { view ->
        when(view.id){
            R.id.edit_reminder_button ->{
                println("This is inside the case for edit button")
            }
        }
    }


    /**
     * This will populate the reminders in an arrayList. This should come from the DB.
     * */
    fun populateReminders(showCheckBox:Boolean,selectedReminderId: String):ArrayList<ReminderCardModel>{
        var listOfReminders = arrayListOf<ReminderCardModel>()
        var generatedReminderId: Int = 0

        generatedReminderId++
        listOfReminders.add(ReminderCardModel("Take Medications","Wife - 123 456 7890",generatedReminderId.toString(),showCheckBox,
            if(generatedReminderId.toString().equals(selectedReminderId))true else false))
        generatedReminderId++
        listOfReminders.add(ReminderCardModel("Wake up in the morning","Mummy - 321 789 4560",generatedReminderId.toString(),showCheckBox,
            if(generatedReminderId.toString().equals(selectedReminderId))true else false))
        generatedReminderId++
        listOfReminders.add(ReminderCardModel("Go to school","kids - 789 465 1230",generatedReminderId.toString(),showCheckBox,
            if(generatedReminderId.toString().equals(selectedReminderId))true else false))
        generatedReminderId++
        listOfReminders.add(ReminderCardModel("Do exercise","Doctor - 159 753 7520",generatedReminderId.toString(),showCheckBox,
            if(generatedReminderId.toString().equals(selectedReminderId))true else false))
        generatedReminderId++
        listOfReminders.add(ReminderCardModel("Do work","Manager - 456 789 1230",generatedReminderId.toString(),showCheckBox,
            if(generatedReminderId.toString().equals(selectedReminderId))true else false))
        generatedReminderId++
        listOfReminders.add(ReminderCardModel("Take Rest","Friend - 789 123 4560",generatedReminderId.toString(),showCheckBox,
            if(generatedReminderId.toString().equals(selectedReminderId))true else false))
        generatedReminderId++

        return listOfReminders
    }

    /**
     * This will take render the populated list of reminders on screen.
     * If required all checkboxes are shown.
     * If required a particular checkbox is checked.
     * */
    fun renderListOfReminders(showCheckBox: Boolean,selectedReminderId: String):Unit{
        //create the list of reminders
        var listOfReminders:ArrayList<ReminderCardModel> = populateReminders(showCheckBox,selectedReminderId)

        //create the adapter
        val reminderAdapter = ReminderRecyclerAdapter(this,listOfReminders)

        //setting the layoutmanager for the recycler adapter with VERTICAL orientation
        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        //create the recycler view
        remindersRecyclerView?.layoutManager = linearLayoutManager
        remindersRecyclerView?.adapter = reminderAdapter
        reminderAdapter.notifyDataSetChanged()
    }

    /**
     * This will change the mode of the fab button.
     * In Add mode fab button will take the user to add activity
     * In Edit mode fab button will cancel the user selection of reminders
     * */
    fun changeFabButton(requiredMode:Int):Boolean{
        if(requiredMode==Constants.FabButtonState.ADD_REMINDER_MODE.value ||
            requiredMode==Constants.FabButtonState.EXIT_REMINDER_SELECT_MODE.value){
            fab!!.startAnimation(outAnimation)
            return true
        }
        else
            return false
    }


    /**
     * This function will add the reminder to the list of reminders selected
    * */
    fun addToCheckedReminderList(reminderId:String):Boolean{
        selectedReminders!!.add(reminderId)
        enableOrDisableEditButton()
        enableOrDisableDeleteButton()
        return true
    }

    /**
     * This function will remove reminders from the list of reminders selected
     */
    fun removeFromCheckedRemindersList(reminderId:String):Boolean{
        try {
            selectedReminders!!.remove(reminderId)
            return true
        }
        catch (e:Exception){
            println("Exception in removing reminder: "+e.message)
            return false
        }
        finally {
            enableOrDisableEditButton()
            enableOrDisableDeleteButton()
        }
    }

    /**
     * This function takes care of enabling/disabling the edit button
     * */
    fun enableOrDisableEditButton(){
        if(selectedReminders!!.size == 1)
            editButton!!.isEnabled = true
        else
            editButton!!.isEnabled = false
    }

    /**
     * This function takes care of enabling/disabling the delete button
     * */
    fun enableOrDisableDeleteButton(){
        if(selectedReminders!!.size >=1)
            deleteButton!!.isEnabled = true
        else
            deleteButton!!.isEnabled = false

    }

    /**
     * This function will clear the list of reminders selected
     * */
    fun clearSelectedReminders(){
        selectedReminders.clear()
    }

}