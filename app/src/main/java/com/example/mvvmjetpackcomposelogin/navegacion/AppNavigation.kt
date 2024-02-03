package com.example.mvvmjetpackcomposelogin.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginScreen
import com.example.mvvmjetpackcomposelogin.screens.login.ui.LoginViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationScreens.LoginScreen.route, builder = {
        composable(route = NavigationScreens.LoginScreen.route){
            LoginScreen(LoginViewModel())
        }
        composable(route = NavigationScreens.RegisterScreen.route){

        }
    })
}