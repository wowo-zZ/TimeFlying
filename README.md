# TimeFlying

简体中文 | [English](README_en.md)

## 项目简介

TimeFlying 是一款精心设计的 Android 时钟应用，通过优雅的动画效果和现代化的界面设计，为用户提供独特的时间显示体验。

## 功能特点

- 🕒 动态时钟显示：实时更新的数字时钟界面
- 🎨 精美动画效果：流畅的界面过渡和交互动画
- 🌈 Material You 设计：遵循 Material Design 3 设计规范
- 📱 响应式布局：完美适配各种屏幕尺寸
- ⚡ 高性能：基于 Jetpack Compose 构建的原生应用

## 技术栈

- 开发语言：Kotlin 1.8.10
- UI 框架：Jetpack Compose 2023.10.00
- 架构组件：
  - ViewModel 2.6.2
  - Coroutines 协程 1.7.3
  - Kotlin Flow
- 序列化：Kotlinx Serialization 1.5.1
- 构建工具：Gradle 8.1.0 with Kotlin DSL

## 系统要求

- Android 8.0 (API 26) 或更高版本
- Android Studio Giraffe (2022.3.1) 或更新版本
- 目标 Android 版本：Android 14 (API 34)

## 安装步骤

1. 克隆仓库
   ```bash
   git clone https://github.com/yourusername/TimeFlying.git
   ```

2. 在 Android Studio 中打开项目

3. 同步 Gradle 文件
   - 点击工具栏中的「Sync Project with Gradle Files」按钮
   - 或者在终端中运行 `./gradlew build`

4. 在 Android 设备或模拟器上构建并运行
   - 确保您的设备已启用 USB 调试模式
   - 点击工具栏中的「Run」按钮或使用快捷键 `Shift + F10`

## 开发环境配置

- JDK 8 (JVM target 1.8)
- Android Studio Giraffe (2022.3.1) 或更新版本
- Android SDK Build-Tools 34.0.0
- Android SDK Platform 34
- Kotlin 1.8.10
- Gradle 8.1.0

## 贡献指南

我们欢迎所有形式的贡献，无论是新功能、bug 修复还是文档改进。请遵循以下步骤：

1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启一个 Pull Request

## 许可证

本项目采用 MIT 许可证