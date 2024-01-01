package com.example.alp_resaver.view

import TransactionNameDialog
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elflin.movieapps.R
import com.elflin.movieapps.ui.theme.MovieAppsTheme
import kotlinx.coroutines.CoroutineScope


@Composable
fun Topup2(navController: NavController){

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(
                color =  Color(0xFF5A45FE)
            )
    ) {
        TopBar2(navController = navController)
        Calculator2(context = context, coroutineScope = coroutineScope, navController = navController)
    }
}



@Composable
fun TopBar2( navController: NavController) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ){

        Image(

            painter = painterResource(id = R.drawable.chevron_left_line),
            contentDescription = "",
            modifier = Modifier
                .size(25.dp)
                .aspectRatio(1f)
                .fillMaxHeight()
                .clickable {
                    navController.popBackStack()
                }
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "Edit Transaction",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Image(

            painter = painterResource(id = R.drawable.question_circle_line),
            contentDescription = "",
            modifier = Modifier
                .size(25.dp)
                .aspectRatio(1f)
                .fillMaxHeight()
        )

    }
}

@Composable
fun Calculator2(context: Context, coroutineScope: CoroutineScope, navController: NavController) {

    val categories = listOf("Leisure", "Figma", "Groceries")
    var input_number by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    val currencyOptions = listOf(
        Pair("Rp", R.drawable.bridgetturu),
    )

    var selectedCurrency by remember { mutableStateOf(currencyOptions.first()) }

    var showDialog by remember { mutableStateOf(false) }
    var transactionName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 30.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Amount",
                fontSize = 16.sp,
            )

            CustomTextFieldWithImageAndDropdown2(
                value = input_number,
                onValueChanged = { input_number = it },
                currencyOptions = currencyOptions,
                selectedCurrency = selectedCurrency,
                onCurrencySelected = { selectedCurrency = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )


            Text(
                text = "Category",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )

            CustomDropdownMenu2(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(3) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) { columnIndex ->
                            CalculatorKey2(
                                text = (rowIndex * 3 + columnIndex + 1).toString(),
                                onClick = { input_number += (rowIndex * 3 + columnIndex + 1).toString() }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CalculatorKey2(text = "*", onClick = { input_number += "0" })
                    CalculatorKey2(text = "0", onClick = { input_number += "." })
                    CalculatorKey2(text = "X", onClick = { input_number = "" })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Continue",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 1.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFD2F801), RoundedCornerShape(16.dp))
                    .padding(horizontal = 135.dp, vertical = 15.dp)
                    .clickable {
                        showDialog = true
                    }
            )

            if (showDialog) {
                TransactionNameDialog2(
                    onDismiss = { showDialog = false },
                    onConfirm = { name ->

                        transactionName = name
                        showDialog = false
                    },
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionNameDialog2(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    navController: NavController
) {
    var transactionName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Update Transaction Name") },
        text = {
            OutlinedTextField(

                value = transactionName,
                onValueChange = {

                    transactionName = it
                },
                label = { Text("Transaction Name") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {

                    onConfirm(transactionName)
                    navController.popBackStack()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}




@Composable
fun CalculatorKey2(text: String, onClick: () -> Unit) {

    Box(

        modifier = Modifier
            .height(65.dp)
            .width(115.dp)
            .clickable { onClick() }
            .padding(8.dp)
            .background(Color(0xC4F3F3F3), shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldWithImageAndDropdown2(
    value: String,
    onValueChanged: (String) -> Unit,
    currencyOptions: List<Pair<String, Int>>,
    selectedCurrency: Pair<String, Int>,
    onCurrencySelected: (Pair<String, Int>) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)

            ) {
                Text(
                    text = selectedCurrency.first,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .weight(1f)
                )
                DropdownIcon2(
                    currencyOptions = currencyOptions,
                    selectedCurrency = selectedCurrency,
                    onCurrencySelected = onCurrencySelected,
                    modifier = Modifier.size(25.dp).align(Alignment.CenterVertically)
                )

            }
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Black
        )
    )
}

@Composable
fun DropdownIcon2(
    currencyOptions: List<Pair<String, Int>>,
    selectedCurrency: Pair<String, Int>,
    onCurrencySelected: (Pair<String, Int>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clickable { expanded = !expanded }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = selectedCurrency.second),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = selectedCurrency.first,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )

        }

        if (expanded) {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
            ) {
                currencyOptions.forEach { currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCurrencySelected(currency)
                                expanded = false
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = currency.second),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            text = currency.first,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu2(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selectedCategory, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }

        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                Column {
                    categories.forEach { category ->
                        Text(
                            text = category,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCategorySelected(category)
                                    expanded = false
                                }
                                .padding(16.dp)
                        )
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

