package com.tiorico.app.auth



import androidx.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase

import com.tiorico.app.model.Player

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow



class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseDatabase.getInstance().reference



    private val _error = MutableStateFlow<String?>(null)

    val error: StateFlow<String?> = _error



    fun isLoggedIn() = auth.currentUser != null



    fun login(email: String, pass: String, onSuccess: () -> Unit) {

        if (email.isBlank() || pass.isBlank()) { _error.value = "Completa todos los campos"; return }

        auth.signInWithEmailAndPassword(email, pass)

            .addOnSuccessListener { _error.value = null; onSuccess() }

            .addOnFailureListener { _error.value = it.message }

    }



    fun register(name: String, email: String, pass: String, onSuccess: () -> Unit) {

        if (name.isBlank() || email.isBlank() || pass.isBlank()) { _error.value = "Completa todos los campos"; return }

        auth.createUserWithEmailAndPassword(email, pass)

            .addOnSuccessListener { res ->

                val uid = res.user!!.uid

                db.child("users/$uid").setValue(Player(uid = uid, name = name))

                    .addOnSuccessListener { _error.value = null; onSuccess() }

            }

            .addOnFailureListener { _error.value = it.message }

    }

}