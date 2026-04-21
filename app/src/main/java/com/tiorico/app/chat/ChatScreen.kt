package com.tiorico.app.chat



import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.google.firebase.auth.FirebaseAuth

import com.tiorico.app.ui.theme.*



@Composable

fun ChatScreen(roomCode: String, modifier: Modifier = Modifier, vm: ChatViewModel = viewModel()) {

    val messages by vm.messages.collectAsState()

    var text by remember { mutableStateOf("") }

    val myUid = FirebaseAuth.getInstance().currentUser?.uid

    val listState = rememberLazyListState()



    LaunchedEffect(Unit) { vm.init(roomCode) }

    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)

    }



    Column(modifier = modifier.background(Color(0xFF0A1520))) {

        Text("Chat", color = Gold, fontSize = 13.sp, fontWeight = FontWeight.Bold,

            modifier = Modifier.background(Color(0xFF121E2D)).fillMaxWidth().padding(8.dp))

        LazyColumn(state = listState, modifier = Modifier.weight(1f).padding(8.dp),

            verticalArrangement = Arrangement.spacedBy(6.dp)) {

            items(messages) { msg ->

                val isMe = msg.uid == myUid

                Row(modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start) {

                    Surface(

                        color = if (isMe) Blue else CardBg,

                        shape = RoundedCornerShape(

                            topStart = 12.dp, topEnd = 12.dp,

                            bottomStart = if (isMe) 12.dp else 2.dp,

                            bottomEnd = if (isMe) 2.dp else 12.dp

                        ),

                        modifier = Modifier.widthIn(max = 240.dp)

                    ) {

                        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {

                            if (!isMe) Text(msg.senderName, color = Color(0xFF90CAF9),

                                fontSize = 11.sp, fontWeight = FontWeight.Bold)

                            Text(msg.message, color = Color.White, fontSize = 14.sp)

                        }

                    }

                }

            }

        }

        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

            OutlinedTextField(

                value = text, onValueChange = { text = it },

                placeholder = { Text("Mensaje...", color = Color.Gray) },

                modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp),

                colors = OutlinedTextFieldDefaults.colors(

                    focusedBorderColor = Blue, unfocusedBorderColor = CardBg,

                    focusedTextColor = Color.White, unfocusedTextColor = Color.White

                )

            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = { vm.sendMessage(text); text = "" },

                colors = ButtonDefaults.buttonColors(containerColor = Blue),

                shape = RoundedCornerShape(12.dp)

            ) { Text("Enviar") }

        }

    }

}