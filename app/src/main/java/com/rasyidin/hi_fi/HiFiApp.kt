package com.rasyidin.hi_fi

import android.app.Application
import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiFiApp : Application() {

    companion object {
        val networkFlipperPlugin = NetworkFlipperPlugin()
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            SoLoader.init(this, false)

            val flipperClient = AndroidFlipperClient.getInstance(this)
            flipperClient.apply {
                addPlugin(InspectorFlipperPlugin(this@HiFiApp, DescriptorMapping.withDefaults()))
                addPlugin(DatabasesFlipperPlugin(this@HiFiApp))
                addPlugin(networkFlipperPlugin)
            }
            flipperClient.start()
        }
    }
}