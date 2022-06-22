package com.example.budgettracker

data class CashFlow(
    val id: String,
    var type: String,
    val description: String,
    val amount: Int,
)
