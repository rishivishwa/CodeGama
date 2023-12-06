package com.example.codegama

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiViewModel : RetrofitApi() {


        private val _categories = MutableLiveData<List<ProductCategory>>()
        val categories: LiveData<List<ProductCategory>> get() = _categories

    private val _response = MutableLiveData<ProductListResponse>()
    val response: LiveData<ProductListResponse>get() = _response


    private val _product = MutableLiveData<ProductListResponse>()
    val product: LiveData<ProductListResponse> get() = _product

    init {
            // Initialize the ViewModel with the provided list of categories
            _categories.value = createCategoriesList()

        }

        private fun createCategoriesList(): List<ProductCategory> {
            return listOf(
                ProductCategory("smartphones"),
                ProductCategory("laptops"),
                ProductCategory("fragrances"),
                ProductCategory("skincare"),
                ProductCategory("groceries"),
                ProductCategory("home-decoration"),
                ProductCategory("furniture"),
                ProductCategory("tops"),
                ProductCategory("womens-dresses"),
                ProductCategory("womens-shoes"),
                ProductCategory("mens-shirts"),
                ProductCategory("mens-shoes"),
                ProductCategory("mens-watches"),
                ProductCategory("womens-watches"),
                ProductCategory("womens-bags"),
                ProductCategory("womens-jewellery"),
                ProductCategory("sunglasses"),
                ProductCategory("automotive"),
                ProductCategory("motorcycle"),
                ProductCategory("lighting")
            )
        }
    fun sendDataToApi(category: String) {
        viewModelScope.launch {
            try {
                // Fetch products by category from the API and update the LiveData
                val result = apiService.getProductsByCategory(category)
                _response.value = result
                Log.d("#DATAAPI", "$category")
            } catch (e: Exception) {
                // Handle error, e.g., log the error
            }
        }
    }
    fun removeProduct(product: Product) {
        val currentList = _response.value?.products.orEmpty().toMutableList()
        currentList.remove(product)
        _response.value = ProductListResponse(currentList,4,4,4) // Assuming ApiResponse has a constructor that takes a List<Product>
    }





}