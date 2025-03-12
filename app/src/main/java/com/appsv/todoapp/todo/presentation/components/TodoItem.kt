package com.appsv.todoapp.todo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.todoapp.R
import com.appsv.todoapp.core.util.Priority
import com.appsv.todoapp.todo.domain.model.ToDoUI

//@Preview
//@Composable
//fun Preview(modifier: Modifier = Modifier) {
//    ToDoItem(
//        todoUI = ToDoUI(
//            id = "",
//            title = "Title",
//            description = "Description",
//            priority = Priority.MEDIUM,
//            dateAdded = "06 July, 5:41 am, 2025"
//        )
//    )
//}


@Composable
fun ToDoItem(
    modifier: Modifier = Modifier ,
    todoUI: ToDoUI,
    onItemClick : () -> Unit
) {
    val containerColor = when(todoUI.priority){
        Priority.LOW -> colorResource(R.color.Green)
        Priority.MEDIUM -> colorResource(R.color.Medium_Blue)
        Priority.HIGH -> colorResource(R.color.Red)
    }
    Card (
        modifier = modifier.fillMaxWidth().clickable { onItemClick() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ){
        Column (
            modifier = modifier.padding(8.dp)

        ){

            // title
            Text(text = todoUI.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if(todoUI.description != ""){
                Spacer(modifier = modifier.height((10.dp)))

                //description
                Text(text = todoUI.description,
                    fontSize = 18.sp,
                    color = Color.White,
                    maxLines = 1 ,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = modifier.height((15.dp)))
            //date
            Text(modifier = modifier.fillMaxWidth(),
                text = todoUI.dateAdded!!,
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.End
            )

        }
    }
}

