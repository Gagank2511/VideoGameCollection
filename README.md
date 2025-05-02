# Video Game Collection Application

A Java console application to manage video game collections.

## Features

- Game library management with inheritance hierarchy
- Progress tracking for different game types
- User profiles
- Search functionality

## Setup Instructions

### Using the Provided Batch File

1. Make sure you have Java installed on your computer
2. Double-click the `run.bat` file to run the application
3. To run the tests, double-click the `test.bat` file

### Manual Setup in an IDE

1. Create a new Java project in your IDE
2. Add the project files to your project
3. Run the `VideoGameCollectionApp` class as the main class

## Project Structure

- `AbstractGame.java` - Base class for all game types
- `SinglePlayer.java` - Class for single-player games
- `Multiplayer.java` - Class for multiplayer games
- `GameLibrary.java` - Manages the collection of games
- `UserProfile.java` - Manages user profile information
- `DataManager.java` - Handles data persistence
- `Playable.java` - Interface for tracking game progress
- `Main.java` - Console-based user interface
- `VideoGameCollectionApp.java` - Main application launcher
- `VideoGameCollectionTest.java` - Unit tests for the application
