# README 🤖

## 🚀 How to Build the App

To run the application, you need to provide a TMDB API key. Add the following property to your local.properties file:

```properties
TMDB_API_KEY=your_api_key
```

## 🏗 General Architecture

The project follows the MVVM (Model-View-ViewModel) architecture, ensuring a clear separation of concerns:

- Presentation Layer: Uses Jetpack Compose to build a declarative UI.
- Domain Layer: Contains business logic encapsulated within use cases.
- Data Layer: Manages API calls and data persistence.

ViewModels expose state to UI components, making it easier to manage and observe updates.

## 📦 Libraries Used

This project leverages modern Android development libraries:

- Jetpack Compose → For building UI declaratively.
- Koin → For dependency injection.
- Compose Destinations → For type-safe navigation in Compose.
- Ktor → For network communication.
- Coil → For efficient image loading.

Each library is chosen to ensure scalability, maintainability, and a smooth developer experience.
