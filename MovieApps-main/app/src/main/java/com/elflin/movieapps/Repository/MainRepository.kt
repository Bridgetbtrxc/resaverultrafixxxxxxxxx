package com.elflin.movieapps.Repository

import com.elflin.movieapps.data.EndPointAPI
import com.elflin.movieapps.model.AddExpenseRequest
import javax.inject.Inject
import retrofit2.Response


class MainRepository @Inject constructor(
    private val api : EndPointAPI
) {
//    suspend fun AddExpense(
//        expense_name: String,
//        user_amount: Int,
//        user_category: Int,
//        token: String
//    ): Response<AddExpenseRequest> {
//        val addExpenseRequest = AddExpenseRequest(
//            name = expense_name,
//            amount = user_amount,
//            category_id = user_category
//        )
//        return api.addExpense(token, addExpenseRequest)
//    }

    suspend fun AddExpense(

        expense_name: String,
        expense_amount:Int,
        expense_category:Int,
        token:String)
            =api.addExpense(token,expense_name,expense_amount,expense_category)

    suspend fun getExpense(
        user_month:String,token:String
    )
            = api.displayExpense(token, user_month)


    suspend fun getExpensesForMonth(month: String, token: String) = api.displayExpense(month,token)



//    fun deleteExpense(expenseId: Int, token: String) = api.delete
}