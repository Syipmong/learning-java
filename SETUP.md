# Java Development Environment Setup Guide

This guide will help you set up a complete Java development environment on Windows.

## 🔧 Prerequisites

Before starting, ensure you have:
- Windows 10 or later
- Administrator privileges
- Stable internet connection
- At least 4GB RAM (8GB recommended)
- 10GB free disk space

## ☕ Java Development Kit (JDK) Installation

### Step 1: Download JDK
1. Visit [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/)
2. Download JDK 17 or later (LTS version recommended)
3. Choose Windows x64 Installer

### Step 2: Install JDK
1. Run the downloaded installer
2. Follow installation wizard
3. Note the installation path (usually `C:\Program Files\Java\jdk-17`)

### Step 3: Set Environment Variables
1. Open System Properties (Windows + X → System → Advanced system settings)
2. Click "Environment Variables"
3. Under System Variables, click "New"
4. Variable name: `JAVA_HOME`
5. Variable value: `C:\Program Files\Java\jdk-17` (your JDK path)
6. Find `Path` variable and click "Edit"
7. Add `%JAVA_HOME%\bin`

### Step 4: Verify Installation
Open Command Prompt or PowerShell and run:
```powershell
java -version
javac -version
```

## 🚀 IDE Installation

### Option 1: IntelliJ IDEA (Recommended)

#### Download and Install
1. Visit [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/)
2. Download Community Edition (free)
3. Run installer and follow wizard
4. Launch IntelliJ IDEA

#### Initial Setup
1. Choose your UI theme
2. Install recommended plugins
3. Import settings (if migrating)
4. Create your first project

#### Essential Plugins
- **Lombok**: Reduces boilerplate code
- **SonarLint**: Code quality analysis
- **GitToolBox**: Enhanced Git integration
- **Rainbow Brackets**: Better code readability
- **String Manipulation**: Text processing utilities

### Option 2: Eclipse IDE

#### Download and Install
1. Visit [Eclipse Downloads](https://www.eclipse.org/downloads/)
2. Download Eclipse IDE for Java Developers
3. Extract to desired location
4. Run eclipse.exe

#### Initial Setup
1. Choose workspace location
2. Install Java Development Tools (JDT)
3. Configure JDK in Preferences

### Option 3: Visual Studio Code

#### Download and Install
1. Visit [VS Code Downloads](https://code.visualstudio.com/)
2. Download Windows installer
3. Run installer with default settings

#### Java Extensions
Install these extensions:
- **Extension Pack for Java**: Complete Java support
- **Spring Boot Tools**: Spring development
- **Debugger for Java**: Advanced debugging
- **Test Runner for Java**: Unit testing support

## 🏗️ Build Tools

### Maven Installation

#### Download Maven
1. Visit [Apache Maven](https://maven.apache.org/download.cgi)
2. Download Binary zip archive
3. Extract to `C:\Program Files\Apache\maven`

#### Set Environment Variables
1. Add `MAVEN_HOME` variable: `C:\Program Files\Apache\maven`
2. Add `%MAVEN_HOME%\bin` to Path

#### Verify Installation
```powershell
mvn -version
```

### Gradle Installation (Alternative)

#### Using Gradle Wrapper (Recommended)
Most projects include Gradle Wrapper, no separate installation needed.

#### Manual Installation
1. Visit [Gradle Downloads](https://gradle.org/releases/)
2. Download Binary-only distribution
3. Extract and add to Path
4. Verify: `gradle -version`

## 🗄️ Database Setup

### H2 Database (For Learning)
- Embedded database
- No separate installation needed
- Great for tutorials and testing

### PostgreSQL (Recommended for Projects)

#### Download and Install
1. Visit [PostgreSQL Downloads](https://www.postgresql.org/download/windows/)
2. Download Windows installer
3. Run installer and follow wizard
4. Remember the superuser password

#### Post-Installation
1. Add `C:\Program Files\PostgreSQL\15\bin` to Path
2. Test connection: `psql -U postgres`

### MySQL (Alternative)

#### Download and Install
1. Visit [MySQL Downloads](https://dev.mysql.com/downloads/installer/)
2. Download MySQL Installer
3. Choose "Developer Default" setup
4. Follow configuration wizard

## 🔧 Additional Tools

### Git Version Control

#### Download and Install
1. Visit [Git for Windows](https://git-scm.com/download/win)
2. Download and run installer
3. Use default settings or customize as needed

#### Configuration
```powershell
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### Postman (API Testing)

#### Download and Install
1. Visit [Postman Downloads](https://www.postman.com/downloads/)
2. Download Windows version
3. Install and create account

### Docker (Advanced)

#### Install Docker Desktop
1. Visit [Docker Desktop](https://www.docker.com/products/docker-desktop)
2. Download Windows version
3. Install and enable WSL 2 integration

## 📝 IDE Configuration

### IntelliJ IDEA Settings

#### Code Style
1. File → Settings → Editor → Code Style → Java
2. Set indentation to 4 spaces
3. Configure import organization
4. Set line length to 120 characters

#### Compiler Settings
1. File → Settings → Build → Compiler
2. Enable "Build project automatically"
3. Set compiler heap size to 1024 MB

#### Useful Shortcuts
- `Ctrl + Shift + F10`: Run current file
- `Ctrl + Shift + F9`: Debug current file
- `Alt + Enter`: Quick fix suggestions
- `Ctrl + Shift + A`: Find action
- `Ctrl + /`: Comment/uncomment line

### Eclipse Configuration

#### Workspace Setup
1. Window → Preferences → Java → Installed JREs
2. Add your JDK installation
3. Set as default

#### Code Formatting
1. Window → Preferences → Java → Code Style → Formatter
2. Import or create formatting rules
3. Enable "Format source code" on save

## 🧪 Testing Your Setup

### Create a Test Project

#### Using IntelliJ IDEA
1. File → New → Project
2. Choose "Java" and select your JDK
3. Create a new class with main method
4. Run the program

#### Sample Test Code
```java
public class SetupTest {
    public static void main(String[] args) {
        System.out.println("Java setup successful!");
        System.out.println("Java version: " + 
            System.getProperty("java.version"));
        System.out.println("Java home: " + 
            System.getProperty("java.home"));
    }
}
```

### Create Maven Project
```powershell
mvn archetype:generate -DgroupId=com.example -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

## 🔍 Troubleshooting

### Common Issues

#### Java Not Found
- Verify JAVA_HOME is set correctly
- Check Path variable includes JDK bin directory
- Restart command prompt/IDE

#### IDE Won't Start
- Check if JDK is properly installed
- Verify IDE's JDK configuration
- Increase IDE memory allocation

#### Maven Dependencies Won't Download
- Check internet connection
- Clear Maven cache: `mvn clean`
- Update Maven settings.xml

### Getting Help
- Check IDE logs for error messages
- Search Stack Overflow for specific errors
- Join Java community forums
- Use IDE's built-in help documentation

## ✅ Setup Verification Checklist

- [ ] JDK installed and JAVA_HOME set
- [ ] IDE installed and configured
- [ ] Maven/Gradle working
- [ ] Database installed (PostgreSQL/MySQL)
- [ ] Git configured
- [ ] Sample project runs successfully
- [ ] All environment variables set
- [ ] Plugins/extensions installed

## 🎯 Next Steps

1. **Complete the setup verification**
2. **Create your first Java project**
3. **Explore IDE features and shortcuts**
4. **Set up version control for your projects**
5. **Start with Phase 1 of the learning path**

Congratulations! Your Java development environment is ready. Happy coding! 🚀
