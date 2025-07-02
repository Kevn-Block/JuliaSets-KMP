package io.sparkedember.juliaset.settings.types

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.composeunstyled.TextField
import com.composeunstyled.TextInput
import io.sparkedember.juliaset.settings.components.BaseRowSetting
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NumberSetting(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    initialNumber: String,
    defaultValue: String,
    onNumberChanged: (Int) -> Unit
) {
    var error: String? by remember { mutableStateOf(null) }
    Column {
        BaseRowSetting(
            modifier = modifier,
            title = title,
            description = description,
            defaultValue = defaultValue
        ) {
            NumberEditor(
                initialNumber = initialNumber,
                onNumberChanged = onNumberChanged,
                onError = { error = it }
            )
        }
        error?.let { Text(it, color = Color.Red) }
    }
}

@Composable
private fun NumberEditor(
    initialNumber: String,
    onNumberChanged: (Int) -> Unit,
    onError: (String?) -> Unit
) {
    var textInput by remember(initialNumber) { mutableStateOf(initialNumber) }
    TextField(
        modifier = Modifier
            .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(4.dp))
            .padding(4.dp),
        value = textInput,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        onValueChange = {
            textInput = it
            val parsedInput = it.toIntOrNull()
            val error = errorFromNumber(it)
            if (error == null && parsedInput != null) {
                onNumberChanged(parsedInput)
            }
            onError(error)
        },
    ) {
        TextInput(
            placeholder = { Text("") }
        )
    }
}

private fun errorFromNumber(input: String): String? {
    val number = input.toIntOrNull()
    if (number == null) {
        return "Enter a valid number"
    } else if (number <= 0) {
        return "Number must be greater 0"
    }
    return null
}


@Composable
@Preview
fun NumberSetting_Preview() {
    NumberSetting(
        modifier = Modifier.background(Color.White),
        title = "This is neat",
        description = "This does something really neat. You should enter a number.",
        initialNumber = "50",
        defaultValue = "500",
        onNumberChanged = {},
    )
}