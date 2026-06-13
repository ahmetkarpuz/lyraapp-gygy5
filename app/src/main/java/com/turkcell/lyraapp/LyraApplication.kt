package com.turkcell.lyraapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt'in bagimlilk grafini baslattigi uygulama giris noktasi.
 *
 * `@HiltAndroidApp` annotasyonu, derleme zamaninda uygulama duzeyindeki bileseni uretir;
 * bu sinif [AndroidManifest] icinde `android:name` ile tanimlanmadan Hilt calismaz.
 */
@HiltAndroidApp
class LyraApplication : Application()
