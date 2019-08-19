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
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


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
            val fcmKey = Integer.parseInt(getString(R.string.fcm_key))

            val deviceDetails = DeviceDetails(deviceModel,deviceType)
            val participant = Participant(fname,lname,phonenumber,getString(R.string.project_code),versionCode,fcmKey,deviceDetails)

            viewModel.registerUser(participant)

        }


        viewModel.response.observe(this, Observer {
            it.let {
                val accestoken = it.accessToken
                Log.d("AccessToke","This is the access token ${accestoken}")
            }
        })

    }
}
