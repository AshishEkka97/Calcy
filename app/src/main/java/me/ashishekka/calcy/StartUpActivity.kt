package me.ashishekka.calcy

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class StartUpActivity : AppCompatActivity() {

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration: AppBarConfiguration by lazy { setUpAppBar() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("My TAG", "onCreate: ")
        setContentView(R.layout.activity_startup)
    }

    private fun setUpAppBar(): AppBarConfiguration =
        AppBarConfiguration.Builder(R.id.fragment_login_dest).build().also {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

    override fun onNavigateUp() = navController.navigateUp(appBarConfiguration)
}