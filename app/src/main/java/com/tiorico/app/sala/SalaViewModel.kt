package com.tiorico.app.sala



import androidx.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.*

import com.tiorico.app.model.Player

import com.tiorico.app.model.Room

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow



class SalaViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference

    private val auth = FirebaseAuth.getInstance()



    private val _room = MutableStateFlow<Room?>(null)

    val room: StateFlow<Room?> = _room



    private val _started = MutableStateFlow(false)

    val started: StateFlow<Boolean> = _started



    private var listener: ValueEventListener? = null

    private var _code = ""

    fun getCode() = _code



    fun createRoom() {

        val uid = auth.currentUser?.uid ?: return

        val code = (1000..9999).random().toString()

        val player = Player(uid = uid, name = auth.currentUser?.email ?: "Player")

        val room = Room(roomCode = code, hostUid = uid, players = mapOf(uid to player))

        db.child("rooms/$code").setValue(room).addOnSuccessListener { listenRoom(code) }

    }



    fun joinRoom(code: String) {

        val uid = auth.currentUser?.uid ?: return

        db.child("users/$uid").get().addOnSuccessListener { snap ->

            val p = snap.getValue(Player::class.java) ?: Player(uid = uid)

            db.child("rooms/$code/players/$uid").setValue(p)

                .addOnSuccessListener { listenRoom(code) }

        }

    }



    fun startGame() { db.child("rooms/$_code/started").setValue(true) }



    private fun listenRoom(code: String) {

        _code = code

        listener = object : ValueEventListener {

            override fun onDataChange(snap: DataSnapshot) {

                val r = snap.getValue(Room::class.java) ?: return

                _room.value = r

                if (r.started) _started.value = true

            }

            override fun onCancelled(e: DatabaseError) {}

        }

        db.child("rooms/$code").addValueEventListener(listener!!)

    }



    override fun onCleared() {

        super.onCleared()

        listener?.let { db.child("rooms/$_code").removeEventListener(it) }

    }

}