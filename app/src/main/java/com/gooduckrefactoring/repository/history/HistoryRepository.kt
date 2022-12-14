package com.gooduckrefactoring.repository.history

import androidx.lifecycle.LiveData
import com.gooduckrefactoring.network.HistoryDao
import com.gooduckrefactoring.dto.History

class HistoryRepository(private val historyDao: HistoryDao) {

    val allData: LiveData<List<History>> = historyDao.getAll()

    suspend fun insertHistory(history : History){
        historyDao.insertHistory(history)
    }


    suspend fun deleteAll(){
        historyDao.deleteAll()
    }
}