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
```