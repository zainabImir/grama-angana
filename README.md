# Grama Angana 🏛️

Grama Angana is a modern, enterprise-grade Android application developed using **Jetpack Compose** and **Clean Architecture** principles to support digital village governance and decentralized community service management. The platform features cloud-synchronized room booking management, local crowdsourced fundraising asset managers, and strict validation layers to optimize administrative workflows.

---

## 🚀 Technical Highlights & Features

### 🗓️ Smart Event Calendar & Hall Booking
- **Double-Booking Prevention:** Implements a strict validation constraint layer between the presentation cache and remote collections to eliminate race conditions.
- **Dynamic Availability Engine:** Utilizes the native `java.time` engine to calculate real-time calendar grids linked to a remote **Firebase Firestore** backend.
- **Bi-Directional State Binding:** Automatically parses and passes exact machine-readable `ISO_LOCAL_DATE` payload parameters (`yyyy-MM-dd`) between screen navigation boundaries to eliminate form input anomalies.

### 🍯 Localized Crowdfunding System (Maintenance Jar)
- **Offline-First Persistence:** Implements a local relational database storage schema using the **Room DB** wrapper over SQLite.
- **Reactive Data Flows:** Tracks real-time community pledge updates using asynchronous cold `Flow` transformations converted into lifecycled `StateFlow` streams.
- **Visual Progress Trackers:** Dynamic UI progress elements designed with **Material Design 3** rendering precise tracking of project targets against aggregate local pledges.

### 🛡️ Architectural & Core Platform Engineering
- **Dependency Injection (DI):** Modularized graph configurations separated strictly across infrastructure segments (`DatabaseModule` vs. `FirebaseModule`) using **Dagger Hilt** to ensure decoupling.
- **Compiler Safety:** Employs **Kotlin Symbol Processing (KSP)** to run static analysis compile-time queries over database schemas.
- **Robust Schema Migrations:** Configured with fallback destructive translation handlers to cleanly manage architectural changes during iterative deployment cycles.
- **Authentication & Core UI:** Secure user management utilizing **Firebase Authentication** alongside a highly responsive, scannable dashboard menu layout built entirely on declarative **Jetpack Compose UI components**.

---

## 🛠️ Tech Stack & Architecture Matrix

- **Language:** 100% Kotlin
- **UI Framework:** Jetpack Compose (Declarative UI)
- **Design Language:** Material Design 3 (M3)
- **Local Database:** Room Persistence Library (SQLite Object Mapping)
- **Cloud Infrastructure:** Firebase Authentication & Firebase Firestore Cloud Database
- **Dependency Injection:** Dagger Hilt (`@Module`, `@Provides`, `@Inject`)
- **Code Generation / Processing:** KSP (Kotlin Symbol Processing)
- **Asynchronous Execution:** Kotlin Coroutines & Reactive Flows (`StateFlow`, `SharingStarted`)
- **Navigation Engine:** Jetpack Navigation Component with Type-Safe Route Arguments

---

## 📂 Production Project Directory Layout

```text
com.example.grama_angana
│
├── data
│   ├── local
│   │   ├── AppDatabase.kt        # Unified single-source DB instance (User & Maintenance tables)
│   │   ├── MaintenanceDao.kt     # Low-level SQL transactional instructions
│   │   └── UserDao.kt            # Authentication metadata queries
│   │
│   ├── model
│   │   └── MaintenanceItem.kt    # Room entity blueprint for infrastructure targets
│   │
│   └── repository
│       └── MaintenanceRepository.kt # Data broker handling database operations
│
├── di
│   ├── DatabaseModule.kt         # Hilt scope providing Room DB dependencies
│   └── FirebaseModule.kt         # Hilt scope providing Cloud Firestore instances
│
├── navigation
│   ├── NavGraph.kt               # Central Navigation Host and route definitions
│   └── Screen.kt                 # Application destination configuration
│
└── ui
    ├── components                # Reusable design components (Cards, cells, loaders)
    └── screens
        ├── booking               # BookingScreen.kt (Strict payload formatting)
        ├── calendar              # EventCalendarScreen.kt & ViewModel (Cloud lookup)
        ├── dashboard             # DashboardScreen.kt (Dynamic Services Grid)
        └── maintainance          # MaintenanceJarScreen.kt & ViewModel (Room engine)

```

---

## ⚙️ Installation & Environments Lifecycle

### 1. Source Configuration

Clone the repository recursively to fetch structural files:

```bash
git clone [https://github.com/zainabImir/grama-angana.git](https://github.com/zainabImir/grama-angana.git)

```

### 2. Connect Your Cloud Environment

1. Initialize a project on the **Firebase Web Console**.
2. Provision and register your Android application using the package identity `com.example.grama_angana`.
3. Fetch your cryptographic token bundle file: `google-services.json`.
4. Drop the target configuration bundle into your root app location directory: `/app/google-services.json`.

### 3. Database Collection Schema Initialization

Enable **Cloud Firestore** inside your console panel and create an index collection matching this structure:

* **Collection Name:** `bookings`
* **Document Payload Interface Mapping:**
```json
{
  "date": "2026-05-22",       // Type: String (ISO format strictly validated)
  "name": "Zain",              // Type: String
  "purpose": "Gathering",      // Type: String
  "timestamp": [Timestamp]     // Type: Firebase Server Timestamp object
}

```



### 4. Build and Compilation Execution

1. Open the source project root tree inside **Android Studio**.
2. Click `Build -> Clean Project` to drop any old pre-compiled KSP caches.
3. Sync your configuration properties via the `Gradle Sync` action panel.
4. Connect your physical debugging target phone or Android Emulator image instance.
5. Tap the **Green Play Button** (`Shift + F10`) to build the binary and run it!

---

## 📈 Learning Outcomes & Core Engineering Competencies

This project showcases a deep dive into real-world Android engineering, focusing on solving production-level problems:

* **State Synchronization:** Solved cross-screen state anomalies by standardizing UI rendering against remote storage constraints.
* **Race Condition Prevention:** Eliminated data collisions (double bookings) via uniform text parsing and proactive interaction locking.
* **Dependency Graph Isolation:** Built modularized compilation pipelines using dependency injection containers to abstract backend details.
* **Offline-First Resilience:** Created data channels using Room DB and Kotlin Flows to keep the UI updating reactively.

---

## 📄 License & Intent

This application is open-sourced as part of an advanced educational track and Androiddevelopment industrial internship.

**Author:** Zainab Imtiyaz👩‍💻

**Repository Access Link:** [grama-angana](https://github.com/zainabImir/grama-angana)

```

```
