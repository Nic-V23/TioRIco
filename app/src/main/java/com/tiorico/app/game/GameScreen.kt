package com.tiorico.app.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tiorico.app.chat.ChatScreen
import com.tiorico.app.ui.theme.*
import com.tiorico.app.utils.Constants

@Composable
fun GameScreen(roomCode: String, onGameOver: (Boolean, Int) -> Unit, vm: GameViewModel = viewModel()) {
    val player by vm.player.collectAsState()
    val eventMsg by vm.eventMsg.collectAsState()
    val gameOver by vm.gameOver.collectAsState()
    val enabled by vm.actionEnabled.collectAsState()

    LaunchedEffect(Unit) { vm.init(roomCode) }
    LaunchedEffect(gameOver) { gameOver?.let { onGameOver(it.first, it.second) } }

    Column(modifier = Modifier.fillMaxSize().background(DarkBg)) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("TÍO RICO", fontSize = 34.sp, fontWeight = FontWeight.Bold, color = Gold,
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(12.dp))
            player?.let { p ->
                Surface(color = CardBg, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Dinero: \$${p.money}", fontSize = 26.sp, color = Green, fontWeight = FontWeight.Bold)
                        Text("Turno: ${p.currentRound} / Meta: ${Constants.TOTAL_ROUNDS} Rondas",
                            fontSize = 13.sp, color = androidx.compose.ui.graphics.Color(0xFF90CAF9))
                    }
                }
            }
            if (eventMsg.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(eventMsg, color = Orange, fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Spacer(Modifier.height(16.dp))
            ActionButton("Ahorrar", Blue, enabled) { vm.performAction("SAVE") }
            Spacer(Modifier.height(8.dp))
            ActionButton("Invertir", Orange, enabled) { vm.performAction("INVEST") }
            Spacer(Modifier.height(8.dp))
            ActionButton("Gastar", Red, enabled) { vm.performAction("SPEND") }
            Spacer(Modifier.height(12.dp))
        }
        HorizontalDivider(color = CardBg)
        ChatScreen(roomCode = roomCode, modifier = Modifier.weight(1f))
    }
}

@Composable
fun ActionButton(label: String, color: androidx.compose.ui.graphics.Color, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) { Text(label, fontSize = 18.sp, fontWeight = FontWeight.SemiBold) }
}
