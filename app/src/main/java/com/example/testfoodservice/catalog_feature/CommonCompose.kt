package com.example.testfoodservice.catalog_feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testfoodservice.R

@Composable
fun CounterRow(
    amount: Int,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Button(
            onClick = { onDecrement() },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(32.dp)
        ) {
            Text("-")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$amount", modifier = Modifier.widthIn(min = 24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { onIncrement() },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(32.dp)
        ) {
            Text("+")
        }
    }
}

@Composable
fun LoadIcon(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    alignment: Alignment = Alignment.Center,
    placeholder: Painter = painterResource(R.drawable.ic_discount),
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        Image(
            painter = placeholder,
            contentDescription = contentDescription,
            modifier = modifier
                .size(48.dp)
                .align(Alignment.TopStart) // Выравнивание в левом верхнем углу
                .padding(8.dp),
        )
    }
}

@Composable
fun LoadImage(
    modifier: Modifier,
    contentDescription: String = "",
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillWidth,
    placeholder: Painter = painterResource(R.drawable.placeholder),
) {

    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        Image(
            painter = placeholder,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}

fun Int.decreaseBy100() = this / 100