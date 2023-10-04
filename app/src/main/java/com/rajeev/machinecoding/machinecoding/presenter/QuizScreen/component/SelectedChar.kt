package com.rajeev.machinecoding.machinecoding.presenter.QuizScreen.component

import android.graphics.Rect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun SelectedChar(
    char: Char? = null,
    modifier: Modifier = Modifier,
    onCharSelected: Boolean = false,
    onSelect: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RectangleShape).background(Color.White)
            .border(
                width = if(onCharSelected) 5.dp else 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RectangleShape
            )
            .clickable {
                onSelect()
            }
    ){
        char?.let {
            Text(
                text = it.toString(),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.
                align(Alignment.Center)
            )
        }
    }
}
