package com.elflin.movieapps.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.viewModelScope


import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import com.elflin.movieapps.Repository.MainRepository
import com.elflin.movieapps.data.DataStoreManager



import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _selectedCategoryId = MutableLiveData<Int>()
    val selectedCategoryId: LiveData<Int> = _selectedCategoryId
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _categoryId = MutableLiveData<Int>()
    val categoryId: LiveData<Int> = _categoryId

    init {
        viewModelScope.launch {
            dataStore.getToken.collect { tokenValue ->
                _token.value = tokenValue
            }
        }
    }
    fun setSelectedCategory(categoryId: Int) {
        _selectedCategoryId.value = categoryId
    }

//    private val _expenses = MutableLiveData<List<Expense>>()
//    val expenses: LiveData<List<Expense>> = _expenses

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    fun getExpensesForMonth(month: String, token: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = mainRepository.getExpensesForMonth(month, token)
//                if (response.isSuccessful && response.body() != null) {
//                    _expenses.value = listOf(response.body())
//                } else {
//                    _responseMessage.value = "Failed to fetch expenses"
//                }
//            } catch (e: Exception) {
//                _responseMessage.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    // Inside MainViewModel
    fun getCategoryId(categoryName: String): Int {
        return when (categoryName) {
            "Needs" -> 1
            "Wants" -> 2
            "Savings" -> 3
            else -> -1 // Invalid category
        }



    }

    fun AddExpense(name: String, amount: Int, categoryId: Int, token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.AddExpense(name,amount,categoryId,token)
                Log.e("AddExpense1", response.toString())
                // Handle the response here
            } catch (e: Exception) {

                _responseMessage.value = "Error: ${e.message}"

                Log.e("AddExpense2", "aaa")
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun AddExpense(name: String, amount: String, categoryId: Int, token: String) {
//
//        _isLoading.value = true
//        viewModelScope.launch {
//            val categoryId = getCategoryID(categoryName)
//            try {
//                val response = mainRepository.AddExpense(name,token,categoryId,amount,categoryId)
//                if (response.isSuccessful && response.body() != null) {
//
//                } else {
//                    _responseMessage.value = "Failed to add expense"
//                }
//            } catch (e: Exception) {
//                _responseMessage.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

//    fun updateExpense(expenseId: Int, name: String, amount: Double, categoryId: Int, date: String, token: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = mainRepository.updateExpense(expenseId, name, amount, categoryId, date, token)
//                if (response.isSuccessful && response.body() != null) {
//                    _responseMessage.value = response.body()?.message ?: "Expense updated successfully"
//                } else {
//                    _responseMessage.value = "Failed to update expense"
//                }
//            } catch (e: Exception) {
//                _responseMessage.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

//    fun deleteExpense(expenseId: Int, token: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = mainRepository.deleteExpense(expenseId, token)
//                if (response.isSuccessful && response.body() != null) {
//                    _responseMessage.value = response.body()?.message ?: "Expense deleted successfully"
//                } else {
//                    _responseMessage.value = "Failed to delete expense"
//                }
//            } catch (e: Exception) {
//                _responseMessage.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    // Additional methods can be implemented for other functionalities like
    // getting total expenses, budget percentages, etc., based on your API structure.

}

//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val repo: MainRepository,
//    private val dataStore : DataStoreManager
//
//): ViewModel(){
//
//    private val _expenseName = MutableLiveData<List<Expense>>()
//    val expenseName: MutableLiveData<List<Expense>> get() = _expenseName
//
//
////    user_amount:String,
////    @Field("category") user_category:String,
////    @Header("Authorization") token: String,
//// LiveData to hold the response message
//private val _responseMessage = MutableLiveData<String>()
//    val responseMessage: LiveData<String> = _responseMessage
//
//    // LiveData to handle loading state
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private fun getCategoryID(categoryName: String): Int {
//        return when (categoryName) {
//            "Needs" -> 1
//            "Wants" -> 2
//            "Savings" -> 3
//            else -> -1 // Invalid category
//        }
//    }
//
//    // Function to add an expense
//    fun addExpense(expenseName: String, categoryName: String, amount: String, token: String) {
//        val categoryID = getCategoryID(categoryName)
//        if (categoryID == -1) {
//            _responseMessage.value = "Invalid category selected"
//            return
//        }
//
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = repo.AddExpense(expenseName, categoryID.toString(), amount, token)
//                if (response.isSuccessful && response.body() != null) {
//                    // Assuming the response body contains a message field
//                    _responseMessage.value = response.body()?.get("message")?.asString ?: "Expense added successfully"
//                } else {
//                    _responseMessage.value = "Failed to add expense"
//                }
//            } catch (e: Exception) {
//                _responseMessage.value = "Error: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
////    fun addExpense(expense_name:String,user_category: String, user_amount: String, token: String)
////            = viewModelScope.launch{
////        repo.AddExpense(expense_name,user_category, user_amount, token).let{
////                response ->
////            if(response.isSuccessful){
////                if(response.body()?.get("message")!!.asString=="Expense added successfully"){
////                    _expenseName.value = response.body()?.get("message")!!.asString
////
////                }
////            } else {
////
////            }
////        }
////}
//
//
//
