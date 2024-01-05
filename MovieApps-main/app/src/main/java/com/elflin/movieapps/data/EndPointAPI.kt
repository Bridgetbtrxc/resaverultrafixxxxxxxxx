package com.elflin.movieapps.data

import com.elflin.movieapps.model.AddExpenseRequest
import com.elflin.movieapps.model.ExpenseUpdateRequest
import com.elflin.movieapps.model.GeneralResponse
import com.elflin.movieapps.model.LoginInfo
import com.elflin.movieapps.model.UserResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface EndPointAPI {



    // Login and receive a token
    @FormUrlEncoded
    @POST("createuser")
    suspend fun register(
        @Field("name") user_username:String,
        @Field("email") user_email:String,
        @Field("password") user_password:String
    ): Response<JsonObject>

//    @FormUrlEncoded
//    @POST("addExpense")
//    suspend fun addExpense(
//
//        @Field("name") user_expenseName:String,
//        @Field("amount") user_amount: String,
//        @Field("category_id") user_category: Int,
//        @Header("Authorization") token: String,
//    ): Response<JsonObject>
//    @FormUrlEncoded
//    @POST("login")
//    suspend fun login(
//                      @Field("email") user_email:String,
//                      @Field("password") user_password:String
//    ): Response<LoginRequest>
@FormUrlEncoded
@POST("login")
suspend fun login(@Field("email") user_email:String,
                  @Field("password") user_password:String): Response<String>

    @FormUrlEncoded
    @POST("update")
    suspend fun updateExpense(
        @Header("Authorization") token: String,
        @Body expenseUpdateRequest: ExpenseUpdateRequest
    ): Response<String>

//    suspend fun addExpense(
//
//        expense_name: String,
//        expense_amount:Int,
//        expense_category:Int
//        token:String)
//            =api.addExpense()

    @FormUrlEncoded
    @POST("addExpense")
    suspend fun addExpense(
        @Header("Authorization") token: String,
        @Field("name") expense_name:String,
        @Field("amount") expense_amount:Int,
        @Field("category_id") expense_category:Int
    ): Response<String>


//    @FormUrlEncoded
//    @POST("update")
//    suspend fun updateExpense(
//
//        @Header("Authorization") token: String,
//        @Field("id") expense_id:String,
//        @Field("name") expense_name:String,
//        @Field("amount") expense_amount :String,
//        @Field("name") expense_category:String,
//        @Field("date") expense_date:String
//    ): Response<String>
//



    // Get login information using the token for authentication
    @GET("logininfo")
    suspend fun getLoginInfo(@Header("Authorization") token: String): Response<LoginInfo>

    @FormUrlEncoded
    @POST("displayExpense")

    suspend fun displayExpense(@Header("Authorization") token: String, @Field("month") user_time:String): Response<JsonObject>
//    // Create a new user
//    @FormUrlEncoded
//    @POST("createuser")
//    suspend fun createUser(@Body newUser: CreateUserRequest): Response<UserResponse>

    // Delete a user
    @FormUrlEncoded
    @DELETE("deleteuser")
    suspend fun deleteUser(@Header("Authorization") token: String, @Query("email") email: String): Response<UserResponse>

    // Update a user's details

    @PATCH("updateuser")
    suspend fun updateUser(@Header("Authorization") token: String, @Field("user_username") user_username:String,
                           @Field("user_email") user_email:String,
                           @Field("user_password") user_password:String): Response<UserResponse>

    // Logout the user
    @GET("logout")
    suspend fun logout(@Header("Authorization") token: String): Response<GeneralResponse>
}