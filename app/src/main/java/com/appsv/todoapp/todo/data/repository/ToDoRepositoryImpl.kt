package com.appsv.todoapp.todo.data.repository

import android.util.Log
import com.appsv.todoapp.todo.data.ToDoDTO
import com.appsv.todoapp.todo.data.mapper.toToDoDTO
import com.appsv.todoapp.todo.domain.mapper.toToDoUI
import com.appsv.todoapp.todo.domain.model.ToDoUI
import com.appsv.todoapp.todo.domain.repository.ToDoRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ToDoRepositoryImpl : ToDoRepository {

    private val firebase = FirebaseDatabase.getInstance()
    private val toDoRef = firebase.getReference("ToDoItems")

    override suspend fun saveToDo(toDoUI: ToDoUI) {
        val toDoDTO = toDoUI.toToDoDTO()
        try {
            toDoRef.child(toDoDTO.id!!).setValue(toDoDTO).await()
        }
        catch (_: Exception){

        }
    }

    override suspend fun getToDo(): Flow<List<ToDoUI>> = callbackFlow{

        val listener  = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("Firebase", "Raw Snapshot: ${snapshot.value}")
                val toDoUIItems  : List<ToDoUI> =  snapshot.children.mapNotNull {
                    it.getValue(ToDoDTO::class.java)
                }.map {
                    it.toToDoUI()
                }
//                Log.d("Firebase", "Parsed Items: $toDoUIItems")
                trySend((toDoUIItems))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        toDoRef.addValueEventListener(listener)

        awaitClose{toDoRef.removeEventListener(listener)}

    }

    override suspend fun updateToDo(toDoUI: ToDoUI) {
        val toDoDTO = toDoUI.toToDoDTO()
        try {
            toDoRef.child(toDoDTO.id!!).setValue(toDoDTO).await()
        }
        catch(_: Exception){

        }
    }

    override suspend fun deleteToDo(id: String) {
        try{
            toDoRef.child(id).removeValue().await()
        }
        catch(_: Exception){

        }
    }


}