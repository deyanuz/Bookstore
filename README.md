
# 📚 Bookstore Application

**A seamless Android book shopping experience — built with Java & Firebase.**

This project emphasizes clean architecture, popular design patterns, and comprehensive testing methodologies.

---

## 👥 Team

This is a collaborative team project developed by:

| # | Name | Roll |
|---|------|------|
| 1 | **Sumaiya Khan** | 2007031 |
| 2 | **Md kawsar Mahmud Khan Zunayed** | 2007046 |
| 3 | **Avishek Debnath** | 2007056 |

---

## 🌟 Features

- **🔐 User Authentication** — Secure sign-in and sign-up using Firebase Auth.
- **📖 Book Catalog** — Browse a wide range of books fetched dynamically from Firebase Firestore / Realtime Database.
- **🛒 Shopping Cart** — Add books to the cart and proceed to checkout smoothly.
- **📦 Order History** — View past orders and purchase history.
- **🗂 Category Browsing** — Filter books by categories.
- **🖼 Image Loading** — Efficient image fetching and caching using Glide.

---

## 🏗 Architecture & Design Patterns

This project adheres to modern software engineering practices, ensuring maintainability and scalability. The following design patterns are actively utilized:

| Pattern | Usage |
|---|---|
| **Singleton** | Database instances, API clients, and factories (e.g., `FirebaseAuthFactory`) — ensures only one instance is created throughout the app lifecycle. |
| **Observer** | Observes changes in data states (e.g., shopping cart updates) and notifies the UI to refresh automatically without tight coupling. |
| **Adapter** | Bridges UI components (`RecyclerView`) and the underlying data model (e.g., `MyAdapter`, `CartItemAdapter`). |
| **Factory** | Handles object creation (e.g., authentication instances) without specifying the exact class of the object to be created. |

---

## 🧪 Testing

Quality assurance is a priority in this project. Multiple testing layers are integrated:

- **Unit Testing** — `JUnit4` and `Mockito` test business logic and ViewModel components in isolation. `Robolectric` enables fast Android framework testing on the JVM.
- **UI (Instrumented) Testing** — Automated UI tests using **Espresso** simulate user interactions and verify UI behaviors on a real device or emulator.
- **Continuous Integration (CI)** — Configured to automatically run the full test suite on every push or pull request, ensuring no breaking changes are merged into the main branch.

---

## 🛠️ Technology Stack

| Category | Details |
|---|---|
| **Language** | Java |
| **Minimum SDK** | 34 |
| **Target SDK** | 34 |
| **Authentication** | Firebase Authentication |
| **Database** | Cloud Firestore, Firebase Realtime Database |
| **Storage** | Firebase Storage |
| **UI** | Material Design, ConstraintLayout, RecyclerView |
| **Image Loading** | [Glide](https://github.com/bumptech/glide) |
| **Testing** | JUnit4, Mockito, Robolectric, Espresso |

---

## 🎥 Demo Video

Check out the live demonstration of the app in action!

[🔗 Click here to watch the Demo Video on Google Drive](https://drive.google.com/file/d/1YNqau51bEXR_Bp5YqxZDzXmHq13JAiIz/view?usp=drive_link)

> *(Replace this link with your actual Google Drive share link)*

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable version recommended)
- JDK 11 or higher
- A Firebase project with Authentication, Firestore, Realtime Database, and Storage enabled

### Setup

**1. Clone the repository:**

```bash
git clone https://github.com/deyanuz/Bookstore-main.git
cd Bookstore-main
```

**2. Connect Firebase:**

- Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project (or use an existing one).
- Add an Android app with your package name.
- Download the `google-services.json` file and place it in the `app/` directory.

**3. Open in Android Studio:**

- Open Android Studio → **File > Open** → select the project folder.
- Let Gradle sync finish automatically.

**4. Run the app:**

- Connect an Android device or start an emulator (API 34+).
- Click **Run ▶** or press `Shift + F10`.

---

## 🧪 Running Tests

**Unit tests:**

```bash
./gradlew test
```

**Instrumented UI tests (requires a connected device or emulator):**

```bash
./gradlew connectedAndroidTest
```

---

## 📁 Project Structure (Simplified)

```
Bookstore-main/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Application source code
│   │   │   └── res/          # Layouts, drawables, strings
│   │   ├── test/             # Unit tests (JUnit, Mockito, Robolectric)
│   │   └── androidTest/      # Instrumented UI tests (Espresso)
│   └── google-services.json  # Firebase config (add manually)
├── build.gradle
└── README.md
```


## 📄 License

This project is open-source and distributed under the **MIT License**. See [`LICENSE`](LICENSE) for more information.

