package com.appsv.todoapp.todo.data.mapper

import com.appsv.todoapp.todo.data.ToDoDTO
import com.appsv.todoapp.todo.domain.model.ToDoUI
import java.util.UUID

fun ToDoUI.toToDoDTO() : ToDoDTO{
    return ToDoDTO(
        id = id ?: UUID.randomUUID().toString(),
        title = title ,
        description = description ,
        priority = priority ,
        dateAdded = System.currentTimeMillis()
    )
}