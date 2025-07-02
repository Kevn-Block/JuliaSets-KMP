package io.sparkedember.juliaset.components

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.Button
import com.composeunstyled.Text

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 18.sp,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }
    var textColor by remember { mutableStateOf(Color.Black) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            when (it) {
                is HoverInteraction.Exit,
                is PressInteraction.Cancel,
                is PressInteraction.Release -> {
                    backgroundColor = Color.Transparent
                }
                is HoverInteraction.Enter,
                is PressInteraction.Press -> {
                    backgroundColor = Color.LightGray
                }
            }
        }
    }

    Button(
        modifier = modifier
            .hoverable(interactionSource),
        interactionSource = interactionSource,
        indication = ripple(),
        onClick = onClick,
        borderColor = Color.Gray,
        borderWidth = 1.dp,
        backgroundColor = backgroundColor,
        contentColor = textColor,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(6.dp)
    ){
        Text(text, fontSize = fontSize)
    }
}