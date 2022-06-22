package com.example.budgettracker

class CashFlowList() {
    private var incomeSourcesList = mutableListOf<CashFlow>(
        CashFlow(
            "123",
            "+",
            "Salary",
            25000,
        ),
        CashFlow(
            "124",
            "+",
            "Project",
            5000,
        )
    )
    private var expenseSourcesList = mutableListOf<CashFlow>(
        CashFlow(
            "111",
            "-",
            "Deductions",
            10000
        ),
        CashFlow(
            id = "112",
            "-",
            "Household necessities",
            3000,
        ),
        CashFlow(
            id = "113",
            "-",
            "Bills",
            2000,
        )
    )

    fun getIncomeList(): List<CashFlow> {
        return incomeSourcesList
    }

    fun getExpenseList(): List<CashFlow> {
        return expenseSourcesList
    }

    fun addIncome(income: CashFlow) {
        incomeSourcesList.add(income)
    }

    fun addExpense(expense: CashFlow) {
        expenseSourcesList.add(expense)
    }
}