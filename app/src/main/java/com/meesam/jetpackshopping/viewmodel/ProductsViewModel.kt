package com.meesam.jetpackshopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meesam.jetpackshopping.events.ProductEvent
import com.meesam.jetpackshopping.model.Product
import com.meesam.jetpackshopping.repository.ProductRepository
import com.meesam.jetpackshopping.states.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var products = MutableStateFlow<AppState<List<Product?>>>(AppState.Loading)
    val _products: StateFlow<AppState<List<Product?>>> = products.asStateFlow()

    private var _productDetail = MutableStateFlow<AppState<Product?>>(AppState.Idle)
    val productDetail : StateFlow<AppState<Product?>> = _productDetail.asStateFlow()
    private var productId = ""

    init {
        getAllProduct()
    }

    fun onEvent(event: ProductEvent){
        when(event){
            is ProductEvent.LoadProductById -> {
                productId = event.id
                getProductById()
            }
        }
    }

    private fun getAllProduct() {
        products.value = AppState.Loading
        viewModelScope.launch {
            try {
                val result = productRepository.getAllProducts()
                products.value = AppState.Success(result)
            } catch (ex: Exception) {
                products.value = AppState.Error(ex.message.toString())
            }
        }
    }

    private fun getProductById(){
        _productDetail.value = AppState.Loading
        viewModelScope.launch {
            try {
                val result = productRepository.getProductById(productId)
                _productDetail.value = AppState.Success(result)

            }catch (ex: Exception){
                _productDetail.value = AppState.Error(ex.message)
            }
        }
    }

}