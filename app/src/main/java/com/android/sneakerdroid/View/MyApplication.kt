package com.android.sneakerdroid.View

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.android.sneakerdroid.presenterpackage.DaggerSampleComponent


import com.android.sneakerdroid.presenterpackage.SampleWorkerFactory

class MyApplicationApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        val factory: SampleWorkerFactory = DaggerSampleComponent.create()
            .factory()

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())



    }

}