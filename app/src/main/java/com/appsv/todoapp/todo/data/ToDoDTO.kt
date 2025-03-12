package com.appsv.todoapp.todo.data

import com.appsv.todoapp.core.util.Priority

data class ToDoDTO(
    val id : String ?  =null,
    val title : String = "",
    val description : String = "",
    val priority : Priority = Priority.LOW,
    val dateAdded : Long ? = null
)
