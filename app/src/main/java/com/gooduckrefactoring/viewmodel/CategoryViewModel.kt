package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.repository.remote.CategoryRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.Category
import com.nepplus.gooduck.models.Product
import kotlinx.coroutines.launch

class CategoryViewModel() : ViewModel() {

    private val _categoryItemList: MutableLiveData<List<Category>> = MutableLiveData()
    val categoryItemList: LiveData<List<Category>> get() = _categoryItemList

    private val _selectedCategoryItemList: MutableLiveData<List<Product>> = MutableLiveData()
    val selectedCategoryItemList: LiveData<List<Product>> get() = _selectedCategoryItemList

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

    fun getSelectedCategoryItems(id : Int) {
        viewModelScope.launch {
            repository!!.getRequestProducts(id){
                if (it is Result.Success) {
                    _selectedCategoryItemList.value = it.data.data!!.products
                }
            }

        }
    }




}




