plugins { id("com.android.application") }

android {
    namespace = "info.imedkablavi.shadownexus"
    compileSdk = 35
    defaultConfig {
        applicationId = "info.imedkablavi.shadownexus"
        minSdk = 29
        targetSdk = 35
        versionCode = 3
        versionName = "2.1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
