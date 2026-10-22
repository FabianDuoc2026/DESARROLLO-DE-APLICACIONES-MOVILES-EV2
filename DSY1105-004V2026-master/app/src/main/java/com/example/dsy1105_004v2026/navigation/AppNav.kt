package com.example.dsy1105_004v2026.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dsy1105_004v2026.ui.home.DrawerMenu
import com.example.dsy1105_004v2026.ui.home.MuestraDatosScreen
import com.example.dsy1105_004v2026.ui.theme.HomeScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()

    NavHost(
        navController=navController, startDestination = "login"
    ){ // inicio NavHost
        composable("login"){
            HomeScreen(navController=navController)
        }//fin composable 1

        composable(
            //route="muestraDatos/{username}",
            route="DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){
                    type= NavType.StringType
                }
            )
        ) // fin composable
        { //defino la ruta
            backStackEntry ->
            val username=backStackEntry.arguments?.getString("username").orEmpty()
//            MuestraDatosScreen(username=username, navController=navController)
            DrawerMenu(username=username, navController=navController)

        }


    }//Fin Nav Host
}//fin AppNav