package com.appsv.todoapp.todo.presentation

import com.appsv.todoapp.todo.domain.model.ToDoUI

sealed class ToDoEvents {
    data class SaveToDo(val toDoUI: ToDoUI) : ToDoEvents()
    data class DeleteToDo( val id : String) : ToDoEvents()
    data class UpdateToDo(val toDoUI: ToDoUI) : ToDoEvents()

}