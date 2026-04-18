package com.tiorico.app.game

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tiorico.app.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player

    private val _eventMsg = MutableStateFlow("")
    val eventMsg: StateFlow<String> = _eventMsg

    private val _gameOver = MutableStateFlow<Pair<Boolean, Int>?>(null)
    val gameOver: StateFlow<Pair<Boolean, Int>?> = _gameOver

    private val _actionEnabled = MutableStateFlow(true)
    val actionEnabled: StateFlow<Boolean> = _actionEnabled

    private var roomCode = ""
    private var listener: ValueEventListener? = null

    fun init(code: String) {
        roomCode = code
        val uid = auth.currentUser?.uid ?: return
        listener = object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                val p = snap.getValue(Player::class.java) ?: return
                _player.value = p
            }
            override fun onCancelled(e: DatabaseError) {}
        }
        db.child("rooms/$code/players/$uid").addValueEventListener(listener!!)
    }

    fun performAction(action: String) {
        val current = _player.value ?: return
        val uid = auth.currentUser?.uid ?: return
        _actionEnabled.value = false

        var newMoney = GameLogic.applyAction(current.money, action)
        val (moneyAfterEvent, eventMessage) = GameLogic.applyRandomEvent(newMoney)
        newMoney = moneyAfterEvent
        _eventMsg.value = eventMessage

        val newRound = current.currentRound + 1
        val alive = GameLogic.isAlive(newMoney)
        val won = GameLogic.hasWon(newRound, newMoney)
        val updated = current.copy(money = newMoney, currentRound = newRound, isAlive = alive)

        db.child("rooms/$roomCode/players/$uid").setValue(updated)
            .addOnCompleteListener {
                _actionEnabled.value = true
                if (won || !alive) _gameOver.value = Pair(won, newMoney)
            }
    }

    override fun onCleared() {
        super.onCleared()
        val uid = auth.currentUser?.uid ?: return
        listener?.let { db.child("rooms/$roomCode/players/$uid").removeEventListener(it) }
    }
}
