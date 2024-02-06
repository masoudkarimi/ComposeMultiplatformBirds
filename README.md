This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

## Technologies

- **[Ktor](https://ktor.io/)**: Ktor is a framework for building asynchronous servers and clients in connected systems using the Kotlin programming language.
- **[Moko MVVM](https://github.com/icerockdev/moko-mvvm)**: Moko MVVM is a Kotlin Multiplatform MVVM library that helps in building cross-platform mobile applications with shared business logic.
- **[Koin](https://insert-koin.io/)**: Koin is a pragmatic lightweight dependency injection framework for Kotlin developers.
- **[Kamel Media](https://github.com/Kamel-Media/Kamel)**: Kamel Media is a media loader for Compose Multiplatform.

![Screenshot 2024-02-02 at 00 37 18](https://github.com/masoudkarimi/ComposeMultiplatformBirds/assets/27435736/45bd692e-0813-43b7-a565-fbfad461fb07)

