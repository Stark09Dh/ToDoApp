package com.appsv.todoapp.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.todoapp.R
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.appsv.todoapp.core.util.Priority
@Preview(showBackground = true)
@Composable
fun ToDoDialogPreview(modifier: Modifier = Modifier) {
    ToDoDialog(
        onDismiss = {},
        onAddToDo = {_,_,_ ,->},
        onDeleteToDo = {},
        onUpdateToDo = {_,_,_ ->},
        existingTitle = "",
        existingDescription =  "",
        exitingPriority=  Priority.LOW
    )
}

@OptIn(ExperimentalComposeApi::class)
@Composable
fun ToDoDialog(
    onDismiss : () -> Unit,
    isEditMode : Boolean = false,
    onAddToDo : (String, String, Priority) -> Unit,

    onUpdateToDo : (String, String, Priority) -> Unit,
    onDeleteToDo : () -> Unit,
    existingTitle : String = "",
    existingDescription : String = "",
    exitingPriority : Priority
)
{
    Dialog(
      onDismissRequest = {onDismiss()},
      properties = DialogProperties(usePlatformDefaultWidth = false)
  ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Surface(
                shape = MaterialTheme.shapes.medium ,
                modifier = Modifier.padding(16.dp)
            ) {

                var currentTitle by rememberSaveable { mutableStateOf( if (isEditMode) existingTitle else "") }
                var currentDescription by rememberSaveable { mutableStateOf( if (isEditMode) existingDescription else "") }
                var currentPriority by rememberSaveable { mutableStateOf( exitingPriority ) }
                var isTitleEmpty by rememberSaveable { mutableStateOf(false) }

                var enable by rememberSaveable { if(isEditMode) mutableStateOf(false) else mutableStateOf(true) }

                var confirmDeletionToDo by rememberSaveable { mutableStateOf(false) }

                val focusRequester = remember{ FocusRequester()}

                val focusManager  = LocalFocusManager.current

                // when add
                LaunchedEffect(focusRequester) {
                    focusRequester.requestFocus()
                }
                // update / delete
                LaunchedEffect(key1 = enable) {
                    focusRequester.requestFocus()
                }




                Column(
                    modifier = Modifier

                        .verticalScroll(rememberScrollState())
                        .background(colorResource(R.color.Dark_Blue))
                        .border(2.dp, colorResource(R.color.Light_Blue), RoundedCornerShape(12.dp))
                        .padding(16.dp)

                ) {
                    Row( // row contains title and Icons
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ){
                        Text(
                            if(isEditMode) "Edit/Delete ToDo" else "Add ToDo",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontSize = 18.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ){
                            if(isEditMode){
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Color.Cyan.copy(alpha = 0.2f),
                                            shape = MaterialTheme.shapes.extraLarge
                                        )
                                ){
                                   IconButton(
                                       onClick = {
                                           enable = true ;
                                       }
                                   ) {
                                       Icon(
                                           imageVector = Icons.Default.Edit,
                                           contentDescription = "Edit ToDo",
                                           tint = Color.Cyan

                                       )
                                   }
                                }
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Color.Red.copy(alpha = 0.2f),
                                            shape = MaterialTheme.shapes.extraLarge
                                        )
                                ){
                                    IconButton(
                                        onClick = {
                                            confirmDeletionToDo = true;
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete ToDo",
                                            tint = Color.Red
                                        )
                                    }

                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        IconWithCircleBackground(
                            priority = Priority.LOW,
                            selected = currentPriority == Priority.LOW
                        ){
                            currentPriority = Priority.LOW
                        }
                        IconWithCircleBackground(
                            priority = Priority.MEDIUM,
                            selected = currentPriority == Priority.MEDIUM
                        ){
                            currentPriority = Priority.MEDIUM
                        }
                        IconWithCircleBackground(
                            priority = Priority.HIGH,
                            selected = currentPriority == Priority.HIGH
                        ){
                            currentPriority = Priority.HIGH
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomizedTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        text = currentTitle,
                        label = "ToDo Title",
                        onValuechange = {
                            currentTitle = it
                        },
                        enabled = enable ,
                        supportingText = if(isTitleEmpty){
                            "Please Enter the Title at least !"
                        }else "",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext =  {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomizedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentDescription,
                        label = "ToDo Description",
                        onValuechange = {
                            currentDescription = it
                        },
                        enabled = enable ,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red.copy(0.6f),
                                contentColor = Color.White
                            )
                        ){
                            Text("Cancel", fontWeight = FontWeight.Bold)
                        }
                        if(isEditMode){
                            Button(
                                onClick = {

                                    if(currentTitle.isNotEmpty()){
                                        onUpdateToDo(
                                            currentTitle,
                                            currentDescription,
                                            currentPriority
                                        )
                                    }
                                    else{
                                        isTitleEmpty = true ;
                                    }
                                },
                                enabled = currentTitle != existingTitle || currentDescription != existingDescription ||
                                        exitingPriority != currentPriority,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Green.copy(0.6f),
                                    contentColor = Color.White,
                                    disabledContentColor = Color.LightGray
                                )
                            ){
                                Text("Update ToDo", fontWeight = FontWeight.Bold)
                            }
                        }
                        else{
                            Button(
                                onClick = {
                                    if(currentTitle.isNotEmpty()){
                                        onAddToDo(currentTitle, currentDescription, currentPriority)
                                    }
                                    else {
                                        isTitleEmpty = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Green.copy(0.6f),
                                    contentColor = Color.White
                                )
                            ){
                                Text("Add ToDo" , fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if(confirmDeletionToDo){
                        SimpleAlertDialog(
                            title = "Deleteing ToDo?",
                            text = "Are You sure you want to delete this ToDo?" ,
                            onConfirm = {
                                onDeleteToDo()
                                confirmDeletionToDo = false
                            },
                            onDismiss = {
                                confirmDeletionToDo = false
                            }
                        ){}
                    }



                }
            }

        }


  }
}

