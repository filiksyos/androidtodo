# Android Setup Guide

This guide provides instructions for setting up the Todo App development environment on your system. Choose the appropriate path based on whether you already have Android Studio installed.

![Overview of setup paths](screenshots/setup-paths-overview.gif)
*Overview of the two setup paths*

## Path 1: For Users with Android Studio Installed

If you already have Android Studio installed, follow these steps:

### 1. Project Setup
1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd todo_projet
   ```
   ![Cloning repository](screenshots/clone-repo.gif)
   *Cloning the repository using Git*

2. **Open Project in Android Studio**
   - Click "File > Open"
   - Navigate to the cloned repository
   - Select the "android" folder
   - Click "OK"
   - Wait for Gradle sync to complete

   ![Opening project](screenshots/open-project.gif)
   *Opening the project in Android Studio*

3. **Configure Gradle**
   - Open `android/build.gradle`
   - Ensure all dependencies are properly synced
   - Click "Sync Now" if prompted

   ![Gradle sync](screenshots/gradle-sync.gif)
   *Syncing Gradle configuration*

### 2. Run the App
1. **Start AVD**
   - Go to Tools > Device Manager
   - Click the play button (▶️) next to your existing virtual device
   - Wait for the emulator to boot up

   ![Starting AVD](screenshots/start-avd.gif)
   *Starting the Android Virtual Device*

2. **Build and Run**
   - Click the "Run" button (green play button) or press Shift + F10

   ![Building and running](screenshots/build-run.gif)
   *Building and running the app*

## Path 2: For Users Without Android Studio

If you don't have Android Studio installed, you can run the app directly using our pre-built APK:

### 1. Install Command Line Tools
1. **Download Android Command Line Tools**
   - Visit [Android Studio Downloads](https://developer.android.com/studio#command-tools)
   - Download "Command line tools only"
   - Extract the downloaded zip file

   ![Command line tools download](screenshots/cmdline-tools-download.png)
   *Downloading Android Command Line Tools*

2. **Set Up Environment Variables**
   ```bash
   # Add these to your system environment variables
   ANDROID_HOME=<path-to-android-sdk>
   PATH=%ANDROID_HOME%\cmdline-tools\latest\bin;%PATH%
   ```

   ![Environment variables setup](screenshots/env-vars-setup.gif)
   *Setting up environment variables*

### 2. Install Required Components
```bash
# Install platform-tools for adb
sdkmanager "platform-tools"

# Install system images for emulator
sdkmanager "system-images;android-30;google_apis;x86_64"

# Install emulator package
sdkmanager "emulator"
```

![Installing components](screenshots/install-components.gif)
*Installing required Android components*

### 3. Create and Start AVD
```bash
# Create AVD
avdmanager create avd -n TodoApp -k "system-images;android-30;google_apis;x86_64"

# Start emulator
emulator -avd TodoApp
```

![Creating and starting AVD](screenshots/create-start-avd.gif)
*Creating and starting the Android Virtual Device*

### 4. Install Pre-built APK
1. **Locate the APK**
   ```bash
   # The APK is located in the repository
   cd todo_projet/android/app/release/
   ```

2. **Install APK using ADB**
   ```bash
   # Make sure your emulator is running
   adb install app-release.apk
   ```

   ![Installing APK](screenshots/install-apk.gif)
   *Installing the pre-built APK on the emulator*

3. **Launch the App**
   - The app will appear in your emulator's app drawer
   - Tap the icon to launch

   ![Launching app](screenshots/launch-app.gif)
   *Launching the installed app*

## Expected Behavior

> ⚠️ **Note**: Due to known issues with the Postchain client integration, the app will have limited functionality.

### Working Features
![Working features demo](screenshots/working-features.gif)
*Demonstration of working features*

- App will launch successfully
- Key pair generation will work
- Account creation will succeed
- Basic UI will render

### Non-Working Features
![Non-working features](screenshots/non-working-features.gif)
*Demonstration of non-working features*

- Todo list retrieval will fail silently
- Adding/updating/deleting todos will not work
- Blockchain interactions will fail

## Troubleshooting

### Common Issues
1. **AVD Won't Start**
   ![AVD troubleshooting](screenshots/avd-troubleshooting.gif)
   *Common AVD issues and solutions*
   - Ensure virtualization is enabled in BIOS
   - Check if Hyper-V is disabled (Windows)
   - Verify system has enough RAM (4GB minimum)

2. **Build Failures**
   ![Build troubleshooting](screenshots/build-troubleshooting.gif)
   *Common build issues and solutions*
   ```bash
   # Clean and rebuild
   ./gradlew clean
   ./gradlew build
   ```

3. **Performance Issues**
   ![Performance optimization](screenshots/performance-optimization.gif)
   *Performance optimization tips*
   - Increase AVD memory in emulator settings
   - Close unnecessary background applications
   - Use hardware acceleration when possible

### System Requirements

![System requirements](screenshots/system-requirements.png)
*Minimum and recommended system requirements*

- **Operating System**: Windows 10/11, macOS 10.14+, or Linux
- **RAM**: Minimum 8GB (16GB recommended)
- **Storage**: At least 10GB free space
- **Processor**: Intel Core i5/AMD Ryzen 5 or better
- **Graphics**: OpenGL 2.0 capable system

## Additional Resources

![Additional resources](screenshots/additional-resources.png)
*Quick links to additional documentation*

- [Android Command Line Tools Guide](https://developer.android.com/studio/command-line)
- [AVD Command Line Guide](https://developer.android.com/studio/run/managing-avds-cmdline)
- [Gradle Build Tool](https://gradle.org/)
- [Android SDK Platform Tools](https://developer.android.com/studio/releases/platform-tools)
