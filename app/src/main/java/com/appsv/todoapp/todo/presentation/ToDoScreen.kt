package com.appsv.todoapp.todo.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.appsv.todoapp.R
import com.appsv.todoapp.core.presentation.component.ToDoDialog
import com.appsv.todoapp.core.util.Priority
import com.appsv.todoapp.todo.domain.model.ToDoUI
import com.appsv.todoapp.todo.presentation.components.ToDoItem
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import  androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
    state : ToDoState ,
    events : (ToDoEvents) -> Unit ,

) {
    val scope = rememberCoroutineScope()

    var showToDoDailog = rememberSaveable{ mutableStateOf(false) }
    var isEditMode = rememberSaveable { mutableStateOf(false) }

    var selectedTitle by rememberSaveable { mutableStateOf("") }
    var selectedDescription by rememberSaveable { mutableStateOf("") }
    var selectedPriority by rememberSaveable { mutableStateOf(Priority.LOW) }
    var selectedId by rememberSaveable { mutableStateOf("") }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.Dark_Blue)),
        contentAlignment = Alignment.Center

    ){

        if(state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        else if(state.toDoList.isNotEmpty()){
            LazyColumn (
                modifier = Modifier.fillMaxSize() ,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                items(state.toDoList, key = { it.id!! }){ currentToDoUiItem ->
                    ToDoItem(todoUI = currentToDoUiItem){
                        showToDoDailog.value = true
                        selectedId = currentToDoUiItem.id!!
                        selectedTitle = currentToDoUiItem.title
                        selectedDescription = currentToDoUiItem.description
                        selectedPriority = currentToDoUiItem.priority
                        isEditMode.value = true


                    }
                }
            }
        }
        else{
            Text("No ToDo items found",
                color = colorResource(R.color.Light_Blue)
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .size(110.dp)
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                showToDoDailog.value = true ;

            },
            containerColor = colorResource(R.color.Light_Blue),

        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
        if(showToDoDailog.value){
            ToDoDialog(
                isEditMode = isEditMode.value,
                onDismiss = {
                    showToDoDailog.value = false
                },
                onAddToDo = {

                            title,description,priority ->

                    scope.launch {

                        val toDoUI = ToDoUI(
                            title = title,
                            description = description,
                            priority = priority
                        )

                        events(ToDoEvents.SaveToDo(toDoUI))
                    }
                    showToDoDailog.value = false;

                },
                onDeleteToDo = {
                    scope.launch {
                        events(ToDoEvents.DeleteToDo(selectedId))
                    }
                    showToDoDailog.value = false;

                },
                onUpdateToDo = {title,description,priority ->
                    scope.launch {
                        val toDoUI = ToDoUI(
                            id = selectedId,
                            title = title,
                            description = description,
                            priority = priority
                        )
                        events(ToDoEvents.UpdateToDo(toDoUI))
                    }
                    showToDoDailog.value = false
                },
                existingTitle = selectedTitle,
                existingDescription = selectedDescription,
                exitingPriority = selectedPriority
            )
        }
    }
}