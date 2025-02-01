package com.timeflying.data

import android.content.Context
import android.content.SharedPreferences
import com.timeflying.R

enum class BackgroundType {
    LICH_KING,
    ARAD,
    JOURNEY_WEST,
    NONE
}

class BackgroundSettings(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "background_settings",
        Context.MODE_PRIVATE
    )

    var currentBackgroundType: BackgroundType
        get() = BackgroundType.valueOf(
            prefs.getString("current_background", BackgroundType.LICH_KING.name) ?: BackgroundType.LICH_KING.name
        )
        private set(value) {
            prefs.edit().putString("current_background", value.name).apply()
        }

    var isAutoChangeEnabled: Boolean
        get() = prefs.getBoolean("auto_change_enabled", false)
        private set(value) {
            prefs.edit().putBoolean("auto_change_enabled", value).apply()
        }

    fun updateBackgroundType(type: BackgroundType) {
        currentBackgroundType = type
    }

    fun toggleAutoChange(enabled: Boolean) {
        isAutoChangeEnabled = enabled
    }

    fun getRandomBackground(): BackgroundType {
        return BackgroundType.values().random()
    }

    fun getBackgroundResourceId(type: BackgroundType): Int? {
        return when (type) {
            BackgroundType.LICH_KING -> R.drawable.lichking
            BackgroundType.ARAD -> R.drawable.ald
            BackgroundType.JOURNEY_WEST -> R.drawable.mhxy
            BackgroundType.NONE -> null
        }
    }

    fun getCurrentBackgroundResourceId(): Int {
        return getBackgroundResourceId(currentBackgroundType) ?: R.drawable.lichking
    }
}