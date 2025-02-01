package com.timeflying.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.timeflying.data.AnimationSettings
import com.timeflying.data.AnimationType
import com.timeflying.data.BackgroundSettings
import com.timeflying.data.BackgroundType

@Composable
fun SettingsIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(end = 24.dp)
            .size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "设置",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    animationSettings: AnimationSettings,
    backgroundSettings: BackgroundSettings,
    onNavigateBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                modifier = Modifier
                    .let { 
                        if (isLandscape) {
                            it.width(400.dp)
                        } else {
                            it.fillMaxWidth()
                        }
                    }
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // 背景设置
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "背景图片",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        var bgExpanded by remember { mutableStateOf(false) }
                        val currentBgType = backgroundSettings.currentBackgroundType
                        
                        Box(
                            modifier = Modifier.width(160.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = bgExpanded,
                                onExpandedChange = { bgExpanded = !bgExpanded }
                            ) {
                                OutlinedTextField(
                                    value = when (currentBgType) {
                                        BackgroundType.LICH_KING -> "冰封王座"
                                        BackgroundType.ARAD -> "决战阿拉德"
                                        BackgroundType.JOURNEY_WEST -> "梦回西游"
                                    },
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { 
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = bgExpanded)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )
                                
                                ExposedDropdownMenu(
                                    expanded = bgExpanded,
                                    onDismissRequest = { bgExpanded = false }
                                ) {
                                    BackgroundType.values().forEach { type ->
                                        DropdownMenuItem(
                                            text = { 
                                                Text(when (type) {
                                                    BackgroundType.LICH_KING -> "冰封王座"
                                                    BackgroundType.ARAD -> "决战阿拉德"
                                                    BackgroundType.JOURNEY_WEST -> "梦回西游"
                                                })
                                            },
                                            onClick = {
                                                backgroundSettings.updateBackgroundType(type)
                                                bgExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 动画设置
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "动画效果",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        var expanded by remember { mutableStateOf(false) }
                        val currentType = animationSettings.currentAnimationType
                        
                        Box(
                            modifier = Modifier.width(160.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                OutlinedTextField(
                                    value = when (currentType) {
                                        AnimationType.DIRECT -> "直接变化"
                                        AnimationType.PAGE_FLIP -> "翻页动画"
                                    },
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { 
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )
                                
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    AnimationType.values().forEach { type ->
                                        DropdownMenuItem(
                                            text = { 
                                                Text(when (type) {
                                                    AnimationType.DIRECT -> "直接变化"
                                                    AnimationType.PAGE_FLIP -> "翻页动画"
                                                })
                                            },
                                            onClick = {
                                                animationSettings.updateAnimationType(type)
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}