package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.repository.CategoryRepository
import com.gooduckrefactoring.repository.HomeRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.Banner
import com.nepplus.gooduck.models.Category
import com.nepplus.gooduck.models.Product
import kotlinx.coroutines.launch

class CategoryViewModel() : ViewModel() {

    private val _categoryItemList: MutableLiveData<List<Category>> = MutableLiveData()

    val categoryItemList: LiveData<List<Category>> get() = _categoryItemList

    private val repository by lazy {
        CategoryRepository.getInstance()
    }

    init {
        getCategoryItems()
    }


    fun getCategoryItems() {
        viewModelScope.launch {
            repository!!.getRequestAllCategory{
                if (it is Result.Success) {
                    _categoryItemList.value = it.data.data!!.categories
                }
            }

        }
    }




}




