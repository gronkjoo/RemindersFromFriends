package com.example.friendlyreminder.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReminderMaster(
    @PrimaryKey(autoGenerate = true) val reminderId:Int = 10000,
    @ColumnInfo(name = "reminder_name") val reminderName:String?
)
