package com.thomas.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppBackground = Color(0xFFF4F6FA)
private val Navy = Color(0xFF172554)
private val Green = Color(0xFF15803D)
private val Red = Color(0xFFDC2626)
enum class TransactionType {
    INCOME,
    EXPENSE
}
data class Transaction(
    val title: String,
    val category: String,
    val amount: Double,
    val type: TransactionType
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) 

        setContent {
            MaterialTheme {
                ExpenseDashboard()
            }
        }
    }
}

@Composable
fun TransactionRow(
    title: String,
    category: String,
    amount: String,
    amountColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = Navy
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = category,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }

        Text(
            text = amount,
            color = amountColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ExpenseDashboard(

) {

    val transactions = listOf(
        Transaction("Supermarket", "Φαγητό", 65.0, TransactionType.EXPENSE),
        Transaction("Μισθός", "Έσοδα", 1500.0, TransactionType.INCOME),
        Transaction("Καύσιμα", "Μεταφορές", 45.0, TransactionType.EXPENSE),
        Transaction("Καφές", "Φαγητό", 3.5, TransactionType.EXPENSE),)

    val income = transactions
        .filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }

    val expenses = transactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }

    val balance = income - expenses
    val formattedBalance = String.format("%.2f", balance)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "ExpenseTracker",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Τα οικονομικά σου, με μια ματιά",
                color = Color.DarkGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Navy,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
            ) {
                Text(
                    text = "Συνολικό υπόλοιπο",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "€$formattedBalance",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Έσοδα",
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "+ €$income",
                            color = Color(0xFF86EFAC),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column {
                        Text(
                            text = "Έξοδα",
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "- €$expenses",
                            color = Color(0xFFFCA5A5),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Πρόσφατες συναλλαγές",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(modifier = Modifier.height(16.dp))

            transactions.forEach { transaction ->
                TransactionRow(
                    title = transaction.title,
                    category = transaction.category,
                    amount = transaction.amount.toString(),
                    amountColor = if (transaction.type == TransactionType.INCOME) Green else Red
                )
        }
    }
}}



