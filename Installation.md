# Android Setup Guide

This guide provides instructions for setting up the Todo App development environment on your system. Choose the path that works best for you.


## Path 1: Using Android Studio (For Developers)

### 1. Install Android Studio
1. Download Android Studio from [https://developer.android.com/studio](https://developer.android.com/studio)
2. Run the installer and follow the setup wizard
3. Let Android Studio download the necessary SDK components during first launch

[image: android-studio-install.png]
*Android Studio installation wizard*

[video: sdk-download.mp4]
*SDK components downloading during first launch*

### 2. Project Setup
1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd todo_projet
   ```

   [video: clone-repository.mp4]
   *How to clone the repository*

2. **Open Project in Android Studio**
   - Click "File > Open"
   - Navigate to the cloned repository
   - Select the "android" folder
   - Click "OK"
   - Wait for Gradle sync to complete


3. **Configure Gradle**
   - Open `android/build.gradle`
   - Ensure all dependencies are properly synced
   - Click "Sync Now" if prompted


### 3. Run the App
1. **Create Virtual Device**
   - Go to Tools > Device Manager
   - Click "Create Device"
   - Select any Phone (e.g., Pixel 2)
   - Download and select the latest system image
   - Click "Finish"


2. **Build and Run**
   - Click the "Run" button (green play button) or press Shift + F10
   - Select your virtual device
   - Wait for the app to build and launch

   ![Building and running the app](screenshots/build-and-run.png)

## Path 2: Direct Installation on Android Phone

The simplest way to run the app if you have an Android phone:

### 1. Download APK
Choose one of these methods:
- **Direct Download**: [todo-app.apk](https://github.com/filiksyos/androidtodo/releases/download/1.0.0/todo-app.apk)
- **QR Code**: Scan this code with your phone's camera or QR scanner app:

![Download QR](screenshots/download-qr.png)

*Scan to download the latest APK*

> 💡 **Tip**: Most modern Android phones can scan QR codes directly through the camera app. Just point your camera at the code.

> ⚠️ **Note**: Your browser or phone might warn you about downloading APKs from unknown sources. This is normal for apps not from Play Store.

### 2. Install on Phone
1. **Before Installing**
   - Go to Settings > Security
   - Enable "Install Unknown Apps" for your browser
   - This setting is required for non-Play Store apps

2. **Installation Steps**
   - Open the downloaded APK
   - Tap "Install" when prompted
   - If blocked, tap "Settings" and allow installation
   - Wait for installation to complete
   - Tap "Open" to launch

### Phone Requirements
- Android 11 (API 30) or higher
- 50MB free storage
- Internet connection for blockchain features

### Troubleshooting
1. **"App not installed" error**
   - Check if you have enough storage space
   - Try downloading the APK again
   - Make sure you enabled unknown sources

2. **App crashes on launch**
   - Check if your Android version is supported
   - Clear app data and cache
   - Try reinstalling the app

## Development Troubleshooting

### Common Issues
1. **Build Failures**
   - Click "File > Invalidate Caches / Restart"
   - Try "Build > Clean Project" then rebuild

   [image: build-errors.png]
   *Common build error messages and solutions*

2. **Performance Issues**
   - Increase RAM for Android Studio in settings
   - Close unnecessary applications
   - Enable hardware acceleration in BIOS

   [image: performance-settings.png]
   *Android Studio performance settings*

### System Requirements
- Windows 10/11, macOS 10.14+, or Linux
- 8GB RAM minimum (16GB recommended)
- 10GB free storage space
- Intel Core i5/AMD Ryzen 5 or better

