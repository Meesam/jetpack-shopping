package com.meesam.jetpackshopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meesam.jetpackshopping.events.FeedEvents
import com.meesam.jetpackshopping.model.Category
import com.meesam.jetpackshopping.repository.CategoryRepository
import com.meesam.jetpackshopping.states.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {

    private var _categories = MutableStateFlow<AppState<List<Category>>>(AppState.Loading)
    val categories: StateFlow<AppState<List<Category>>> = _categories.asStateFlow()

    init {
        getAllCategory()
    }

    fun onEvent(events: FeedEvents) {
        when (events) {
            is FeedEvents.onCategorySelect -> {
                viewModelScope.launch {
                    val currentState = _categories.value
                    if (currentState is AppState.Success) {
                        val currentCategory = currentState.data
                        val updatedCategory = currentCategory.map { category ->
                            if (events.categoryName == category.name) {
                                category.copy(isSelected = !category.isSelected)
                            }else {
                                category
                            }
                        }
                        _categories.value = AppState.Success(updatedCategory)
                    }
                }
            }
        }
    }


    private fun getAllCategory() {
        _categories.value = AppState.Loading
        viewModelScope.launch {
            try {
                val allCategory = categoryRepository.getAllCategory()
                _categories.value = AppState.Success(allCategory)
            } catch (ex: Exception) {
                _categories.value = AppState.Error(ex.toString())
            }
        }
    }

}