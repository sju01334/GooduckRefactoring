package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.network.HistoryDatabase
import com.gooduckrefactoring.dto.History
import com.gooduckrefactoring.repository.history.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    val allData: LiveData<List<History>>
    private val repository: HistoryRepository

    init {
        val historyDao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(historyDao) //이니셜라이즈
        allData = repository.allData // rrepository에서 만들어줬던 livedata
    }

    fun addHistory(history: History){// 파라미터에 만든 데이터클래스
        viewModelScope.launch(Dispatchers.IO) { //코루틴 활성화 dispatcherIO는 백그라운드에서 실행
            repository.insertHistory(history)
        }
    }

    fun deleteAll(){// 파라미터에 만든 데이터클래스
        viewModelScope.launch(Dispatchers.IO) { //코루틴 활성화 dispatcherIO는 백그라운드에서 실행
            repository.deleteAll()
        }
    }

}