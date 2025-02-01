package com.timeflying.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*
import com.timeflying.utils.Constants
import java.net.URL

data class WeatherData(
    val weatherEmoji: String = "⏳"
)

class WeatherManager {
    private val weatherCodes = mapOf(
        0 to "☀️",
        1 to "🌤️", 2 to "🌤️", 3 to "🌤️",
        45 to "🌫️", 48 to "🌫️",
        51 to "🌧️", 53 to "🌧️", 55 to "🌧️",
        61 to "🌧️", 63 to "🌧️", 65 to "🌧️",
        71 to "🌨️", 73 to "🌨️", 75 to "🌨️",
        77 to "🌨️",
        80 to "🌧️", 81 to "🌧️", 82 to "🌧️",
        85 to "🌨️", 86 to "🌨️",
        95 to "⛈️",
        96 to "⛈️", 99 to "⛈️"
    )

    suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherData {
        return try {
            val url = URL("${Constants.WEATHER_API_URL}?latitude=${latitude}&longitude=${longitude}&current=weather_code")
            val response = withContext(Dispatchers.IO) {
                url.openConnection().getInputStream().bufferedReader().use { it.readText() }
            }
            val jsonElement = Json.parseToJsonElement(response)
            val weatherCode = jsonElement.jsonObject["current"]?.jsonObject?.get("weather_code")?.jsonPrimitive?.int
            WeatherData(weatherEmoji = weatherCodes[weatherCode] ?: "❓")
        } catch (e: Exception) {
            e.printStackTrace()
            WeatherData(weatherEmoji = "❌")
        }
    }
}