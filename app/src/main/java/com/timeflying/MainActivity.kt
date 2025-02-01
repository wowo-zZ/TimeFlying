package com.timeflying

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.timeflying.data.LocationManagerWrapper
import com.timeflying.data.AnimationSettings
import com.timeflying.data.BackgroundSettings
import com.timeflying.ui.components.TimeDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.timeflying.data.LocationState
import com.timeflying.ui.components.SettingsIcon
import com.timeflying.ui.components.SettingsScreen
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var locationManager: LocationManagerWrapper
    private lateinit var animationSettings: AnimationSettings
    private lateinit var backgroundSettings: BackgroundSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置全屏显示
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        // 初始化位置服务和设置
        locationManager = LocationManagerWrapper(this)
        locationManager.requestLocationUpdates()
        animationSettings = AnimationSettings(this)
        backgroundSettings = BackgroundSettings(this)
        
        setContent {
            MaterialTheme {
                var showSettings by remember { mutableStateOf(false) }
                
                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black
                    ) {
                        TimeDisplay(
                            locationData = locationManager.locationData,
                            animationSettings = animationSettings,
                            backgroundSettings = backgroundSettings
                        )
                    }
                    
                    // 设置按钮
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        SettingsIcon { showSettings = true }
                    }
                    
                    // 设置页面
                    if (showSettings) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            SettingsScreen(
                                animationSettings = animationSettings,
                                backgroundSettings = backgroundSettings,
                                onNavigateBack = { showSettings = false }
                            )
                        }
                    }
                }
            }
        }
    }
}