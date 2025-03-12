package com.appsv.todoapp.todo.domain.model

import com.appsv.todoapp.core.util.Priority

data class ToDoUI(
    val id: String ? = null,
    val title: String,
    val description: String = "",
    val priority: Priority ,
    val dateAdded: String ? = null
)
