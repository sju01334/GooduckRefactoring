package com.gooduckrefactoring.api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gooduckrefactoring.dto.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDao() : RoomAPIList


}