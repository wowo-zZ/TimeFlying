package com.timeflying.utils

object Constants {
    const val LOCATION_PERMISSION_CODE = 2
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val LOCATION_UPDATE_DISTANCE = 5f
    const val SCREEN_REFRESH_INTERVAL = 300000L // 5分钟
    const val ANIMATION_DURATION = 150
    
    // 默认位置（合肥）
    const val DEFAULT_LATITUDE = 31.8630
    const val DEFAULT_LONGITUDE = 117.2830
    
    // 天气API
    const val WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast"
}