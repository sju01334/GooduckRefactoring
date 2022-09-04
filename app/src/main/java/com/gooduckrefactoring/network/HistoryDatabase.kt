package com.gooduckrefactoring.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gooduckrefactoring.dto.History

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase : RoomDatabase(){
    abstract fun historyDao() : HistoryDao

    companion object{
        @Volatile //다른 thread에서 접근 가능하게 만드는 것입니다.
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context):HistoryDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){ //synchronized는 새로운 데이터베이스를 instance시킵니다.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}