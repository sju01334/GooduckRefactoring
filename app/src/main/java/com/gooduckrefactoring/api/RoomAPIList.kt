package com.gooduckrefactoring.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gooduckrefactoring.dto.History

@Dao
interface RoomAPIList {
    @Query("SELECT * FROM history")
    suspend fun getAll() : List<History>

    //같은 데이터가 있다면 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history : History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    suspend fun delete(keyword : String)

}