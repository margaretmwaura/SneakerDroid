package com.android.sneakerdroid


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.sneakerdroid.databinding.ActivityMainBinding
import kotlinx.android.synthetic.*
import android.content.pm.PackageManager
import android.R.attr.versionName
import android.content.pm.PackageInfo
import android.R.attr.versionCode
import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity()
{
    var versionCode : Int  = 0
    var versionName : String = " "

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val ccp = binding.ccp
        ccp.registerCarrierNumberEditText(binding.phoneNumber)


        //Getting all the apps
        val appsData = AppsData()
        val apps = appsData.getAppData(this)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(apps)
        editor.putString("Apss",json)
        editor.commit()

        binding.button.setOnClickListener {

            try {
                val pInfo = getPackageManager().getPackageInfo(packageName, 0)
                versionName = pInfo.versionName
                versionCode = pInfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            val fname = binding.firstname.text.toString()
            val lname = binding.lastnamme.text.toString()
            val phonenumber = ccp.fullNumberWithPlus
            val deviceModel = binding.deviceModel.text.toString()
            val deviceType = binding.deviceType.text.toString()
            val fcmKey = 579988450106

            val deviceDetails = DeviceDetails(deviceModel,deviceType)
            val participant = Participant(fname,lname,phonenumber,getString(R.string.project_code),versionCode,fcmKey,deviceDetails)

            viewModel.registerUser(participant)

        }


        viewModel.response.observe(this, Observer {
            it.let {
                val accestoken = it.accessToken
                val id = it._0participantDetails.id
                Log.d("AccessToken","This is the access token ${accestoken}")



            }
        })

    }
}
