package com.elflin.movieapps.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elflin.movieapps.data.DataStoreManager

import com.elflin.movieapps.ui.view.GetStartedView
import com.elflin.movieapps.ui.view.LoginView

import com.elflin.movieapps.ui.view.GetStartedView
import com.elflin.movieapps.ui.view.LoginView
import com.elflin.movieapps.ui.view.HomeView
import com.elflin.movieapps.ui.view.InsightView

import com.elflin.movieapps.viewmodel.AuthViewModel
import com.elflin.movieapps.ui.view.LockView
import com.elflin.movieapps.ui.view.ProfileView

@Composable
fun MovieAppsRoute(authViewModel: AuthViewModel, dataStore: DataStoreManager, lifecycleOwner: LifecycleOwner,) {
  val navController = rememberNavController()
  val isLoggedIn by authViewModel.isLoggedIn.observeAsState()

  // Determine start destination
  val startDestination = if (isLoggedIn == true) "home" else "getStarted"

  NavHost(navController = navController, startDestination = startDestination) {
    composable("getStarted") {
      GetStartedView(onGetStartedClicked = { navController.navigate("login") })
    }
    composable("login") {
      LoginView(authViewModel,
        lifecycleOwner,
        navController,
        dataStore)
    }
    composable("home") {
      HomeView(navController)
    }

    composable("insight") {
      InsightView(navController)  // Replace with your InsightView Composable
    }
    composable("lockview") {
      LockView(navController)  // Replace with your LockView Composable
    }
    composable("profileview") {
      ProfileView(navController)  // Replace with your ProfileView Composable
    }

  }

  // Respond to changes in isLoggedIn
  LaunchedEffect(isLoggedIn) {
    isLoggedIn?.let {
      if (it) {
        navController.navigate("home") {
          popUpTo("login") { inclusive = true }
        }
      } else {
        navController.navigate("getStarted")
      }
    }
  }
}



//@Composable
//fun MovieAppsRoute(authViewModel: AuthViewModel) {
//  val navController = rememberNavController()
//  val isLoggedIn by authViewModel.isLoggedIn.observeAsState()
//
//  val startDestination = if (isLoggedIn == true) {
//    "home"
//  } else {
//    "getStarted"
//  }
//
//  NavHost(navController = navController, startDestination = startDestination) {
//    composable("getStarted") {
//      GetStartedView(onGetStartedClicked = { navController.navigate("login") })
//    }
//    composable("login") {
//      // Assuming LoginView requires these parameters
//      LoginView(authViewModel, navController)
//    }
//    composable("home") {
//      // Directly using the imported HomeView
//      HomeView(navController)
//    }
//  }
//}



@Composable
fun AppContent() {
  val authViewModel: AuthViewModel = viewModel()

  // Obtain the lifecycleOwner
  val lifecycleOwner = LocalLifecycleOwner.current

  // Create or obtain an instance of DataStoreManager
  val context = LocalContext.current
  val dataStore = DataStoreManager(context)
  MovieAppsRoute(authViewModel, dataStore, lifecycleOwner)
}

