<div align="center">

# 🛡️ FemGuard

### *Your Personal Security Companion & Emergency SOS Dispatcher*

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Language-Java_11-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-blue?style=for-the-badge)](https://developer.android.com/about/versions/nougat)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-36%20(Android%2015)-brightgreen?style=for-the-badge)](https://developer.android.com/about/versions/15)
[![License](https://img.shields.io/badge/License-MIT-orange.svg?style=for-the-badge)](LICENSE)

<p align="center">
  <b>FemGuard</b> is a modern, lightweight, and offline-resilient Android safety application engineered to provide immediate assistance and deterrence during critical emergencies.
</p>

[Key Features](#-key-features) •
[Architecture](#-architecture--tech-stack) •
[Installation](#-installation--setup) •
[Permissions & Privacy](#-permissions--privacy) •
[Usage Guide](#-usage--how-it-works)

---

</div>

## 📖 Overview

In high-stakes emergency situations, every second counts. **FemGuard** bridges the gap between distress and assistance by delivering a robust multi-tier personal safety solution:

1. **Instant Location & Profile Dispatch**: Sends exact GPS coordinates alongside vital medical & identity information to trusted contacts with a single gesture.
2. **Discreet Exit Strategies**: Offers a simulated fake phone call interface to help users seamlessly exit uncomfortable or suspicious situations.
3. **Local Navigation & Awareness**: Features a lightweight interactive live map showing current real-time surroundings.
4. **Active Deterrence**: Option to emit high-decibel audible sirens and tactical vibrations to deter potential threats.

Designed with a **privacy-first architecture**, all contact lists and personal data reside **100% locally** on the user's device.

---

## 🌟 Key Features

### 🚨 Emergency SOS System
* **Long-Press Activation**: Prevents accidental triggers via a deliberate hold gesture on the main SOS button.
* **Precision GPS Location**: Utilizes Google Play Services `FusedLocationProviderClient` with `PRIORITY_HIGH_ACCURACY` to acquire pinpoint location.
* **Automated SMS Dispatch**: Concurrently transmits SMS alerts to all configured trusted contacts via `SmsManager`.
* **Medical Profile Integration**: Automatically appends the user's name, blood type, and custom distress message to the SMS alert alongside a direct Google Maps coordinates URL.
* **Acoustic & Haptic Alarm**: Triggers an loud police siren sound effect (`police_siren.mp3`) and device vibration to alert bystanders and frighten off attackers.

### 🎭 Simulated Fake Call
* **Authentic Ringtone Engine**: Accesses device system ringtones via `RingtoneManager` for maximum realism.
* **Realistic Call UI**: Full-screen incoming call interface complete with "Answer" and "Decline" controls.
* **Instant Stop**: Immediately silences audio upon user interaction or screen termination.

### 🗺️ Live Safety Map
* **Lightweight Embedded Map**: Uses an in-app `WebView` integrated with **Leaflet.js** and **OpenStreetMap**.
* **Zero Heavy Dependencies**: Avoids heavy map SDK overhead while maintaining fast load times and interactive panning/zooming.
* **Self-Location Marker**: Automatically drops a precise location pin centered on the user's active coordinates.

### 📇 Trusted Contact Circle
* **Manage Emergency Network**: Add and remove trusted contacts effortlessly.
* **Dynamic Card Interface**: Clean Material CardView list displaying contact names and phone numbers.
* **Local Data Persistence**: Contacts are serialized to JSON and stored securely inside private `SharedPreferences`.

### ⚙️ Customizable Settings & Reliability
* **Emergency Profile Configuration**: Save personal details (Name, Blood Group) and customize default distress messages.
* **Power Optimization Bypass**: One-click prompt for `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` ensuring the app is never killed by OEM aggressive power-saving policies during emergencies.
* **Toggle Controls**: Enable or disable police sirens and vibration modes independently.

---

## 🛠️ Architecture & Tech Stack

```text
┌──────────────────────────────────────────────────────────────────┐
│                           FemGuard UI                            │
├─────────────────┬──────────────────┬─────────────────────────────┤
│  HomeFragment   │ ContactFragment  │      SettingsFragment       │
├─────────────────┼──────────────────┼─────────────────────────────┤
│ FakeCallAct.    │   MapActivity    │       MainActivity          │
└────────┬────────┴────────┬─────────┴──────────────┬──────────────┘
         │                 │                        │
┌────────▼────────┐┌───────▼─────────┐┌─────────────▼──────────────┐
│ RingtoneManager ││ Leaflet / OSM   ││ Google Location Services  │
│ Audio & Haptics ││ WebChromeClient ││ (FusedLocationProvider)   │
└─────────────────┘└─────────────────┘└─────────────┬──────────────┘
                                                    │
                                      ┌─────────────▼──────────────┐
                                      │  SmsManager & Local Storage│
                                      │  (JSON / SharedPreferences)│
                                      └────────────────────────────┘
```

* **Core Framework**: Native Android SDK (Java 11)
* **Design System**: Material Design 3, `BottomNavigationView`, Custom Fragment Transitions
* **Location API**: `com.google.android.gms:play-services-location:21.0.1`
* **Map Engine**: Leaflet.js v1.7.1 + OpenStreetMap via Android `WebView`
* **Hardware & System APIs**:
  * `SmsManager` (SMS Transmission)
  * `Vibrator` / `VibrationEffect` (Tactical Haptics)
  * `MediaPlayer` (Audio Sirens)
  * `RingtoneManager` (Fake Calls)

---

## 📂 Project Structure

```text
FemGuard-main/
├── app/
│   ├── build.gradle.kts                # App-level Gradle configurations & dependencies
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml      # App permissions, activities, and metadata
│           ├── java/com/example/guardian/
│           │   ├── MainActivity.java    # Primary host activity & SOS logic coordinator
│           │   ├── HomeFragment.java    # SOS button & action shortcuts home screen
│           │   ├── ContactFragment.java # Emergency contact manager UI
│           │   ├── SettingsFragment.java# Profile, message, & hardware preferences
│           │   ├── FakeCallActivity.java# Simulated incoming call screen
│           │   ├── MapActivity.java     # Embedded OpenStreetMap location view
│           │   └── ContactActivity.java # Legacy contact management screen
│           └── res/
│               ├── drawable/            # UI icons, custom shapes, & SOS gradients
│               ├── layout/              # XML layout definitions for screens & fragments
│               ├── raw/
│               │   └── police_siren.mp3 # High-decibel emergency siren audio
│               └── values/              # Color palettes, string resources, & themes
├── build.gradle.kts                     # Root build file
├── gradle/                              # Gradle wrapper & version catalog (libs.versions.toml)
└── settings.gradle.kts                  # Repository & module inclusions
```

---

## 🔒 Permissions & Privacy

### Required Permissions
| Permission | Purpose |
| :--- | :--- |
| `android.permission.SEND_SMS` | Transmits emergency SMS alerts with location links to contacts. |
| `android.permission.ACCESS_FINE_LOCATION` | Obtains precise GPS coordinates for emergency alerts & map view. |
| `android.permission.ACCESS_COARSE_LOCATION` | Fallback approximate location acquisition. |
| `android.permission.INTERNET` | Loads map tiles from OpenStreetMap within the interactive WebView. |
| `android.permission.VIBRATE` | Provides haptic feedback during SOS activation. |
| `android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` | Guarantees background survival during low-power device states. |

### 🛡️ Privacy Guarantee
* **Zero Remote Data Collection**: FemGuard does **not** track, upload, or sell your personal data or location history.
* **100% On-Device Storage**: All personal information, trusted numbers, and preference settings stay exclusively on your local device within app-private storage (`SharedPreferences`).

---

## 🚀 Installation & Setup

### Prerequisites
* **Android Studio**: Ladybug (2024.2.1) or newer recommended.
* **JDK**: Java Development Kit 11 or higher.
* **Test Device**: Android phone with API 24+ (Android 7.0+), active SIM card (for SMS features), and enabled GPS/Location services.

### Building from Source

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Dustless-web/FemGuard.git
   cd FemGuard
   ```

2. **Open in Android Studio**:
   * Select **File > Open** and choose the `FemGuard-main` folder.

3. **Sync Gradle**:
   * Allow Android Studio to download dependencies listed in `gradle/libs.versions.toml` and `app/build.gradle.kts`.

4. **Run the Application**:
   * Connect an Android device via USB/Wireless Debugging.
   * Click **Run (Shift + F10)**.

---

## 📱 Usage & How It Works

1. **Initial Setup**:
   * Open the **Settings** tab.
   * Fill in your **Name** and **Blood Type**.
   * Customize your emergency message if desired.
   * Tap **Ignore Battery Optimizations** to ensure reliable operation.

2. **Adding Emergency Contacts**:
   * Navigate to the **Contacts** tab.
   * Enter the name and phone number of your trusted individual.
   * Click **Save Contact**.

3. **Triggering SOS Emergency**:
   * Go to the **Home** tab.
   * **Press and Hold** the red glowing **SOS** button.
   * The app will acquire your current GPS coordinates, play a police siren (if enabled), vibrate, and automatically dispatch SMS messages with a Google Maps link to all saved contacts.

4. **Using Fake Call**:
   * Tap **Fake Call** on the home screen.
   * A simulated call screen will appear with incoming ringtone audio.
   * Tap **Answer** or **Decline** to exit.

5. **Viewing Live Map**:
   * Tap **Live Map** on the home screen to view your real-time position on an OpenStreetMap interface.

---

## 🤝 Contributing

Contributions, bug reports, and feature requests are welcome!

1. **Fork** the repository.
2. Create your Feature Branch: `git checkout -b feature/NewFeature`
3. Commit your changes: `git commit -m 'Add NewFeature'`
4. Push to the Branch: `git push origin feature/NewFeature`
5. Open a **Pull Request**.

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

Made with ❤️ for safety and peace of mind.

</div>
