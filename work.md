# Todo App Documentation

## Overview

This document explains how the Todo app was designed to work with the Chromia blockchain and the current limitations we're facing.

## How It Should Work

### Blockchain Configuration
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
blockchain.rid=F8D6FA48C1F1483726E490BCEBC62A2EBC9850CDFA15FAAC8BBA76F64B9B7B6B
node.url=http://10.0.2.2:7740

```

- BRID (Blockchain RID) uniquely identifies the blockchain
- Node URL points to blockchain node:
  - Development: `10.0.2.2:7740` (localhost from emulator)
  - Production: Would use actual Chromia node
- Configuration can be switched between development/production

### Key Pair Authentication
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

The app uses public-private key pairs for secure authentication:
- Key pair is generated on first launch
- Private key stays on device (never shared)
- Public key is used for blockchain account creation
- Keys are stored securely in Android Keystore
- No passwords needed - keys handle authentication
- Automatic login on subsequent launches

### Development Environment Setup
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

## Current Reality

### Current Data Flow
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

### Root Cause Analysis
```mermaid
graph TD
    subgraph "Root Cause"
        A[Postchain's Internal Shading] -->|Prevents| B[External Access]
        A -->|Strips| C[Method Signatures]
        A -->|Modifies| D[Class Loading]
    end

    subgraph "Impact"
        B -->|Blocks| E[Wrapper Solution]
        C -->|Prevents| F[ProGuard Fix]
        D -->|Invalidates| G[Dependency Management]
    end

    style A fill:#ff9999
    style C fill:#ff9999
    style D fill:#ff9999
```

### Technical Details

The issue stems from Postchain client's internal repackaging (shading) of Commons IO library:

1. **Library Shading Problem**
   - Postchain repackages Commons IO internally
   - Original class signatures are modified
   - Version information is stripped

2. **Method Access Issue**
   - Can't access shaded classes
   - Can't modify internal implementation
   - Can't intercept before shading

3. **Silent Failure Chain**
   - BoundedInputStream fails to find method
   - PostchainClientImpl swallows exception
   - SafePostchainClient receives no data
   - App shows error dialog to user

### Attempted Solutions

We've tried multiple approaches to resolve this:

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

### Why Solutions Failed

```mermaid
graph TD
    subgraph "Solution Blockers"
        A[Shaded Classes] -->|Prevents| B[External Override]
        C[Stripped Methods] -->|Blocks| D[Reflection Access]
        E[Modified ClassLoader] -->|Invalidates| F[Dependency Injection]
    end

    subgraph "User Impact"
        B --> G[Silent Failures]
        D --> H[Data Loss]
        F --> I[Poor UX]
    end
```

### Current Workarounds

1. **Error Handling**
   - Friendly error dialogs explain the issue
   - Clear messaging about temporary limitations
   - Option to retry operations

2. **Feature Limitations**
   - Account creation still works (no blockchain reads)
   - UI remains functional and responsive
   - Clear indication of limited features

3. **User Communication**
   - Transparent about current limitations
   - Regular updates on fix progress
   - Alternative workflows where possible

### Next Steps

The issue requires changes to the Postchain client library itself:

1. **Short Term**
   - Continue showing user-friendly error messages
   - Document the issue for other developers
   - Monitor for Postchain client updates

2. **Long Term**
   - Work with Chromia team on client update
   - Consider alternative blockchain client implementation
   - Improve error handling architecture