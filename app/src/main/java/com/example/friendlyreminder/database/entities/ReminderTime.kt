package com.example.friendlyreminder.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity
class ReminderTime (
    @ColumnInfo(name = "reminder_id")val reminderId:Int,
    @ColumnInfo(name = "reminder_time")val reminderTime:Date
)