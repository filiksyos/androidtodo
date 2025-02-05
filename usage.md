# Todo App Usage Guide

A blockchain-based todo list application that allows users to manage their tasks securely through Chromia blockchain technology.

![App Overview](screenshots/app-overview.png)
*Main screen of the Todo App*

## Features Overview

### Current Working Features
- **Account Management**: 
  - Create new accounts
  - Secure key pair generation
  - Account persistence
- **Basic UI**: 
  - Clean, intuitive interface
  - Task list view
  - Action buttons for task management

![Working Features](screenshots/working-features.png)
*Overview of currently working features*

### Limited Features (Due to Postchain Client Issue)
- Todo list retrieval
- Task creation
- Task updates
- Task deletion

## Using the App

### 1. First Launch
![First Launch](screenshots/first-launch.gif)
*App's first launch experience*

1. **Launch the App**
   - Locate "Todo App" in your device's app drawer
   - Tap to open

2. **Initial Setup**
   - The app will automatically generate a secure key pair
   - Your account will be created automatically
   - Wait for initialization to complete

### 2. Account Creation
![Account Creation](screenshots/account-creation.gif)
*Account creation process*

1. **On First Launch**
   - The app automatically creates your account
   - A unique identifier is generated
   - Your keys are stored securely

2. **Account Information**
   - Your account ID will be displayed
   - Format: `account_XXXXXXXXXX`
   - Keys are stored in secure storage

### 3. Main Interface
![Main Interface](screenshots/main-interface.png)
*App's main interface layout*

The main screen consists of:
- Task list area (top)
- Action buttons (bottom)
- Menu options (top right)

### 4. Task Management
![Task Management](screenshots/task-management.png)
*Task management interface*

> ⚠️ **Note**: These features are currently limited due to the Postchain client issue

1. **View Tasks**
   - Tasks are displayed in a scrollable list
   - Each task shows:
     - Title
     - Status (complete/incomplete)
     - Creation date

2. **Add New Task** (Limited)
   - Tap the "+" button
   - Enter task details
   - Tap "Save"

   ![Add Task](screenshots/add-task.gif)
   *Adding a new task*

3. **Update Tasks** (Limited)
   - Tap a task to edit
   - Modify details
   - Save changes

   ![Edit Task](screenshots/edit-task.gif)
   *Editing an existing task*

4. **Delete Tasks** (Limited)
   - Swipe left on a task
   - Tap delete icon
   - Confirm deletion

   ![Delete Task](screenshots/delete-task.gif)
   *Deleting a task*

### 5. Settings and Configuration
![Settings Screen](screenshots/settings-screen.png)
*App settings and configuration*

Available settings:
- View account information
- Check blockchain connection status
- View app version
- Access help resources

## Error Messages and Troubleshooting

### Common Issues
![Error Messages](screenshots/error-messages.png)
*Common error messages and their meaning*

1. **Connection Issues**
   - Check internet connectivity
   - Verify blockchain node status
   - Try restarting the app

2. **Task Operation Failures**
   - Due to known Postchain client issue
   - Operations will fail silently
   - No data loss will occur

### Error Recovery
![Error Recovery](screenshots/error-recovery.gif)
*Steps to recover from common errors*

1. **App Not Responding**
   - Force close the app
   - Clear app cache
   - Restart the app

2. **Account Issues**
   - Account data is stored securely
   - No manual recovery needed
   - Reinstall if persistent

## Security Features

### Key Storage
![Security Features](screenshots/security-features.png)
*Overview of security implementation*

- Keys stored in Android Keystore
- Encrypted storage for sensitive data
- Secure blockchain communication

### Data Protection
- All data is blockchain-backed
- Local caching for performance
- Encrypted communication

## Additional Information

### App States
![App States](screenshots/app-states.png)
*Different states of the application*

1. **Normal Operation**
   - Account created
   - UI responsive
   - Basic features available

2. **Limited Operation**
   - Blockchain queries failing
   - Task operations unavailable
   - Account management working

### Performance Considerations
- App remains responsive
- Minimal battery impact
- Efficient data caching

## Support and Feedback

### Getting Help
- Check troubleshooting guide
- Review error messages
- Contact support team

### Reporting Issues
- Include app version
- Describe steps to reproduce
- Attach relevant screenshots

## Version Information
- App Version: 1.0.0
- Minimum Android: API 30
- Target Android: API 33
