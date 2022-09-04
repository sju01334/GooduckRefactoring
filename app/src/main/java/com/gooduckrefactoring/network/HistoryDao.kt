package com.gooduckrefactoring.network

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gooduckrefactoring.dto.History

@Dao
interface HistoryDao {
    @Query("SELECT DISTINCT keyword FROM history ORDER BY uid DESC")
    fun getAll() : LiveData<List<History>>

    //같은 데이터가 있다면 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history : History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    suspend fun delete(keyword : String)

    @Query("DELETE FROM history")
    suspend fun deleteAll()

}