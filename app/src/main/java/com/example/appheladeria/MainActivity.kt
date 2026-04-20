package com.example.appheladeria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.appheladeria.data.datastore.DataStoreManager
import com.example.appheladeria.data.repository.AppRepository
import com.example.appheladeria.navigation.AppNavigation
import com.example.appheladeria.ui.theme.AppHeladeriaTheme
import com.example.appheladeria.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStoreManager = DataStoreManager(applicationContext)
        val repository = AppRepository(dataStoreManager)
        val appViewModel = AppViewModel(repository)

        setContent {
            AppHeladeriaTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    viewModel = appViewModel
                )
            }
        }
    }
}