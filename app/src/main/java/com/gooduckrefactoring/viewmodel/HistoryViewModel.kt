package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.api.HistoryDatabase
import com.gooduckrefactoring.dto.History
import com.gooduckrefactoring.repository.local.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    val allData: LiveData<List<History>>
    private val repository: HistoryRepository

    init {
        val historyDao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(historyDao) //이니셜라이즈 해줍니다.
        allData = repository.allData // rrepository에서 만들어줬던 livedata입니다.
    }

    fun addHistory(history: History){// 파라미터에 만든 데이터클래스가 들어갑니다.
        viewModelScope.launch(Dispatchers.IO) { //코루틴 활성화 dispatcherIO는 백그라운드에서 실행
            repository.insertHistory(history)
        }
    }

}