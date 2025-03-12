package com.appsv.todoapp.todo.presentation

import com.appsv.todoapp.todo.domain.model.ToDoUI

data class ToDoState (
    val isLoading : Boolean = true ,
    val toDoList : List<ToDoUI> = emptyList()
)