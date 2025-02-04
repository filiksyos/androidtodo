# Installation Guide for Todo App

## Prerequisites

1. **Java Development Kit (JDK)**
   - Install JDK 11 or higher
   - Set JAVA_HOME environment variable

2. **Android Studio**
   - Download and install [Android Studio](https://developer.android.com/studio)
   - Minimum version: Arctic Fox (2020.3.1) or higher

## Setting Up Android Virtual Device (AVD)

1. **Open Android Studio**
   ```bash
   # Launch Android Studio and wait for initial setup to complete
   ```

2. **Create New Virtual Device**
   - Go to Tools > Device Manager
   - Click "Create Virtual Device"
   - Select Phone category
   - Choose "Pixel 2" (or any other device)
   - Click "Next"

3. **Select System Image**
   - Choose "API 30" (Android 11.0)
   - If not downloaded, click "Download" next to the system image
   - Click "Next"
   - Click "Finish"

## Project Setup

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd todo_projet
   ```

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

## Running the App

1. **Start the AVD**
   - In Device Manager, click the play button (▶️) next to your virtual device
   - Wait for the emulator to fully boot up

2. **Build and Run**
   ```bash
   # In Android Studio
   Click the "Run" button (green play button) or press Shift + F10
   ```

3. **Alternative: Command Line Build**
   ```bash
   # From the android directory
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

## Expected Behavior

> ⚠️ **Note**: Due to known issues with the Postchain client integration, the app will have limited functionality.

### Working Features
- App will launch successfully
- Key pair generation will work
- Account creation will succeed
- Basic UI will render

### Non-Working Features
- Todo list retrieval will fail silently
- Adding/updating/deleting todos will not work
- Blockchain interactions will fail

## Troubleshooting

1. **Gradle Build Issues**
   ```bash
   # Clean and rebuild
   ./gradlew clean
   ./gradlew build
   ```

2. **AVD Won't Start**
   - Ensure virtualization is enabled in BIOS
   - Check if Hyper-V is disabled (Windows)
   - Verify system has enough RAM (4GB minimum recommended)

3. **App Crashes on Launch**
   - Check logcat for detailed error messages
   - Ensure all dependencies are properly synced
   - Verify Android SDK version matches project requirements

4. **Performance Issues**
   - Increase AVD memory in Device Manager settings
   - Close unnecessary background applications
   - Consider using a hardware accelerator (HAXM for Intel processors)

## System Requirements

- **Operating System**: Windows 10/11, macOS 10.14+, or Linux
- **RAM**: Minimum 8GB (16GB recommended)
- **Storage**: At least 10GB free space
- **Processor**: Intel Core i5/AMD Ryzen 5 or better
- **Graphics**: OpenGL 2.0 capable system

## Additional Resources

- [Android Studio User Guide](https://developer.android.com/studio/intro)
- [AVD Manager Guide](https://developer.android.com/studio/run/managing-avds)
- [Gradle Build Tool](https://gradle.org/)
