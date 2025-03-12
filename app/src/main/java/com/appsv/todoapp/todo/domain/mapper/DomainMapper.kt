package com.appsv.todoapp.todo.domain.mapper

import com.appsv.todoapp.core.util.formatToReadableDate
import com.appsv.todoapp.todo.data.ToDoDTO
import com.appsv.todoapp.todo.domain.model.ToDoUI

fun ToDoDTO.toToDoUI() : ToDoUI {
    return ToDoUI(
        id = id!!,
        title = title,
        description = description,
        priority = priority,
        dateAdded = formatToReadableDate(dateAdded!!)
    )
}