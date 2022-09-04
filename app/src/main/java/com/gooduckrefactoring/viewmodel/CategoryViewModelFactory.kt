package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.data.category.CategoryDatasouce
import com.gooduckrefactoring.data.category.CategoryRepository

class CategoryViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repository = CategoryRepository(CategoryDatasouce())) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}