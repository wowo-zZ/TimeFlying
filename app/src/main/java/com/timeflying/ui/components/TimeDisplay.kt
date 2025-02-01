package com.timeflying.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import com.timeflying.data.AnimationSettings
import com.timeflying.data.AnimationType
import com.timeflying.data.BackgroundSettings
import com.timeflying.data.LocationData
import com.timeflying.data.WeatherData
import com.timeflying.data.WeatherManager
import com.timeflying.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DateWeatherDisplay(
    currentDate: LocalDate,
    weatherEmoji: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${currentDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))} $weatherEmoji",
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(bottom = 32.dp)
    )
}

@Composable
fun TimeDisplay(
    locationData: LocationData,
    animationSettings: AnimationSettings,
    backgroundSettings: BackgroundSettings,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    var previousTime by remember { mutableStateOf(LocalTime.now()) }
    var needsRefresh by remember { mutableStateOf(false) }
    var weatherData by remember { mutableStateOf(WeatherData()) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp
    val scope = rememberCoroutineScope()
    val weatherManager = remember { WeatherManager() }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            previousTime = currentTime
            currentTime = LocalTime.now()
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(Constants.SCREEN_REFRESH_INTERVAL)
            needsRefresh = true
            delay(100)
            needsRefresh = false
        }
    }

    LaunchedEffect(locationData) {
        scope.launch(Dispatchers.IO) {
            weatherData = weatherManager.fetchWeather(
                latitude = locationData.latitude,
                longitude = locationData.longitude
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 背景图片
        Image(
            painter = painterResource(id = backgroundSettings.getCurrentBackgroundResourceId()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            contentScale = ContentScale.FillBounds
        )

        // 半透明黑色遮罩
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(Color.Black.copy(alpha = 0.75f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DateWeatherDisplay(
                currentDate = LocalDate.now(),
                weatherEmoji = weatherData.weatherEmoji
            )

            if (needsRefresh) {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {}
            }

            when (animationSettings.currentAnimationType) {
                AnimationType.DIRECT -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val timeStr = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                        val digitWidth = if (isLandscape) 70.dp else 50.dp
                        val fontSize = if (isLandscape) 120.sp else 80.sp

                        val hours = timeStr.substring(0, 2)
                        val minutes = timeStr.substring(3, 5)
                        val seconds = timeStr.substring(6, 8)

                        // 显示小时
                        for (digit in hours) {
                            Box(modifier = Modifier.width(digitWidth)) {
                                Text(
                                    text = digit.toString(),
                                    fontSize = fontSize,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        TimeColon(fontSize = fontSize)

                        // 显示分钟
                        for (digit in minutes) {
                            Box(modifier = Modifier.width(digitWidth)) {
                                Text(
                                    text = digit.toString(),
                                    fontSize = fontSize,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        TimeColon(fontSize = fontSize)

                        // 显示秒钟
                        for (digit in seconds) {
                            Box(modifier = Modifier.width(digitWidth)) {
                                Text(
                                    text = digit.toString(),
                                    fontSize = fontSize,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                AnimationType.PAGE_FLIP -> {
                    TimeDigitsDisplay(
                        currentTime = currentTime,
                        previousTime = previousTime,
                        isLandscape = isLandscape
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeDigitsDisplay(
    currentTime: LocalTime,
    previousTime: LocalTime,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    val fontSize = if (isLandscape) 120.sp else 80.sp
    val digitWidth = if (isLandscape) 70.dp else 50.dp

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeUnitDisplay(
            currentTime = currentTime,
            previousTime = previousTime,
            pattern = "HH",
            fontSize = fontSize,
            digitWidth = digitWidth
        )

        TimeColon(fontSize = fontSize)

        TimeUnitDisplay(
            currentTime = currentTime,
            previousTime = previousTime,
            pattern = "mm",
            fontSize = fontSize,
            digitWidth = digitWidth
        )

        TimeColon(fontSize = fontSize)

        TimeUnitDisplay(
            currentTime = currentTime,
            previousTime = previousTime,
            pattern = "ss",
            fontSize = fontSize,
            digitWidth = digitWidth
        )
    }
}

@Composable
private fun TimeUnitDisplay(
    currentTime: LocalTime,
    previousTime: LocalTime,
    pattern: String,
    fontSize: androidx.compose.ui.unit.TextUnit,
    digitWidth: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val current = currentTime.format(DateTimeFormatter.ofPattern(pattern))
    val previous = previousTime.format(DateTimeFormatter.ofPattern(pattern))

    for (i in current.indices) {
        Box(modifier = Modifier.width(digitWidth)) {
            AnimatedContent(
                targetState = current[i],
                transitionSpec = {
                    if (current[i] != previous[i]) {
                        (slideInVertically { height -> height } + fadeIn(
                            animationSpec = tween(
                                Constants.ANIMATION_DURATION
                            )
                        )) with
                                (slideOutVertically { height -> -height } + fadeOut(
                                    animationSpec = tween(Constants.ANIMATION_DURATION)
                                ))
                    } else {
                        EnterTransition.None with ExitTransition.None
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            ) { digit ->
                Text(
                    text = digit.toString(),
                    fontSize = fontSize,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun TimeColon(
    fontSize: androidx.compose.ui.unit.TextUnit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size((fontSize.value * 0.2).dp)
                .padding(2.dp)
                .offset(y = -(fontSize.value * 0.15).dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.6f)
            ) {}
        }
        Box(
            modifier = Modifier
                .size((fontSize.value * 0.2).dp)
                .padding(2.dp)
                .offset(y = (fontSize.value * 0.15).dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.6f)
            ) {}
        }
    }
}