package com.appsv.todoapp.todo.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.todoapp.todo.data.repository.ToDoRepositoryImpl
import com.appsv.todoapp.todo.domain.model.ToDoUI
import com.appsv.todoapp.todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ToDoViewModel : ViewModel() {
    private val toDoRepository : ToDoRepository = ToDoRepositoryImpl()
    private val _state = MutableStateFlow(ToDoState())
    val state = _state.asStateFlow()

    init {
        getToDo()
    }

    fun onEvent(events : ToDoEvents ){
        when(events){
            is ToDoEvents.SaveToDo -> {
                val newUuid = UUID.randomUUID().toString()
                val toDoWithId = events.toDoUI.copy(id = newUuid)
                saveToDo(toDoWithId)
            }

            is ToDoEvents.DeleteToDo -> {
                deleteToDo(events.id)
            }
            is ToDoEvents.UpdateToDo -> {
                updateToDo(events.toDoUI)
            }
        }
    }


    private fun saveToDo(toDoUI : ToDoUI){
        viewModelScope.launch {
            toDoRepository.saveToDo(toDoUI)

        }
    }
    private fun getToDo(){
        viewModelScope.launch {

            toDoRepository.getToDo().collect{toDoList->
//                Log.d("ToDoViewModel", "Received updates: $toDoList")
                _state.value = _state.value.copy(
                    toDoList = toDoList,
                    isLoading = false
                )
            }

        }
    }

    private fun updateToDo(toDoUI: ToDoUI){
        viewModelScope.launch {
            toDoRepository.updateToDo(toDoUI)
        }
    }

    private fun deleteToDo(id : String){
        viewModelScope.launch {
            toDoRepository.deleteToDo(id)
        }
    }
}