package com.example.friendlyreminder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.friendlyreminder.database.dao.ReminderDao
import com.example.friendlyreminder.database.entities.ReminderContact
import com.example.friendlyreminder.database.entities.ReminderMaster
import com.example.friendlyreminder.database.entities.ReminderTime

@Database(entities = arrayOf(ReminderContact::class,ReminderMaster::class, ReminderTime::class),version=1)
abstract class ReminderDB:RoomDatabase(){
    abstract fun reminderDao():ReminderDao
}