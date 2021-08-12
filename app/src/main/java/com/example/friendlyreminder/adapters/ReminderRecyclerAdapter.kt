package com.example.friendlyreminder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.friendlyreminder.MainActivity
import com.example.friendlyreminder.R
import com.example.friendlyreminder.constants.Constants
import com.example.friendlyreminder.model.ReminderCardModel
import com.example.friendlyreminder.observers.ReminderRecyclerObserver
import com.google.android.material.textview.MaterialTextView

class ReminderRecyclerAdapter: RecyclerView.Adapter<ReminderRecyclerAdapter.ViewHolder> {

    var context:Context? = null
    var reminderList: ArrayList<ReminderCardModel>? = null

    constructor(context: Context?, reminderList: ArrayList<ReminderCardModel>?) : super() {
        this.context = context
        this.reminderList = reminderList
    }

    init {
        this.registerAdapterDataObserver(ReminderRecyclerObserver())
    }


    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the view of each cardview
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.single_reminder_card,parent,false)
        return ViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var singleReminder:ReminderCardModel = reminderList!!.get(position)
        holder.reminderNameTextField!!.setText(singleReminder.reminderName)
        holder.reminderContactTextField!!.setText(singleReminder.reminderContact)
        holder.reminderId!!.setText(singleReminder.reminderId)
        if(singleReminder.showCheckbox) {
            holder.checkboxLayout!!.visibility = LinearLayout.VISIBLE
        }
        else{
            holder.checkboxLayout!!.visibility = LinearLayout.GONE
        }

        holder.actionCheckbox!!.id = Integer(singleReminder.reminderId).toInt()
        holder.actionCheckbox!!.isChecked = singleReminder.checkCheckbox

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return reminderList!!.size
    }

    fun getItem(position:Int):ReminderCardModel{
        return reminderList!!.get(position)
    }

    inner class ViewHolder : RecyclerView.ViewHolder,View.OnClickListener,View.OnLongClickListener {
        var reminderNameTextField:TextView? = null
        var reminderContactTextField:TextView? = null
        var reminderId:TextView? = null
        var actionCheckbox: CheckBox? = null
        var checkboxLayout: LinearLayout? = null

        init{
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        constructor(itemView: View):super(itemView){
            reminderNameTextField = itemView.findViewById(R.id.reminder_name)
            reminderContactTextField = itemView.findViewById(R.id.contact_name)
            reminderId = itemView.findViewById(R.id.reminderId)
            checkboxLayout = itemView.findViewById(R.id.checkbox_layout)
            actionCheckbox = itemView.findViewById(R.id.action_checkbox)

            actionCheckbox?.setOnCheckedChangeListener { checkBox, isChecked ->
                if(isChecked){
                    //Toast.makeText(itemView?.context,checkBox!!.id.toString(),Toast.LENGTH_SHORT).show()
                    (this@ReminderRecyclerAdapter.context as MainActivity).addToCheckedReminderList(checkBox!!.id.toString())
                }
                else{
                    (this@ReminderRecyclerAdapter.context as MainActivity).removeFromCheckedRemindersList(checkBox!!.id.toString())
                }
            }
        }

        override fun onClick(view: View?) {
            //Toast.makeText(view?.context,"click",Toast.LENGTH_SHORT).show()
        }

        override fun onLongClick(view: View?): Boolean {
            //Toast.makeText(view?.context,"LOOOONNNG click",Toast.LENGTH_SHORT).show()

            var selectedReminderId: String = (((view as ViewGroup).getChildAt(1) as ViewGroup)
                .getChildAt(0) as MaterialTextView).text.toString()

            var mainActivity = context as MainActivity
            //repopulate the list of reminders again with the select buttons shown
            mainActivity.renderListOfReminders(true,selectedReminderId)
            //change the fab button to cancel selection mode
            mainActivity.changeFabButton(Constants.FabButtonState.EXIT_REMINDER_SELECT_MODE.value)

            //this@ReminderRecyclerAdapter.notifyDataSetChanged()

            return true
        }
    }
}