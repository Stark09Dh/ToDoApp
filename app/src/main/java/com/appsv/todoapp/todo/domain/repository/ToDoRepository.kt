package com.appsv.todoapp.todo.domain.repository

import com.appsv.todoapp.todo.domain.model.ToDoUI
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun saveToDo(toDoUI : ToDoUI)
    suspend fun  getToDo() : Flow<List<ToDoUI>>         // suspended function runs in background
    suspend fun updateToDo(toDoUI: ToDoUI)
    suspend fun deleteToDo(id : String)

}