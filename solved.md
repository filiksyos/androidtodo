# Resolved Issues

## Misleading Documentation for Postchain Client Implementation

### What Documentation Says

The official documentation suggests a simple implementation:

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

### Reality

![Their Documentation](screenshots/their%20gradle%20implementation.png)

However, this leads to a "couldn't resolve dependency" error. The actual required implementation needs explicit version specification:

![Our Implementation](screenshots/my%20gradle%20implementation.png)

```gradle
dependencies {
    implementation('net.postchain.client:postchain-client:3.23.1')
}
```

### Issues with Documentation

1. **Missing Version Requirement**
   - Documentation doesn't mention version specification is mandatory
   - No guidance on which versions are available
   - No mention of where to find available versions

2. **Package Registry Access**
   - Had to manually search GitLab package registry
   - No documentation about version compatibility
   - No mention of breaking changes between versions

3. **Best Practices Violation**
   - Documentation should include version information
   - Should provide guidance on version selection
   - Should list compatible version ranges

### Resolution Steps

1. Access GitLab package registry
2. Find latest stable version (3.23.1)
3. Implement with explicit version specification
4. Verify build success

This documentation issue adds unnecessary complexity to the integration process and should be reported to the Postchain team.
