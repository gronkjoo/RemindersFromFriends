package com.example.friendlyreminder.database.entities

import androidx.room.ColumnInfo

data class ReminderSummary (
    @ColumnInfo(name = "reminder_name")val reminderName:String?,
    @ColumnInfo(name = "contact_name")val contactName:String?,
    @ColumnInfo(name = "contact_phone_no")val contactNo:String?
)