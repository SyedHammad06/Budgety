package com.example.budgettracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracker.ui.theme.BudgetTrackerTheme
import java.text.NumberFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.secondary) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun ListItem(item: CashFlow, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier.width(200.dp)) {
            Text(
                text = item.description,
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 20.sp,
                modifier = modifier.padding(start = 10.dp),
            )
        }
        Column {
            Text(
                text = item.amount.toString(),
                fontSize = 20.sp,
            )
        }
        Column {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "delete icon"
                )
            }
        }
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Menu Icon",
            tint = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.size(36.dp),
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h1
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "User Icon",
            tint = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun TopSection(
    totalIncome: Int,
    totalExpense: Int,
    setTotalIncome: (Int) -> Unit,
    setTotalExpense: (Int) -> Unit,
    typeInput: String,
    onTypeInputChanged: (String) -> Unit,
    descriptionInput: String,
    onDescriptionInputChanged: (String) -> Unit,
    amountInput: String,
    onAmountInputChanged: (String) -> Unit,
    incomeList: List<CashFlow>,
    expenseList: List<CashFlow>,
    incomeListUpdate: (List<CashFlow>) -> Unit,
    expenseListUpdate: (List<CashFlow>) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val budget = totalIncome - totalExpense

    val formattedBudget = NumberFormat
        .getCurrencyInstance(Locale("en", "in")).format(budget)
    val formattedIncome = NumberFormat
        .getCurrencyInstance(Locale("en", "in")).format(totalIncome)
    val formattedExpense = NumberFormat
        .getCurrencyInstance(Locale("en", "in")).format(totalExpense)
    val focusManager = LocalFocusManager.current

    val balance = if (budget >= 0) {
        "+ $formattedBudget"
    } else {
        "- $formattedBudget"
    }

    Card(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(630.dp)
    ) {
        Column(
            modifier = modifier.padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Column {
                    Text(
                        text = stringResource(R.string.current_balance),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = balance,
                        fontSize = 46.sp,
                        style = MaterialTheme.typography.body2,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Row {
                Column {
                    Text(
                        text = stringResource(R.string.total_income),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = "+ $formattedIncome",
                        style = MaterialTheme.typography.body2,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.total_expense),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = "- $formattedExpense",
                        style = MaterialTheme.typography.body2,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Row(modifier = modifier.fillMaxWidth()) {
                Column {
                    Row {
                        Card(
                            modifier = modifier
                                .width(80.dp)
                                .height(60.dp)
                                .clickable { expanded = !expanded },
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                        ) {
                            Text(
                                text = typeInput,
                                style = MaterialTheme.typography.h2,
                                color = MaterialTheme.colors.secondaryVariant,
                                modifier = modifier.wrapContentSize(Alignment.Center)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(onClick = {
                                    onTypeInputChanged("+")
                                    expanded = false
                                } ) {
                                    Text("+")
                                }
                                DropdownMenuItem(onClick = {
                                    onTypeInputChanged("-")
                                    expanded = false
                                } ) {
                                    Text("-")
                                }
                            }
                        }
                        TextField(
                            value = descriptionInput,
                            onValueChange = onDescriptionInputChanged,
                            modifier = modifier
                                .padding(start = 10.dp)
                                .height(60.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Enter Description",
                                    fontSize = 20.sp)
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant,
                                textColor = MaterialTheme.colors.secondaryVariant,
                                focusedLabelColor = MaterialTheme.colors.secondaryVariant
                            )
                        )
                    }
                    Spacer(modifier = modifier.height(10.dp))
                    Row {
                        TextField(
                            value = amountInput,
                            onValueChange = onAmountInputChanged,
                            modifier = modifier
                                .padding(end = 10.dp)
                                .weight(2f)
                                .height(60.dp),
                            placeholder = {
                                Text(
                                    text = "Enter Amount",
                                    fontSize = 20.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant,
                                textColor = MaterialTheme.colors.secondaryVariant,
                                focusedLabelColor = MaterialTheme.colors.secondaryVariant
                            )
                        )
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                if (typeInput == "+") {
                                    val income = CashFlow(
                                        id = UUID.randomUUID().toString(),
                                        type = "+",
                                        description = descriptionInput,
                                        amount = amountInput.toInt()
                                    )
                                    setTotalIncome(totalIncome + amountInput.toInt())
                                    incomeListUpdate(incomeList + listOf(income))
                                    onDescriptionInputChanged("")
                                    onAmountInputChanged("")
                                } else if (typeInput == "-") {
                                    val expense = CashFlow(
                                        id = UUID.randomUUID().toString(),
                                        type = "-",
                                        description = descriptionInput,
                                        amount = amountInput.toInt()
                                    )
                                    setTotalExpense(totalExpense + amountInput.toInt())
                                    expenseListUpdate(expenseList + listOf(expense))
                                    onDescriptionInputChanged("")
                                    onAmountInputChanged("")
                                }
                            },
                            modifier = modifier
                                .weight(1f)
                                .height(60.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                            )
                        ) {
                            Text(
                                text = "Submit",
                                color = MaterialTheme.colors.secondaryVariant,
                                fontSize = 20.sp,
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(10.dp))
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_horizontal_rule),
                            contentDescription = "Horizontal Rule Icon",
                            tint = MaterialTheme.colors.primaryVariant,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSection(
    incomeList: List<CashFlow>,
    expenseList: List<CashFlow>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = modifier.padding(bottom = 15.dp)) {
            Text(
                text = stringResource(R.string.income_subheading),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary
            )
        }
        Row(modifier = modifier.padding(bottom = 25.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                incomeList.forEach { income ->
                    ListItem(item = income)
                }
            }
        }
        Row(modifier = modifier.padding(bottom = 15.dp)) {
            Text(
                text = stringResource(R.string.expense_subheading),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary
            )
        }
        Row {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                expenseList.forEach { income ->
                    ListItem(item = income)
                }
            }
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.footer_text),
            fontSize = 24.sp,
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h2
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen() {
    var totalIncome by remember { mutableStateOf( 0) }
    var totalExpense by remember { mutableStateOf(0) }
    var typeInput by remember { mutableStateOf("+" )}
    var descriptionInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    var incomeList by mutableStateOf(listOf<CashFlow>())
    var expenseList by mutableStateOf(listOf<CashFlow>())

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar() }
    ) {
        Column (
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .background(color = MaterialTheme.colors.secondary)
        ) {
            Row () {
                TopSection(
                    totalIncome,
                    totalExpense,
                    {totalIncome = it},
                    {totalExpense = it},
                    typeInput,
                    {typeInput = it},
                    descriptionInput,
                    {descriptionInput = it},
                    amountInput,
                    {amountInput = it},
                    incomeList,
                    expenseList,
                    {incomeList = it},
                    {expenseList = it}
                )
            }
            Row {
                BottomSection(incomeList = incomeList, expenseList = expenseList)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BudgetTrackerTheme {
        MainScreen()
    }
}