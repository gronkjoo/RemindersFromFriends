package com.example.friendlyreminder.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class ReminderContact (
    @ColumnInfo(name = "reminder_id")val reminderId:Int,
    @ColumnInfo(name = "contact_name")val contactName:String,
    @ColumnInfo(name = "contact_phone_no")val contactNumber:String
)