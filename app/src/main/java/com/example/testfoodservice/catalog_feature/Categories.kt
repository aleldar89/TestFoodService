package com.example.testfoodservice.catalog_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.models_api.category.Category

@Composable
fun CategoryButton(
    category: Category,
    onClick: () -> Unit
) {
    val buttonState = remember { mutableStateOf(false) }

    Button(
        onClick = { buttonState.value = !buttonState.value },
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (buttonState.value) Color(0xFFFFA500) else Color.White, // Orange color when pressed
            disabledContentColor = Color.Gray
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        content = {
            Row(
                modifier = Modifier.background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    fontSize = 16.sp,
                    color = if (buttonState.value) Color.White else Color.Black
                )
            }
        }
    )
}


@Composable
fun CategoryRow(categories: List<Category>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(categories.size) { index ->
            CategoryButton(category = categories[index]) {}
        }
    }
}

//@Composable
//fun CategoryButton(
//    category: Category,
//    onClick: () -> Unit
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier.padding(8.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.White,
//            disabledContentColor = Color.Gray
//        )
//    ) {
//        Row(
//            modifier = Modifier
//                .background(Color.Transparent)
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            Text(
//                text = category.name,
//                fontSize = 16.sp,
//                color = Color.Black
//            )
//        }
//    }
//}