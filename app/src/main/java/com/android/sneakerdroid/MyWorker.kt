package com.android.sneakerdroid

import android.content.ContentValues
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Provider


class MyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val appsData: AppsData
   ) : Worker(appContext, params) {

    override fun doWork(): Result {

        val sharedPreference =  appContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val time = sharedPreference.getString("EventTime"," ")
        Log.d("SavedData","This is the saved data pal ${time}")

        appsData.getAppData(appContext)
        appsData.installedApps(appContext)
        return Result.success()
    }


    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}

class SampleWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry =
            workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = foundEntry?.value
            ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
        return factoryProvider.get().create(appContext, workerParameters)
    }
}

