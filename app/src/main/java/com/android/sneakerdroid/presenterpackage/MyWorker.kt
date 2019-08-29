package com.android.sneakerdroid.presenterpackage

import android.content.Context
import android.util.Log
import androidx.work.*
import com.android.sneakerdroid.Model.Constants
import com.android.sneakerdroid.Model.DataApps
import com.android.sneakerdroid.Model.ProbesDatum
import com.android.sneakerdroid.Model.UploadRequest
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject
import javax.inject.Provider
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.io.IOException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        Log.d("Worker","Worker started")
        var outputData : Data? = null
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
        val probesDatumList  = ArrayList<ProbesDatum>()
        val sharedPreference =  appContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val time = sharedPreference.getString("Apss"," ")
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        val id = sharedPreference.getString("ParticipantId"," ")
        val accessToken = sharedPreference.getString("AccessToken"," ")

        //Getting of the values
        val apps =  gson.fromJson<ArrayList<String>>(time, type)
        val second = appsData.getAppData(appContext)
        val third = appsData.installedApps(appContext)

        val installed = appsData.checkForInstalledApp(apps,second)
        val uninstalled = appsData.checkForUnistalledApp(second,apps)

        for(installeNew in installed)
        {
            val system = appsData.checkIfAppIsInstalledApp(third,installeNew)
            val data = DataApps(installeNew, system, true)

            val date = Date()
            val dateInstalled = formatter.format(date)

            val probesDatum = ProbesDatum(data, dateInstalled, 2)
            probesDatumList.add(probesDatum)


        }

        for(uninstalled in uninstalled)
        {
            val system = appsData.checkIfAppIsInstalledApp(third,uninstalled)
            val data = DataApps(uninstalled, system, false)

            val dateUnistalled = Date()
            val date = formatter.format(dateUnistalled)
            val probesDatum = ProbesDatum(data, date, 2)
            probesDatumList.add(probesDatum)

        }

        var jsonProbesDatumDetails = gson.toJson(probesDatumList)

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response
            {
                val request = chain.request().newBuilder().addHeader("Authorization Token ", accessToken).build()
                return chain.proceed(request)
            }
        })
        val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .addCallAdapterFactory(CoroutineCallAdapterFactory())
                     .client(httpClient.build())
                     .build()
                    .create(api_service::class.java)

        val response = retrofit.upLoadRequest(Constants.PROBE,id,jsonProbesDatumDetails)

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

