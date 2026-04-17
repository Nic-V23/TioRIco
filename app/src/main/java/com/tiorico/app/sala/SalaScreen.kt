package com.tiorico.app.sala



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

import com.tiorico.app.ui.theme.*



@Composable

fun SalaScreen(onGameStart: (String) -> Unit, vm: SalaViewModel = viewModel()) {

    var codeInput by remember { mutableStateOf("") }

    val room by vm.room.collectAsState()

    val started by vm.started.collectAsState()



    LaunchedEffect(started) { if (started) onGameStart(vm.getCode()) }



    Column(

        modifier = Modifier.fillMaxSize().background(DarkBg).padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(Modifier.height(32.dp))

        Text("Sala de Juego", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Gold)

        Spacer(Modifier.height(16.dp))

        room?.let {

            Surface(color = CardBg, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {

                Text("Código: ${it.roomCode}", modifier = Modifier.padding(16.dp),

                    fontSize = 20.sp, color = Gold, fontWeight = FontWeight.Bold)

            }

            Spacer(Modifier.height(12.dp))

            Text("Jugadores en la Sala:", color = Gold, fontSize = 14.sp)

            it.players.values.forEach { p ->

                Text("• ${p.name}", color = androidx.compose.ui.graphics.Color.White, fontSize = 15.sp)

            }

        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(

            value = codeInput, onValueChange = { codeInput = it },

            label = { Text("Código de sala") },

            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Gold, unfocusedBorderColor = CardBg,

                focusedTextColor = androidx.compose.ui.graphics.Color.White,

                unfocusedTextColor = androidx.compose.ui.graphics.Color.White

            )

        )

        Spacer(Modifier.height(12.dp))

        Button(onClick = { vm.joinRoom(codeInput) }, modifier = Modifier.fillMaxWidth().height(48.dp),

            colors = ButtonDefaults.buttonColors(containerColor = Blue), shape = RoundedCornerShape(12.dp)

        ) { Text("Unirse a Sala") }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(onClick = { vm.createRoom() }, modifier = Modifier.fillMaxWidth().height(48.dp),

            shape = RoundedCornerShape(12.dp)

        ) { Text("Crear Sala", color = Gold) }

        Spacer(Modifier.weight(1f))

        Button(onClick = { vm.startGame() }, modifier = Modifier.fillMaxWidth().height(52.dp),

            colors = ButtonDefaults.buttonColors(containerColor = Green), shape = RoundedCornerShape(12.dp)

        ) { Text("Iniciar Partida", fontSize = 17.sp) }

    }

}