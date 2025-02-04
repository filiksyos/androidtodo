# Todo App Documentation

## Overview

This document explains how the Todo app is designed to work and its current state, particularly focusing on the Postchain client integration issues.

## System Architecture

### Components

```mermaid
graph TB
    subgraph Presentation Layer
        UI[TodoListFragment]
        VM[TodoViewModel]
        App[TodoApplication]
    end

    subgraph Domain Layer
        Rep[ChromiaRepository]
        PC[PostchainClient]
    end

    subgraph Data Layer
        SPC[SafePostchainClient]
        KS[SecureKeyStorage]
        IO[IOWrapper]
    end

    subgraph Blockchain
        BC[Postchain Blockchain]
    end

    UI --> VM
    VM --> Rep
    App --> Rep
    Rep --> SPC
    Rep --> KS
    SPC --> PC
    PC --> BC
    SPC --> IO
```

## Current App Flow

### Initialization Process

```mermaid
sequenceDiagram
    participant App as TodoApplication
    participant Rep as ChromiaRepository
    participant KS as SecureKeyStorage
    participant SPC as SafePostchainClient
    participant BC as Blockchain

    App->>Rep: initialize()
    Rep->>KS: retrieveKeyPair()
    KS-->>Rep: return stored keypair
    Rep->>SPC: initialize with keypair
    SPC->>BC: attempt connection
    Note over SPC,BC: Connection established successfully

    Rep->>Rep: store account
    Note over Rep: account_1738710405265
    Rep-->>App: initialization complete
```

### Data Query Process (Expected vs Actual)

```mermaid
sequenceDiagram
    participant App
    participant Rep as ChromiaRepository
    participant SPC as SafePostchainClient
    participant PC as PostchainClient
    participant BC as Blockchain

    %% Expected Flow
    rect rgb(200, 255, 200)
        Note over App,BC: Expected Flow
        App->>Rep: getTodos()
        Rep->>SPC: query("get_todos")
        SPC->>PC: delegate query
        PC->>BC: send query
        BC-->>PC: return response
        PC-->>SPC: process response
        SPC-->>Rep: return todos
        Rep-->>App: display todos
    end

    %% Actual Flow with Error
    rect rgb(255, 200, 200)
        Note over App,BC: Actual Flow (With Error)
        App->>Rep: getTodos()
        Rep->>SPC: query("get_todos")
        SPC->>PC: delegate query
        PC->>BC: send query
        BC-->>PC: return response
        Note over PC: BoundedInputStream.readAllBytes missing
        PC--xSPC: Silent failure
        SPC--xRep: Query fails
        Rep--xApp: No data returned
    end
```

## Current Issues

### 1. Postchain Client Integration Problem

```mermaid
graph TD
    subgraph "Root Cause Analysis"
        A[Postchain Client Library] -->|Uses| B[Shaded Commons IO]
        B -->|Missing| C[readAllBytes method]
        C -->|Causes| D[Silent Failures]
    end

    subgraph "Failed Solutions"
        E[Custom IOWrapper] -->|Cannot Access| F[Shaded Classes]
        G[ProGuard Rules] -->|Cannot Protect| H[Already Shaded Classes]
    end

    subgraph "Impact"
        I[Query Operations] -->|Fail Silently| J[No Data Retrieved]
        J -->|Results In| K[Empty Todo List]
    end
```

### 2. Working Features

The following features are currently working as expected:

1. Key Pair Generation and Storage
2. Account Creation and Management
3. Basic UI Rendering
4. Repository Initialization

### 3. Non-Working Features

Features currently not working due to the Postchain client issue:

1. Todo List Retrieval
2. Adding New Todos
3. Updating Existing Todos
4. Deleting Todos

## Log Analysis

Based on the application logs, we can see:

1. Successful initialization:
   - Key pair retrieval works
   - Account creation successful
   - Repository initialization completes

2. Failed operations:
   - BoundedInputStream.readAllBytes method missing
   - Silent failures in SafePostchainClient
   - Query operations not completing

## Recommendations

1. Short-term workarounds:
   - Implement custom serialization/deserialization
   - Use alternative IO methods
   - Create a proxy layer for IO operations

2. Long-term solutions:
   - Work with Postchain team to fix Commons IO shading
   - Implement custom client without shaded dependencies
   - Use alternative blockchain client implementation
