package com.timeflying.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class AnimationType {
    DIRECT,
    PAGE_FLIP
}

class AnimationSettings(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("animation_settings", Context.MODE_PRIVATE)
    
    var currentAnimationType by mutableStateOf(
        AnimationType.valueOf(
            sharedPreferences.getString("animation_type", AnimationType.PAGE_FLIP.name) ?: AnimationType.PAGE_FLIP.name
        )
    )
        private set
    
    fun updateAnimationType(type: AnimationType) {
        currentAnimationType = type
        sharedPreferences.edit().putString("animation_type", type.name).apply()
    }
}