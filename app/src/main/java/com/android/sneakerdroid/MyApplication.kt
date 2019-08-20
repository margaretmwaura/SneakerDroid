package com.android.sneakerdroid

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

class MyApplicationApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        val factory: SampleWorkerFactory = DaggerSampleComponent.create().factory()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())

    }

}