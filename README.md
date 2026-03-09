# Mandiri News App 📱

A dynamic and responsive Android news application built as the Final Task for the **Bank Mandiri - Mobile Apps Developer Virtual Internship Program** (via Rakamin Academy). This app consumes the REST API from [NewsAPI](https://newsapi.org/) to display real-time news articles.

## 🚀 Features

* **Top Headlines:** Displays trending and breaking news using a horizontally scrolling list.
* **All News (Endless Scrolling):** Displays a comprehensive list of news articles featuring seamless infinite/endless scrolling to load older news continuously.
* **Modern UI:** Clean and intuitive user interface utilizing Material Design components.
* **Image Caching:** Asynchronous image loading and caching for smooth scrolling performance.
* **Date Formatting:** Transforms standard ISO 8601 API date strings into a clean, human-readable format.

## 🛠️ Tech Stack & Architecture

This project is built with modern Android development standards:

* **Language:** Kotlin
* **Architecture:** MVVM (Model-View-ViewModel) for clean separation of concerns.
* **Networking:** [Retrofit2](https://square.github.io/retrofit/) & [OkHttp3](https://square.github.io/okhttp/) for efficient REST API consumption.
* **Asynchronous Programming:** Kotlin Coroutines & Flow.
* **Pagination:** Android Jetpack [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for handling the endless scroll logic automatically.
* **Image Loading:** [Glide](https://github.com/bumptech/glide) for fast and optimized image rendering.
* **UI & Views:** ViewBinding, `RecyclerView`, and `MaterialCardView`.

## ⚙️ Setup and Installation

To run this project locally, you need to configure the API key securely.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/yourusername/Mandiri-News-App.git](https://github.com/yourusername/Mandiri-News-App.git)
    ```
2.  **Get an API Key:**
    * Go to [NewsAPI.org](https://newsapi.org/) and register for a free account[cite: 31, 32].
    * Copy your generated API Key[cite: 64].
3.  **Configure `local.properties`:**
    * Open the project in Android Studio[cite: 25, 29].
    * Locate the `local.properties` file in the root directory of the project.
    * Add your API key inside the file like this:
        ```properties
        NEWS_API_KEY="YOUR_API_KEY_HERE"
        ```
    *(Note: The `local.properties` file is git-ignored, ensuring your API key remains secure and is not pushed to the public repository).*
4.  **Build and Run:**
    * Sync the project with Gradle files.
    * Run the application on an emulator or a physical device.

## 📡 API Reference

This app relies on the **NewsAPI** endpoints:
* `/v2/top-headlines` : Used to fetch the Top Headlines.
* `/v2/everything` : Used to fetch the All News list with pagination parameters (`page` and `pageSize`).
