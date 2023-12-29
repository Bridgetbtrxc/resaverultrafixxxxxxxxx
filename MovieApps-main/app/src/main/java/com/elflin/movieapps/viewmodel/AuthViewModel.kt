package com.elflin.movieapps.viewmodel
import com.google.gson.JsonParser
import android.content.Context
import android.util.Log
import okhttp3.ResponseBody

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.viewModelScope
import com.elflin.movieapps.Application
import com.elflin.movieapps.MainActivity


import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elflin.movieapps.Repository.AuthRepository
import com.elflin.movieapps.model.LoginRequest
import com.google.gson.Gson
import com.google.gson.JsonObject


import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel(){


    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    val _register: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn



    private fun checkLoginStatus(context: Context) {
        viewModelScope.launch {
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", "")
            _isLoggedIn.value = !token.isNullOrEmpty()
        }
    }

    fun onLoggedOut() {
        _isLoggedIn.value = false
    }



    val _login: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val _logout: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val register: LiveData<String>
        get() = _register

    val login : LiveData<String>
        get()=_login

    val logout: LiveData<String>
        get()=_logout

    fun registeruser(user_username: String, user_email: String, user_password: String)
    = viewModelScope.launch{
        repo.registerUser(user_username, user_email, user_password).let{
            response ->
            if(response.isSuccessful){
                _register.value = "user succesfully registered"
                Log.d("Register user", response.body().toString())
            } else {
                _register.value = response.message()
                Log.e("Register User", response.message())
            }
        }
    }



    fun logoutuser(user_token: String, context: Context)
    = viewModelScope.launch{
        clearToken(context)
        onLoggedOut()
        repo.logoutUser(user_token)
        _logout.value = "User successfully logged out"
    }

// In your AuthViewModel class

    fun getToken(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // This will return an empty string if the token does not exist
        return sharedPreferences.getString("token", "") ?: ""
    }


    private fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("token", token).apply()
    }

    private fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("token").apply()
    }



    fun logoutuser(context: Context) = viewModelScope.launch {
        clearToken(context) // Clear token
        _logout.value = "User successfully logged out"
        _isLoggedIn.value = false
        _userEmail.value = ""
    }



    fun loginUser(user_email: String, user_password: String, context: Context) = viewModelScope.launch {
        try {
            val response = repo.loginUser(user_email, user_password)
            if (response.isSuccessful) {
                // Parse the JSON response to get the token
                val tokenJson = response.body()?.string().orEmpty()
                val token = JsonParser.parseString(tokenJson).asJsonObject.get("token").asString

                if (token.isNotEmpty()) {
                    saveToken(context, token)
                    _isLoggedIn.value = true
                    _userEmail.value = user_email
                    _login.value = "User successfully logged in"
                } else {
                    _login.value = "Error: Empty token"
                }
            } else {
                _login.value = "Error: " + response.errorBody()?.string()
            }
        } catch (e: Exception) {
            _login.value = "Exception: " + e.message
        }
    }






//    fun loginUser(user_email: String, user_password: String) = viewModelScope.launch {
//        val loginRequest = LoginRequest(user_email, user_password)
//        try {
//            val response = repo.loginUser(user_email,user_password)
//            if (response.isSuccessful && response.body() != null) {
//                _login.value = "User successfully logged in"
//                Log.d("Login user", response.body().toString())
//            } else {
//                _login.value = "Error: " + response.errorBody()?.string()
//                Log.e("Login User Error", response.errorBody()?.string() ?: "Unknown Error")
//            }
//        } catch (e: Exception) {
//            _login.value = "Exception: " + e.message
//            Log.e("Login User Exception", e.message ?: "Unknown Exception")
//        }
//    }

}