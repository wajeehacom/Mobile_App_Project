package com.example.financepal
import Transaction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/TransactionsBackup/backup")
    suspend fun backupTransactions(@Body transactions: List<Transaction>): Response<Unit>
}

