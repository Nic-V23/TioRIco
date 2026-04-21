package com.tiorico.app.chat



import androidx.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.*

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow



class ChatViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference

    private val auth = FirebaseAuth.getInstance()



    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())

    val messages: StateFlow<List<ChatMessage>> = _messages



    private var listener: ValueEventListener? = null

    private var roomCode = ""



    fun init(code: String) {

        roomCode = code

        listener = object : ValueEventListener {

            override fun onDataChange(snap: DataSnapshot) {

                val list = mutableListOf<ChatMessage>()

                for (child in snap.children) {

                    child.getValue(ChatMessage::class.java)?.let { list.add(it) }

                }

                _messages.value = list.sortedBy { it.timestamp }

            }

            override fun onCancelled(e: DatabaseError) {}

        }

        db.child("chat/$code").addValueEventListener(listener!!)

    }



    fun sendMessage(text: String) {

        if (text.isBlank()) return

        val uid = auth.currentUser?.uid ?: return

        db.child("users/$uid/name").get().addOnSuccessListener { snap ->

            val name = snap.getValue(String::class.java) ?: auth.currentUser?.email ?: "Anon"

            val msg = ChatMessage(uid = uid, senderName = name, message = text, timestamp = System.currentTimeMillis())

            db.child("chat/$roomCode").push().setValue(msg)

        }

    }



    override fun onCleared() {

        super.onCleared()

        listener?.let { db.child("chat/$roomCode").removeEventListener(it) }

    }

}