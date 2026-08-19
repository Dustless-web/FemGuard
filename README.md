# FemGuard: Your Personal Safety Companion 🛡️👯‍♀️

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![GitHub stars](https://img.shields.io/github/stars/Dustless-web/FemGuard.svg)](https://github.com/Dustless-web/FemGuard/stargazers)

**FemGuard** is a sophisticated personal safety application designed to empower individuals and provide peace of mind in uncertain situations. Built with modern Android technologies, it combines intuitive UI with high-stakes functionality to ensure help is always just one tap away.

---

## 🌟 Key Features

### 🚨 One-Tap SOS System
*   **Instant Dispatch:** Send emergency SMS messages to multiple pre-configured trusted contacts simultaneously.
*   **Precision Tracking:** Automatically attaches your real-time GPS location via Google Maps link.
*   **Vital Stats:** Includes critical user information (Name, Blood Group) in the SOS message for first responders.
*   **Multi-Sensory Alert:** Triggers a high-decibel police siren and tactical vibration to deter threats.

### 🎭 Discreet Fake Call
*   **Social Escape:** Simulate an incoming phone call with a realistic ringtone to gracefully exit uncomfortable or potentially dangerous social situations.
*   **One-Touch Interface:** Quick-access "Answer" and "Decline" UI that stops the simulation instantly.

### 📍 Interactive Safety Map
*   **Real-time Visualization:** Built-in WebView integration using **Leaflet** and **OpenStreetMap** to visualize your exact surroundings without heavy resource consumption.
*   **Local Awareness:** Identify your position instantly with custom markers and interactive popups.

### ⚙️ Customizable Security
*   **Emergency Contact Management:** Add, remove, and manage your circle of trust through a dedicated fragment.
*   **Privacy-First:** User data, contact lists, and SOS preferences are stored locally using encrypted SharedPreferences.
*   **Configurable Alerts:** Toggle sirens, vibrations, and custom SOS messages based on your personal preference.

---

## 🛠️ Tech Stack & Architecture

*   **Language:** Java (Android SDK)
*   **UI Components:** Material Design 3, BottomNavigationView, Custom Fragments.
*   **Location Services:** Google Play Services (`FusedLocationProviderClient`) for high-accuracy positioning.
*   **Map Engine:** JavaScript Leaflet API rendered via Android WebView.
*   **Storage:** JSON-serialized Local Storage (SharedPreferences).
*   **Hardware Integration:** SMS Manager API, Vibrator Service, MediaPlayer for high-frequency audio alerts.

---

## 🚀 Getting Started

### Prerequisites
*   Android Studio Ladybug (or newer)
*   Android Device/Emulator running API 26 (Oreo) or higher
*   Active SIM card (for SMS features) and GPS enabled

### Installation
1.  Clone the repository:
    ```bash
    git clone https://github.com/Dustless-web/FemGuard.git
    ```
2.  Open the project in Android Studio.
3.  Sync Gradle and run the app on your device.

---

## 🛡️ Permissions Required
To provide full protection, FemGuard requires:
*   `ACCESS_FINE_LOCATION`: For precise SOS coordinates.
*   `SEND_SMS`: To notify your trusted contacts.
*   `VIBRATE`: For tactical feedback.
*   `INTERNET`: To load the interactive safety maps.

---

## 🤝 Contributing
Contributions make the open-source community an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

## 📬 Contact
Project Link: [https://github.com/Dustless-web/FemGuard](https://github.com/Dustless-web/FemGuard)

---
*Created with ❤️ for a safer world.*
