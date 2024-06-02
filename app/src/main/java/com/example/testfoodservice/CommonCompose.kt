package com.example.testfoodservice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.testfoodservice.ui.theme.LightGrey
import com.example.testfoodservice.ui.theme.Orange
import com.example.testfoodservice.ui.theme.White

@Composable
fun AmountButtons(
    modifier: Modifier = Modifier,
    amount: Int,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {},
    color: Color = LightGrey
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Button(
            onClick = { onDecrement() },
            modifier = modifier
                .weight(1f)
                .aspectRatio(1f),
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
            colors = ButtonDefaults.buttonColors(color)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_minus),
                contentDescription = "",
                tint = Orange,
                modifier = modifier.scale(3f)
            )
        }

        Text(
            text = "$amount",
            modifier = modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { onIncrement() },
            modifier = modifier
                .weight(1f)
                .aspectRatio(1f),
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
            colors = ButtonDefaults.buttonColors(color)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "",
                tint = Orange,
                modifier = modifier.scale(3f)
            )
        }
    }
}

@Composable
fun LoadIcon(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    alignment: Alignment = Alignment.TopStart,
    placeholder: Painter = painterResource(R.drawable.ic_discount),
) {
    Image(
        painter = placeholder,
        contentDescription = contentDescription,
        modifier = modifier
            .size(dimensionResource(R.dimen.icon_size_48))
            .padding(dimensionResource(R.dimen.padding_30)),
        alignment = alignment
    )
}

@Composable
fun LoadImage(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.FillWidth,
    placeholder: Painter = painterResource(R.drawable.placeholder),
) {
    Image(
        painter = placeholder,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun WideButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    shape: Shape = RoundedCornerShape(dimensionResource(R.dimen.corner_size)),
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = shape,
    ) {
        Text(
            text = text,
            color = White,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

fun Int.decreaseBy100() = this / 100