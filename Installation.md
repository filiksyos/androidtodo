# Android Setup Guide

This guide provides instructions for setting up the Todo App development environment on your system. Choose the path that works best for you.

[video: setup-options.mp4]
*Overview of different setup options*

## Path 1: Using Android Studio (Recommended for Developers)

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

   [image: open-project.png]
   *Opening the project in Android Studio*

   [video: gradle-sync.mp4]
   *Gradle sync process*

3. **Configure Gradle**
   - Open `android/build.gradle`
   - Ensure all dependencies are properly synced
   - Click "Sync Now" if prompted

   [image: gradle-config.png]
   *Gradle configuration screen*

### 3. Run the App
1. **Create Virtual Device**
   - Go to Tools > Device Manager
   - Click "Create Device"
   - Select any Phone (e.g., Pixel 2)
   - Download and select the latest system image
   - Click "Finish"

   [video: create-avd.mp4]
   *Creating an Android Virtual Device*

2. **Build and Run**
   - Click the "Run" button (green play button) or press Shift + F10
   - Select your virtual device
   - Wait for the app to build and launch

   [video: build-and-run.mp4]
   *Building and running the app*

## Path 2: Using Bluestacks (For Users)

If you just want to run the app without development setup, you can use Bluestacks:

### 1. Install Bluestacks
1. Download Bluestacks from [https://www.bluestacks.com](https://www.bluestacks.com)
2. Run the installer and follow the setup wizard
3. Wait for initial setup to complete

[video: bluestacks-install.mp4]
*Installing Bluestacks emulator*

### 2. Run the App
1. **Download the APK**
   - Download our pre-built APK from the releases section
   - Or use this direct link: [todo-app.apk]

2. **Install on Bluestacks**
   - Double-click the APK file
   - Bluestacks will open automatically
   - Click "Install" when prompted
   - Wait for installation to complete

   [video: bluestacks-install-apk.mp4]
   *Installing the app on Bluestacks*

3. **Launch the App**
   - Find "Todo App" in Bluestacks
   - Click to launch
   - You're ready to go!

   [image: app-in-bluestacks.png]
   *Todo App running in Bluestacks*

### Bluestacks System Requirements
- Windows 7 or higher
- 4GB RAM minimum
- 5GB free storage space
- Intel/AMD processor

[image: bluestacks-requirements.png]
*Bluestacks system requirements*

### Troubleshooting Bluestacks
1. **Performance Issues**
   - Enable virtualization in BIOS
   - Allocate more RAM in Bluestacks settings
   - Update graphics drivers

2. **Installation Problems**
   - Run installer as administrator
   - Disable antivirus temporarily
   - Clear temporary files

## Path 3: Direct Installation on Android Phone

The simplest way to run the app if you have an Android phone:

### 1. Download APK
- Download our pre-built APK: [todo-app.apk](releases/todo-app.apk)
- Or scan this QR code:

![APK QR Code](screenshots/apk-qr.png)
*Scan to download the APK*

### 2. Install on Phone
1. **Enable Unknown Sources**
   - Go to Settings > Security
   - Enable "Install from Unknown Sources" or "Install Unknown Apps"
   - If prompted, allow your browser/file manager to install apps

2. **Install APK**
   - Find the downloaded APK in your Downloads folder
   - Tap the APK file
   - Tap "Install"
   - Wait for installation to complete
   - Tap "Open" when done

![Phone Installation](screenshots/phone-install.gif)
*Installing on Android phone*

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

## Troubleshooting

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

[image: system-requirements.png]
*Detailed system requirements and recommendations*
