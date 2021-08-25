package com.example.friendlyreminder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.friendlyreminder.database.entities.ReminderContact
import com.example.friendlyreminder.database.entities.ReminderMaster
import com.example.friendlyreminder.database.entities.ReminderSummary
import com.example.friendlyreminder.database.entities.ReminderTime

@Dao
interface ReminderDao {
    @Query("SELECT reminder_id,reminder_name,contact_name,contact_phone_no FROM remindermaster,remindercontact")
    fun getReminderSummary():ReminderSummary

    @Insert
    fun insertReminderDetails(reminderMaster: ReminderMaster,reminderContact: ReminderContact,reminderTime: ReminderTime)
}