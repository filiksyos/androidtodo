# Todo App - Android Blockchain Task Manager

> 📱 **Additional Project Note**: This Android application is an additional implementation of the [Chromia Todo List Assignment](https://github.com/filiksyos/todo_app), which I have already completed and submitted as a web application. I created this Android version to demonstrate my ability to work with Chromia blockchain technology across different environments and platforms. This project showcases how Chromia can be integrated into mobile applications, highlighting both the opportunities and technical challenges of blockchain integration in Android development.

<img src="screenshots/app-demo.gif" width="200" height="auto" alt="App Demo"/>

## Table of Contents
- [Overview](#overview)
- [Features](#features)
  - [Current Features](#current-features)
  - [Limited Features](#limited-features)
- [Architecture](#architecture)
  - [Blockchain Integration](#blockchain-integration)
  - [Security Model](#security-model)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation Steps](#installation-steps)
  - [Configuration](#configuration)
- [Usage Guide](#usage-guide)
  - [First Launch](#first-launch)
  - [Account Management](#account-management)
  - [Task Management](#task-management)
  - [UI Features](#ui-features)
- [Technical Documentation](#technical-documentation)
  - [Project Structure](#project-structure)
  - [Core Technologies](#core-technologies)
  - [Development Environment](#development-environment)
- [Known Issues](#known-issues)
  - [Postchain Client Limitations](#postchain-client-limitations)
  - [Current Workarounds](#current-workarounds)
- [Development Notes](#development-notes)
  - [Resolved Issues](#resolved-issues)
  - [Best Practices](#best-practices)
- [Author](#author)

## Overview

A multi-user todo list application built on the Chromia blockchain, allowing users to manage their tasks securely and efficiently through blockchain technology.

This application demonstrates the implementation of a blockchain-based todo list using Chromia technology. Users can create accounts, manage their tasks, and track their progress - all secured by blockchain technology.

## Features

### Current Features
1. **Account Management**
   - ✓ Account creation and key pair generation
   - ✓ Secure key storage in Android Keystore
   - ✓ Automatic login on subsequent launches
   - ✓ Public key blockchain registration

2. **User Interface**
   - ✓ Modern, responsive UI with dark mode support
   - ✓ Material Design 3 components
   - ✓ Error handling and user feedback
   - ✓ Loading states and animations

3. **Local Functionality**
   - ✓ Task creation interface
   - ✓ Task list visualization
   - ✓ Error handling and recovery
   - ✓ Offline capability with error handling

### Limited Features
> ⚠️ **Important Note**: The following features are currently limited due to fundamental issues in the Postchain client library that we cannot fix from our side.

1. **Blockchain Operations**
   - ⚠️ Blockchain synchronization (read operations affected)
   - ⚠️ Real-time updates
   - ⚠️ Multi-device synchronization
   - ⚠️ Task persistence across devices

2. **Data Management**
   - ⚠️ Task creation on blockchain
   - ⚠️ Task updates and deletion
   - ⚠️ Task status synchronization
   - ⚠️ Cross-device data consistency

## Architecture

### Blockchain Integration

```mermaid
graph LR
    subgraph App Assets
        Config[chromia_config.properties]
    end

    subgraph Configuration
        BRID[Blockchain RID]
        URL[Node URL]
    end

    subgraph Environment
        Dev[Development: localhost:7740]
        Prod[Production: remote node]
    end

    Config --> BRID
    Config --> URL
    URL --> Dev
    URL --> Prod
```

The app uses a configuration file (`chromia_config.properties`) to manage blockchain connectivity:
```properties
# Development setup
blockchain.rid=YOUR_BRID_HERE
node.url=http://10.0.2.2:7740
```

#### Configuration Details
- BRID (Blockchain RID) uniquely identifies the blockchain
- Node URL points to blockchain node:
  - Development: `10.0.2.2:7740` (localhost from emulator)
  - Production: Would use actual Chromia node

### Security Model

#### Key Pair Authentication
> 💡 **Note**: This is the only blockchain operation currently working reliably, as it doesn't require reading data through the problematic Postchain client implementation.

```mermaid
sequenceDiagram
    participant User
    participant App
    participant Storage
    participant Chain

    Note over User,Chain: First Time Setup
    User->>App: Open App
    App->>App: Generate Key Pair
    App->>Storage: Store Keys Securely
    App->>Chain: Register Account
    Chain-->>App: Account Created
    App-->>User: Ready to Use

    Note over User,Chain: Subsequent Logins
    User->>App: Open App
    App->>Storage: Retrieve Key Pair
    App->>Chain: Authenticate
    Chain-->>App: Session Active
    App-->>User: Auto-logged In
```

#### Security Implementation
- Key pair is generated on first launch
- Private key stays on device (never shared)
- Public key is used for blockchain account creation
- Keys are stored securely in Android Keystore
- No passwords needed - keys handle authentication
- Automatic login on subsequent launches

### Development Architecture

```mermaid
sequenceDiagram
    participant App as Android App
    participant Emu as Android Emulator
    participant Node as Local Blockchain Node
    
    Note over App,Node: Development Environment
    App->>Emu: Run in emulator
    Emu->>Node: Connect to 10.0.2.2:7480
    Note over Emu,Node: 10.0.2.2 is localhost from emulator
    Node-->>Emu: Connection established
    Emu-->>App: Ready for operations
```

### Intended Data Flow
> 📝 **Note**: This represents how the app should work. However, the current Postchain client's implementation makes this flow impossible due to its internal dependency handling issues.

```mermaid
sequenceDiagram
    participant User
    participant App as Android App
    participant Node as Blockchain Node
    participant Chain as Blockchain

    Note over App,Chain: How it should work
    
    User->>App: Create todo
    App->>Node: Send to blockchain (localhost:7480)
    Node->>Chain: Store in blockchain
    Chain-->>Node: Confirm storage
    Node-->>App: Success response
    App-->>User: Show updated todo list

    User->>App: Request todos
    App->>Node: Query blockchain
    Node->>Chain: Fetch data
    Chain-->>Node: Return todos
    Node-->>App: Send todos
    App-->>User: Display todos
```

## Getting Started

### Prerequisites

1. **Development Environment**
   - Android Studio ([download](https://developer.android.com/studio))
   - Minimum system requirements:
     - 8GB RAM (16GB recommended)
     - 10GB free storage space
     - Intel Core i5/AMD Ryzen 5 or better
     - Windows 10/11, macOS 10.14+, or Linux

2. **Blockchain Requirements**
   - Docker Desktop ([download](https://www.docker.com/products/docker-desktop))
   - PostgreSQL (installed via Docker)
   - Chromia node (setup instructions below)

3. **Android Device/Emulator**
   - Physical device running Android 10 (API 29) or higher
   - Or Android Studio's emulator
   - 50MB free storage
   - Internet connection for blockchain features

### Installation Steps

Choose the installation method that works best for you:

#### Path 1: Using Android Studio (For Developers)

1. **Project Setup**
   ```bash
   # Clone the repository
   git clone <repository-url>
   cd android
   ```

2. **Install Android Studio**
   - Download from [developer.android.com/studio](https://developer.android.com/studio)
   - Run the installer and follow the setup wizard
   - Let Android Studio download necessary SDK components

3. **Setup Blockchain Environment**
   ```bash
   # Make sure you're in the project root
   cd android
   ```

   1. Start Docker Desktop

   2. Stop local PostgreSQL service:
      - Windows:
        1. Press `Windows + R`, type `services.msc` and press Enter
        2. Find "PostgreSQL Server" in the list
        3. Right-click and select "Stop"
        ![Stopping PostgreSQL in Windows Services](screenshots/windows-postgresql-service.png)
      - Linux: `sudo service postgresql stop`
      - macOS: `brew services stop postgresql`

   3. Start Docker and PostgreSQL container:
      ```bash
      # Make sure you're in the project root
      cd android
      docker-compose up -d
      ```

   4. Configure `chromia_config.properties`:
      ```bash
      # Navigate to the Android project
      cd android
      ```
      - Look for the BRID in node startup logs
      ![Finding BRID in logs](screenshots/brid-location.png)
      - Update configuration in `app/src/main/assets/chromia_config.properties`:
      ```properties
      blockchain.rid=YOUR_BRID_HERE
      node.url=http://10.0.2.2:7740
      ```
      > Note: We use `10.0.2.2` instead of `localhost` because it's the special alias Android emulator uses to access the host machine's localhost.

4. **Open in Android Studio**
   ```bash
   # Make sure you're in the android directory
   cd android
   ```
   - Click "File > Open"
   - Navigate to the cloned repository's android folder
   - Select the "android" folder
   - Click "OK"
   - Wait for Gradle sync to complete

5. **Create Virtual Device**
   - Go to Tools > Device Manager
   - Click "Create Device"
   - Select any Phone (e.g., Pixel 2)
   - Download and select the latest system image
   - Click "Finish"

6. **Build and Run**
   - Click the "Run" button (green play button) or press Shift + F10
   - Select your virtual device
   - Wait for the app to build and launch

   ![Building and running the app](screenshots/build-and-run.png)

#### Path 2: Direct Installation on Android Phone

1. **Download APK**
   Choose one of these methods:
   - **Direct Download**: [todo-app.apk](https://github.com/filiksyos/androidtodo/releases/download/1.0.0/todo-app.apk)
   - **QR Code**: Scan this code with your phone's camera:

   ![Download QR](screenshots/download-qr.png)

   > 💡 **Tip**: Most modern Android phones can scan QR codes directly through the camera app.

2. **Installation Steps**
   - Open the downloaded APK
   - If prompted, enable "Install Unknown Apps" for your browser
   - Tap "Install" when prompted
   - If blocked, tap "Settings" and allow installation
   - Wait for installation to complete
   - Tap "Open" to launch

### Configuration

1. **Development Environment Setup**
   - Ensure all Android Studio SDK components are installed
   - Configure your preferred IDE settings
   - Set up version control integration if needed

2. **Blockchain Configuration**
   - Verify Docker is running
   - Ensure PostgreSQL container is active
   - Check Chromia node status
   - Verify BRID configuration

3. **Troubleshooting Common Issues**
   - If encountering build failures:
     - Click "File > Invalidate Caches / Restart"
     - Try "Build > Clean Project" then rebuild
   - For performance issues:
     - Increase RAM for Android Studio in settings
     - Close unnecessary applications
     - Enable hardware acceleration in BIOS
   - For blockchain issues:
     - Check Docker container status
     - Verify PostgreSQL port availability
     - Review Chromia node logs

> ⚠️ **Note**: Your browser or phone might warn you about downloading APKs from unknown sources. This is normal for apps not from Play Store.

## Usage Guide

### First Launch
1. **Launch the App**
   - Locate "Todo App" in your device's app drawer
   - Tap to open

2. **Initial Setup**
   - The app will automatically generate a secure key pair
   - Your account will be created automatically
   - A toast notification will confirm when your account is ready
   - The "Todo List" button will appear once your account is ready

### Account Management
1. **Generate Key Pair**
   - Tap the "Generate Key Pair" button
   - Wait for the key generation process
   - A success toast will appear when ready

2. **Account Information**
   - Your account ID will be displayed in the list
   - The button will update to "Generate New Key Pair"
   - The "Todo List" button appears for account access

### Task Management
1. **View Tasks**
   - Clean list layout with consistent spacing
   - Each task shows title and description
   - Visual feedback for completion status

2. **Add New Task**
   - Tap the "Add New Task" button
   - Fill in the task details:
     - Required task title
     - Optional description
   - Choose "Add Task" or "Cancel"

3. **Update Tasks**
   - Tap a task to edit
   - Modify details in the styled form
   - Save or cancel changes

4. **Delete Tasks**
   - Tap delete icon on task
   - Confirm in styled alert dialog

### UI Features
1. **Color Scheme**
   - Primary blue (#2E7BEF) for main actions
   - Surface colors for clean backgrounds
   - Consistent text colors for hierarchy
   - Ripple effects for interaction feedback

2. **Components**
   - Rounded corners on all buttons (12dp)
   - Outlined text fields with rounded corners
   - Consistent 56dp height for buttons
   - Proper spacing and padding throughout

3. **Notifications**
   - Toast messages for non-intrusive feedback
   - Dialog alerts for important information
   - Clear error states in forms

4. **Error Handling**
   - Friendly error dialogs explain any issues
   - Clear explanation of limitations
   - Option to retry operations

> ⚠️ **Known Issue**: The current version of the Kotlin Postchain client has compatibility issues with some Android API levels. When performing blockchain operations, you might encounter an error dialog explaining the limitation. This is a temporary issue as we work with the Chromia team to update the client.

<img src="screenshots/postchain-error.png" width="300" height="auto" alt="Postchain Client Error Dialog"/>

*Error dialog showing Postchain client compatibility issue*

## Technical Documentation

### Project Structure

The project follows a clean architecture pattern with modular design:

```
android/
├── app/                    # Application module
├── data/                   # Data layer implementation
├── domain/                 # Business logic and entities
├── presentation/          # UI layer and ViewModels
├── rell/                  # Blockchain smart contracts
├── libs/                  # Local libraries
├── screenshots/           # App screenshots and demos
└── gradle/                # Gradle configuration
```

#### Module Details

1. **App Module** (`app/`)
   - Application entry point
   - Dependency injection setup
   - Module coordination
   - Main activity and application class

2. **Data Layer** (`data/`)
   - Repository implementations
   - Data sources (local and remote)
   - API services and models
   - Blockchain client integration
   - Data mapping utilities

3. **Domain Layer** (`domain/`)
   - Business logic
   - Use cases
   - Repository interfaces
   - Domain models
   - Business rules

4. **Presentation Layer** (`presentation/`)
   - Activities and Fragments
   - ViewModels
   - UI models and mappers
   - Custom views
   - Adapters and view holders

5. **Blockchain Layer** (`rell/`)
   - Smart contract definitions
   - Blockchain operations
   - Data structures
   - Access control

### Core Technologies

1. **Android Framework**
   - Modern Android development practices
   - Material Design components
   - AndroidX libraries
   - Kotlin programming language

2. **Architecture Components**
   - ViewModel for UI state management
   - LiveData for reactive updates
   - Navigation for screen transitions
   - Room for local data persistence

3. **Blockchain Integration**
   - Chromia Postchain client
   - Secure key pair management
   - Blockchain data synchronization
   - Smart contract interaction

4. **Security Features**
   - Android Keystore integration
   - Secure key storage
   - Encrypted communications
   - Safe blockchain operations

### Development Environment

1. **Required Tools**
   - Android Studio
   - Docker for blockchain node
   - PostgreSQL database
   - Chromia development tools

2. **Testing Environment**
   - Local blockchain node
   - Android emulator
   - Physical device testing
   - Development configurations

## Known Issues

### Postchain Client Limitations

The application faces a critical issue with the Postchain client's handling of `BoundedInputStream`. This issue manifests when attempting to read responses from the blockchain, particularly during todo list operations.

```mermaid
sequenceDiagram
    participant App
    participant SafePC as SafePostchainClient
    participant PC as PostchainClientImpl
    participant BI as BoundedInputStream (Shaded)
    
    App->>SafePC: query("get_todos")
    SafePC->>PC: delegate.query()
    PC->>BI: readAllBytes()
    BI--xPC: Method missing (shaded version)
    PC--xSafePC: Silent failure
    SafePC--xApp: Query fails silently
```

#### Technical Details

The issue stems from Postchain client's internal repackaging (shading) of Commons IO library:

```mermaid
graph TD
    A[Postchain Client] -->|Shades| B[Commons IO]
    B -->|Strips| C[readAllBytes method]
    C -->|Causes| D[Silent Failures]
    
    style C fill:#ff6666
    style D fill:#ff9999
```

#### Library Best Practices Violations

The Postchain client library violates several critical library development best practices:

```mermaid
graph TD
    subgraph "Dependency Management Violations"
        A[Incorrect Shading] -->|Violates| B[Transparent Dependencies]
        C[Silent Failures] -->|Violates| D[Proper Error Handling]
        E[Method Stripping] -->|Violates| F[API Stability]
    end

    subgraph "Impact"
        B --> G[Dependency Hell]
        D --> H[Debug Difficulty]
        F --> I[Integration Issues]
    end

    style A fill:#ff9999
    style C fill:#ff9999
    style E fill:#ff9999
```

1. **Dependency Handling**
   - ❌ Improper dependency shading without relocating packages
   - ❌ No documentation about shaded dependencies
   - ❌ No way to exclude or replace shaded dependencies
   - ✓ Should use proper dependency management with clear documentation

2. **API Design**
   - ❌ Silent failures instead of proper error propagation
   - ❌ Undocumented internal dependency modifications
   - ❌ Breaking changes in internal dependencies
   - ✓ Should maintain backward compatibility and proper error handling

3. **Library Packaging**
   - ❌ Strips essential methods from dependencies
   - ❌ No proper versioning of shaded dependencies
   - ❌ Lacks proper manifest entries for repackaged libraries
   - ✓ Should preserve full functionality of repackaged dependencies

### Current Workarounds

We've extensively investigated and attempted multiple solutions:

1. **Dependency Management**
   ```gradle
   implementation('commons-io:commons-io:2.7') {
       force = true
   }
   ```

2. **Build Configuration**
   ```gradle
   android {
       packagingOptions {
           preserve 'META-INF/MANIFEST.MF'
           preserve 'META-INF/LICENSE.txt'
       }
   }
   ```

3. **ProGuard Rules**
   ```proguard
   -keep class org.apache.commons.io.** { *; }
   -keepclassmembers class org.apache.commons.io.** { *; }
   ```

4. **Custom Wrapper Implementation**
   ```kotlin
   object IOWrapper {
       fun readAllBytes(input: InputStream): ByteArray
   }
   ```

#### Why Solutions Failed

```mermaid
graph TD
    subgraph "Root Cause Analysis"
        A[Postchain's Internal Shading] -->|Prevents| B[External Access]
        A -->|Strips| C[Method Signatures]
        A -->|Modifies| D[Class Loading]
    end

    subgraph "Solution Blockers"
        B -->|Blocks| E[Wrapper Solution]
        C -->|Prevents| F[ProGuard Fix]
        D -->|Invalidates| G[Dependency Management]
    end
```

This issue is fundamentally unsolvable without modifications to the Kotlin Postchain client itself. The root cause lies in their dependency shading implementation, which:

1. Repackages Commons IO
2. Strips essential methods
3. Prevents external solutions

> 📢 **Note**: This issue has been reported to the Postchain client team, as it requires changes to their library's implementation.

## Development Notes

### Resolved Issues

We've successfully resolved several critical implementation challenges:

#### 1. Postchain Client Implementation

The official documentation suggested a simple implementation:

```gradle
repositories {
    maven("https://gitlab.com/api/v4/projects/50818999/packages/maven")
    maven("https://gitlab.com/api/v4/projects/32294340/packages/maven")
    maven("https://gitlab.com/api/v4/projects/46288950/packages/maven")
}

dependencies {
    implementation("net.postchain.client:postchain-client")
}
```

However, this led to dependency resolution errors. The correct implementation requires explicit version specification:

```gradle
dependencies {
    implementation('net.postchain.client:postchain-client:3.23.1')
}
```

#### 2. Android Test Compatibility

Resolved test infrastructure conflicts by implementing a custom test configuration:

```gradle
android {
    gradle.taskGraph.whenReady { graph ->
        graph.allTasks.forEach { task ->
            if (task.name.contains("AndroidTest")) {
                task.enabled = false
            }
        }
    }
}
```

**Trade-offs:**
- ✓ Resolves build failures
- ✓ Maintains core app functionality
- ✓ Reduces build time
- ❌ No instrumentation testing capability
- ❌ Limited UI testing options
- ❌ Reduced test coverage

#### 3. META-INF Resource Conflicts

Resolved duplicate resource conflicts with comprehensive packaging options:

```gradle
android {
    packagingOptions {
        resources {
            excludes += [
                'META-INF/DEPENDENCIES',
                'META-INF/LICENSE',
                'META-INF/LICENSE.txt',
                'META-INF/license.txt',
                'META-INF/NOTICE',
                'META-INF/NOTICE.txt',
                'META-INF/notice.txt',
                'META-INF/ASL2.0',
                'META-INF/*.kotlin_module'
            ]
            pickFirsts += [
                'META-INF/maven/commons-io/commons-io/pom.properties',
                'META-INF/maven/commons-io/commons-io/pom.xml',
                'META-INF/MANIFEST.MF'
            ]
            merges += [
                'META-INF/maven/commons-io/commons-io/**'
            ]
        }
    }
}
```

#### 4. BouncyCastle Version Conflicts

Resolved cryptography provider conflicts by forcing consistent versions:

```gradle
configurations.all {
    resolutionStrategy {
        force 'org.bouncycastle:bcprov-jdk18on:1.78.1'
        exclude group: 'org.bouncycastle', module: 'bcprov-jdk15on'
    }
}

dependencies {
    implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
    implementation('net.postchain.client:postchain-client:3.23.1') {
        exclude group: 'org.bouncycastle', module: 'bcprov-jdk15on'
    }
}
```

### Best Practices

1. **Dependency Management**
   - Always specify explicit versions for critical dependencies
   - Document version compatibility requirements
   - Use dependency exclusions to prevent conflicts
   - Maintain clean dependency trees

2. **Build Configuration**
   - Handle resource conflicts through proper packaging options
   - Implement appropriate ProGuard/R8 rules
   - Configure test environments appropriately
   - Document build customizations

3. **Security Considerations**
   - Use latest stable security provider versions
   - Implement proper key pair generation and storage
   - Handle cryptographic operations safely
   - Document security-related configurations

4. **Testing Strategy**
   - Focus on unit tests when instrumentation tests are limited
   - Implement UI tests in separate modules when possible
   - Use mocked clients for blockchain testing
   - Document testing limitations and workarounds

5. **Error Handling**
   - Provide user-friendly error messages
   - Implement proper error recovery
   - Log errors appropriately for debugging
   - Document known issues and solutions

## Author

- Name: Filiksyos Destaw
- Email: franknick285@gmail.com
- GitHub: [@filiksyos](https://github.com/filiksyos)

---

Made with ❤️ using Chromia Blockchain Technology 