package me.ashishekka.calcy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration: AppBarConfiguration by lazy { setUpAppBar() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> {
                navController.navigate(R.id.action_fragment_calculator_dest_to_fragment_history_dest)
            }

            R.id.action_logout -> {
                Toast.makeText(baseContext, "Logging Out", Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this, StartUpActivity::class.java))
            }
        }

        return true
    }

    private fun setUpAppBar(): AppBarConfiguration =
        AppBarConfiguration.Builder(R.id.fragment_calculator_dest).build().also {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

    override fun onNavigateUp() = navController.navigateUp(appBarConfiguration)
}