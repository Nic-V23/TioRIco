package com.tiorico.app.auth



import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.tiorico.app.ui.theme.*



@Composable

fun RegisterScreen(onSuccess: () -> Unit, vm: AuthViewModel = viewModel()) {

    var name by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var pass by remember { mutableStateOf("") }

    val error by vm.error.collectAsState()



    Column(

        modifier = Modifier.fillMaxSize().background(DarkBg).padding(32.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Text("Crear Cuenta", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Gold)

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(

            value = name, onValueChange = { name = it },

            label = { Text("Nombre de usuario") },

            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Gold, unfocusedBorderColor = CardBg,

                focusedTextColor = androidx.compose.ui.graphics.Color.White,

                unfocusedTextColor = androidx.compose.ui.graphics.Color.White

            )

        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(

            value = email, onValueChange = { email = it },

            label = { Text("Email") },

            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Gold, unfocusedBorderColor = CardBg,

                focusedTextColor = androidx.compose.ui.graphics.Color.White,

                unfocusedTextColor = androidx.compose.ui.graphics.Color.White

            )

        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(

            value = pass, onValueChange = { pass = it },

            label = { Text("Contraseña") },

            visualTransformation = PasswordVisualTransformation(),

            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Gold, unfocusedBorderColor = CardBg,

                focusedTextColor = androidx.compose.ui.graphics.Color.White,

                unfocusedTextColor = androidx.compose.ui.graphics.Color.White

            )

        )

        error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = Red, fontSize = 13.sp) }

        Spacer(Modifier.height(24.dp))

        Button(

            onClick = { vm.register(name, email, pass, onSuccess) },

            modifier = Modifier.fillMaxWidth().height(50.dp),

            colors = ButtonDefaults.buttonColors(containerColor = Blue),

            shape = RoundedCornerShape(12.dp)

        ) { Text("Crear Cuenta", fontSize = 16.sp) }

    }

}