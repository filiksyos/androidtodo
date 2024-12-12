package com.wolfbytetechnologies.ielts.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.wolfbytetechnologies.ielts.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Install splash screen
        val splashScreen = installSplashScreen()

        // Offload initialization tasks
        var isInitializationComplete = false
        splashScreen.setKeepOnScreenCondition { !isInitializationComplete }

        lifecycleScope.launch {
            initializeApp() // Perform background initialization
            isInitializationComplete = true
        }

        // Transition to MainActivity
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private suspend fun initializeApp() {
        // Simulate heavy initialization tasks (e.g., loading resources, initializing Koin)
        delay(1000) // Simulating a delay
        // Add your actual initialization tasks here
    }
}


