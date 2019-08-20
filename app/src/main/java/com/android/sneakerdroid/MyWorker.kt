package com.android.sneakerdroid

import android.content.Context
import androidx.work.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Provider
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class MyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val appsData: AppsData
   ) : Worker(appContext, params)
{

    override fun doWork(): Result
    {
        var outputData : Data? = null
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
        val probesDatumList  = ArrayList<ProbesDatum>()
        val sharedPreference =  appContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val time = sharedPreference.getString("Apss"," ")
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {
        }.type
        val apps =  gson.fromJson<ArrayList<String>>(time, type)
        val second = appsData.getAppData(appContext)
        val third = appsData.installedApps(appContext)

        val installed = appsData.checkForInstalledApp(apps,second)
        val uninstalled = appsData.checkForUnistalledApp(second,apps)

        for(installeNew in installed)
        {
            val system = appsData.checkIfAppIsInstalledApp(third,installeNew)
            val data = DataApps(installeNew,system,true)

            val date = Date()
            val dateInstalled = formatter.format(date)

            val probesDatum = ProbesDatum(data,dateInstalled,2)
            probesDatumList.add(probesDatum)

        }

        for(uninstalled in uninstalled)
        {
            val system = appsData.checkIfAppIsInstalledApp(third,uninstalled)
            val data = DataApps(uninstalled,system,false)

            val dateUnistalled = Date()
            val date = formatter.format(dateUnistalled)
            val probesDatum = ProbesDatum(data,date,2)
            probesDatumList.add(probesDatum)

        }

        val uploadRequest = UploadRequest(500,"ID",probesDatumList)

        val response = RetrofitFactory.makeRetrofitService().upLoadRequest(uploadRequest)


        if(response.isSuccessful)
        {
            val sampleResponse = response.body()
            outputData = createOutputData(sampleResponse!!.message)
        }
        else
        {
            outputData = createOutputData("Fail")
        }

        var editor = sharedPreference.edit()
        val gsonSave = Gson()
        val json = gsonSave.toJson(apps)
         editor.putString("Apss",json)
         editor.commit()



        return Result.success(outputData!!)
    }


    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}

fun createOutputData(firstData: String): Data {
    return Data.Builder()
        .putString("Message" , firstData)
        .build()
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

