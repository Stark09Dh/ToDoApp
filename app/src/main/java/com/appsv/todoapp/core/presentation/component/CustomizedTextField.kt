package com.appsv.todoapp.core.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.appsv.todoapp.R

@Composable
fun CustomizedTextField(
    modifier: Modifier = Modifier,
    text : String ,
    label : String,
    onValuechange : (String) -> Unit,
    enabled : Boolean,
    supportingText : String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions : KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        colors =  OutlinedTextFieldDefaults.colors().copy(
            focusedTextColor = colorResource(R.color.Light1_Blue),
            unfocusedTextColor = colorResource(R.color.Light1_Blue),
            focusedContainerColor = colorResource(R.color.Dark_Blue),
            unfocusedContainerColor = colorResource(R.color.Dark_Blue),
            focusedLeadingIconColor = colorResource(R.color.Light_Blue),
            unfocusedLeadingIconColor = colorResource(R.color.Light_Blue),
            focusedIndicatorColor = colorResource(R.color.Light_Blue),
            unfocusedIndicatorColor = colorResource(R.color.Light_Blue),
            focusedLabelColor = colorResource(R.color.Light_Blue),
            unfocusedLabelColor = colorResource(R.color.Light_Blue),
            cursorColor = colorResource(R.color.Light_Blue),
            disabledTextColor = Color.LightGray,
            disabledLabelColor = Color.LightGray,
            disabledIndicatorColor = Color.LightGray
        ),

        onValueChange = {onValuechange(it)},
        enabled = enabled,
        label = {Text(text = label)} ,
        keyboardOptions = keyboardOptions ,
        keyboardActions = keyboardActions,
        supportingText = {Text(
            text = supportingText,
            color = Color.Red,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        }

    )

}