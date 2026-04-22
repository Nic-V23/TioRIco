package com.tiorico.app.result




import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier



import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import com.tiorico.app.ui.theme.*



@Composable

fun ResultScreen(won: Boolean, finalMoney: Int, onRetry: () -> Unit, onExit: () -> Unit) {

    Column(

        modifier = Modifier.fillMaxSize().background(DarkBg).padding(32.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Text(

            text = if (won) "¡Ganaste!" else "¡Perdiste!",

            fontSize = 52.sp,

            fontWeight = FontWeight.Bold,

            color = if (won) Green else Red

        )

        Spacer(Modifier.height(16.dp))

        Text(

            text = if (won) "💰" else "💸",

            fontSize = 90.sp,


        )

        Spacer(Modifier.height(24.dp))

        Surface(color = CardBg, shape = RoundedCornerShape(16.dp)) {

            Text(

                text = "Dinero Final: \$$finalMoney",

                fontSize = 24.sp,

                color = if (finalMoney > 0) Green else Red,

                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)

            )

        }

        Spacer(Modifier.height(40.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            Button(

                onClick = onRetry,

                colors = ButtonDefaults.buttonColors(containerColor = Blue),

                shape = RoundedCornerShape(12.dp),

                modifier = Modifier.weight(1f).height(50.dp)

            ) { Text("Reintentar", fontWeight = FontWeight.Bold) }

            Button(

                onClick = onExit,

                colors = ButtonDefaults.buttonColors(containerColor = Red),

                shape = RoundedCornerShape(12.dp),

                modifier = Modifier.weight(1f).height(50.dp)

            ) { Text("Salir", fontWeight = FontWeight.Bold) }

        }

    }

}