package com.android.sneakerdroid.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import com.android.sneakerdroid.presenterpackage.MyWorker
import com.android.sneakerdroid.R
import com.android.sneakerdroid.presenterpackage.AppsData
import com.google.gson.Gson
import java.util.concurrent.TimeUnit

class Recording : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)

        //Getting all the apps
        val appsData = AppsData()
        val apps = appsData.getAppData(this)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(apps)
        editor.putString("Apss",json)
        editor.commit()


        fun createConstraints() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        fun createWorkRequest() = PeriodicWorkRequestBuilder<MyWorker>(30, TimeUnit.MINUTES)  // setting period to 12 hours
                       .setConstraints(createConstraints())
                    .build()

        val work = createWorkRequest()
        WorkManager.getInstance().enqueueUniquePeriodicWork("Smart work", ExistingPeriodicWorkPolicy.KEEP, work)

        WorkManager.getInstance().getWorkInfoByIdLiveData(work.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED)
                {
                    workInfo.outputData
                }
            })


//
//        WorkManager.getInstance().enqueue(
//            OneTimeWorkRequestBuilder<MyWorker>().build()
//        )
    }
}
