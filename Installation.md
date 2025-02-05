# Android Setup Guide

This guide provides instructions for setting up the Todo App development environment on your system. Choose the appropriate path based on whether you already have Android Studio installed.

## Path 1: For Users with Android Studio Installed

If you already have Android Studio installed, follow these steps:

### 1. Project Setup
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

### 2. Run the App
1. **Start AVD**
   - Go to Tools > Device Manager
   - Click the play button (▶️) next to your existing virtual device
   - Wait for the emulator to boot up

2. **Build and Run**
   - Click the "Run" button (green play button) or press Shift + F10

## Path 2: For Users Without Android Studio

If you don't have Android Studio installed, you can set up just the Android Virtual Device (AVD):

### 1. Install Command Line Tools
1. **Download Android Command Line Tools**
   - Visit [Android Studio Downloads](https://developer.android.com/studio#command-tools)
   - Download "Command line tools only"
   - Extract the downloaded zip file

2. **Set Up Environment Variables**
   ```bash
   # Add these to your system environment variables
   ANDROID_HOME=<path-to-android-sdk>
   PATH=%ANDROID_HOME%\cmdline-tools\latest\bin;%PATH%
   ```

### 2. Install Required Components
```bash
# Install basic Android SDK components
sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"

# Install system images for emulator
sdkmanager "system-images;android-30;google_apis;x86_64"

# Install emulator package
sdkmanager "emulator"
```

### 3. Create and Start AVD
```bash
# Create AVD
avdmanager create avd -n TodoApp -k "system-images;android-30;google_apis;x86_64"

# Start emulator
emulator -avd TodoApp
```

### 4. Build and Run Project
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

### Common Issues
1. **AVD Won't Start**
   - Ensure virtualization is enabled in BIOS
   - Check if Hyper-V is disabled (Windows)
   - Verify system has enough RAM (4GB minimum)

2. **Build Failures**
   ```bash
   # Clean and rebuild
   ./gradlew clean
   ./gradlew build
   ```

3. **Performance Issues**
   - Increase AVD memory in emulator settings
   - Close unnecessary background applications
   - Use hardware acceleration when possible

### System Requirements

- **Operating System**: Windows 10/11, macOS 10.14+, or Linux
- **RAM**: Minimum 8GB (16GB recommended)
- **Storage**: At least 10GB free space
- **Processor**: Intel Core i5/AMD Ryzen 5 or better
- **Graphics**: OpenGL 2.0 capable system

## Additional Resources

- [Android Command Line Tools Guide](https://developer.android.com/studio/command-line)
- [AVD Command Line Guide](https://developer.android.com/studio/run/managing-avds-cmdline)
- [Gradle Build Tool](https://gradle.org/)
- [Android SDK Platform Tools](https://developer.android.com/studio/releases/platform-tools)
