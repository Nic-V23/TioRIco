package com.tiorico.app



import android.os.Bundle

import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController

import com.google.firebase.auth.FirebaseAuth

import com.tiorico.app.auth.LoginScreen

import com.tiorico.app.auth.RegisterScreen

import com.tiorico.app.sala.SalaScreen

import com.tiorico.app.game.GameScreen

import com.tiorico.app.result.ResultScreen

import com.tiorico.app.ui.theme.TioRicoTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            TioRicoTheme {

                val nav = rememberNavController()

                val start = if (FirebaseAuth.getInstance().currentUser != null) "sala" else "login"

                NavHost(navController = nav, startDestination = start) {

                    composable("login") {

                        LoginScreen(

                            onLoginSuccess = { nav.navigate("sala") { popUpTo("login") { inclusive = true } } },

                            onGoRegister = { nav.navigate("register") }

                        )

                    }

                    composable("register") {

                        RegisterScreen(onSuccess = { nav.navigate("sala") { popUpTo("login") { inclusive = true } } })

                    }

                    composable("sala") {

                        SalaScreen(onGameStart = { code -> nav.navigate("game/$code") })

                    }

                    composable("game/{roomCode}") { back ->

                        val roomCode = back.arguments?.getString("roomCode") ?: ""

                        GameScreen(

                            roomCode = roomCode,

                            onGameOver = { won, money -> nav.navigate("result/$won/$money") { popUpTo("sala") } }

                        )

                    }

                    composable("result/{won}/{money}") { back ->

                        val won = back.arguments?.getString("won").toBoolean()

                        val money = back.arguments?.getString("money")?.toIntOrNull() ?: 0

                        ResultScreen(

                            won = won, finalMoney = money,

                            onRetry = { nav.navigate("sala") { popUpTo(0) { inclusive = true } } },

                            onExit = { finishAffinity() }

                        )

                    }

                }

            }

        }

    }

}