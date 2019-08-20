package com.android.sneakerdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class Recording : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)

        WorkManager.getInstance().enqueue(
            OneTimeWorkRequestBuilder<MyWorker>().build())
    }
}
