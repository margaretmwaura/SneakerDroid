package com.android.sneakerdroid.View


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import android.content.pm.PackageManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.android.sneakerdroid.Model.Constants
import com.android.sneakerdroid.Model.DeviceDetails

import com.android.sneakerdroid.R
import com.android.sneakerdroid.databinding.ActivityMainBinding

import com.android.sneakerdroid.presenterpackage.AppsData

import com.android.sneakerdroid.presenterpackage.RegisterViewModel
import com.google.gson.Gson


class MainActivity : AppCompatActivity()
{
    var versionCode : Int  = 0
    var versionName : String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        val ccp = binding.ccp
        ccp.registerCarrierNumberEditText(binding.phoneNumber)

        val viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

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
            val fcmKey = Constants.FCM_KEY

            val deviceDetails = DeviceDetails(deviceModel, deviceType)

            val gson = Gson()
            var jsonDeviceDetails = gson.toJson(deviceDetails)
            Log.d("Device details","This are the device details ${jsonDeviceDetails}")
            jsonDeviceDetails = "{device_model:hghk,device_type:cblhg}"
            Log.d("Values","This are the values to pass firstname : ${fname} ,lastname :  ${lname} ,phonenumber :  ${phonenumber} ,projectId:  ${Constants.project_id} ,versioncode :  ${versionCode} ,fcmkey : ${fcmKey}")
            viewModel.registerUser(fname,lname,phonenumber,Constants.project_id,versionCode,fcmKey,jsonDeviceDetails)

        }


        viewModel.response.observe(this, Observer {
            it.let {

                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                val accestoken = it.accessToken
                val id = it._0participantDetails.id
                Log.d("AccessToken","This is the access token ${accestoken}")
                var editor = sharedPreference.edit()
                editor.putString("ParticipantId",id)
                editor.putString("AccessToken",accestoken)
                editor.commit()

                val intent = Intent(this@MainActivity,Recording::class.java)
                startActivity(intent)


            }
        })



    }
}
