Task-Manager-Application-Screen-Recording -> 
[Google Drive Link](https://drive.google.com/file/d/1FvdtV__eJBOlOs3urfH0cw-lt_LkSQj6/view?usp=sharing)

Setup and Run Instructions
Prerequisites
Android Studio (latest version)
Java 8 or higher
Android SDK with the necessary tools (Build Tools, Emulator, etc.)

Steps to Set Up the Project

Clone the Repository: Clone the project from your GitHub repository:
(“git clone https://github.com/akxshatjain05/Task-Manager-Application.git”)

Import the Project: Open Android Studio and select. Open an existing Android Studio project. Navigate to the directory where you cloned the repository and open it.

Checkout to master branch:
(“git checkout master”)
-> : delete any commits showing then checkout to master.

Install Dependencies: The project uses Gradle to manage dependencies. Once the project is opened in Android Studio, Gradle will automatically sync and download the necessary dependencies. If it doesn’t, go to the File > Sync Project with Gradle Files option.


Run the App: Connect an Android device to your computer or use an Android Emulator. Click on the green play button in Android Studio to build and run the app on the connected device or emulator.



Details of Third-Party Library Integration

1. Room Database
Version: 2.6.1
Purpose: Room is used for local data storage to save tasks and their statuses. It provides an abstraction layer over SQLite to allow more robust database access while leveraging SQLite’s full power.
implementation "androidx.room:room-runtime:2.6.1"
annotationProcessor "androidx.room:room-compiler:2.6.1"

2. RecyclerView
Version: 1.3.2
Purpose: RecyclerView displays a large set of data in a scrollable list reusing the UI without creating it again and again. It’s more efficient than ListView and supports better performance and layout flexibility.
implementation "androidx.recyclerview:recyclerview:1.3.2"

3. Firebase
Version: 33.10.0
Implementation platform("com.google.firebase:firebase-bom:33.10.0")
implementation "com.google.firebase:firebase-analytics"
implementation "com.google.firebase:firebase-crashlytics"
implementation “com.google.firebase:firebase-perf:21.0.5”



Explanations of Your Design Decisions
1. UI/UX Design:
The app follows Material Design principles to provide a clean and user-friendly interface.
Task View: Tasks are displayed in a simple list with checkboxes to mark them as completed. When a task is completed, its name is crossed out for clarity.
Task Input: A text input field allows users to add new tasks, and the tasks are stored in a local database.
Task Editing: Tapping a task's name allows the user to edit it. This gives flexibility to modify tasks after creation.
2. Error Handling:
A validation mechanism ensures that users cannot add empty tasks or tasks with only spaces. A toast is displayed when this happens, preventing invalid entries.
Task editing also ensures that only valid task names are saved.


-> Active Users
![Active Users](https://github.com/akxshatjain05/Task-Manager-Application/blob/main/Active%20Users.png)

-> Realtime Analytics
![Realtime Analytics](https://github.com/akxshatjain05/Task-Manager-Application/blob/main/Realtime%20Analytics.png)

-> Crashalytics
![Crashalytics](https://github.com/akxshatjain05/Task-Manager-Application/blob/main/Crashalytics.png)

->Network Performance
![Network Performance](https://github.com/akxshatjain05/Task-Manager-Application/blob/main/Network%20Performance.png)






