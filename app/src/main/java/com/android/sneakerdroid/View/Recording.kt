package com.android.sneakerdroid.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import com.android.sneakerdroid.presenterpackage.MyWorker
import com.android.sneakerdroid.R
import java.util.concurrent.TimeUnit

class Recording : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)

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

                }
            })
    }
}
