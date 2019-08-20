package com.android.sneakerdroid

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log

class AppsData
{

    fun getAppData(context: Context)
    {

        val pm = context.getPackageManager()
//get a list of installed apps.
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (packageInfo in packages) {

            Log.d(TAG, "Installed package :" + packageInfo.packageName)
            Log.d(TAG, "Source dir : " + packageInfo.sourceDir)
//            Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName)!!)

        }

        Log.d("MethodAppsData","The actual method are being called ")

    }


    fun installedApps(context: Context) {
        val packList = context.getPackageManager().getInstalledPackages(0)
        for (i in packList.indices) {
            val packInfo = packList.get(i)
            if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = packInfo.applicationInfo.loadLabel(context.getPackageManager()).toString()
                Log.d("PackageName", "This is the package name of the app " + packInfo.applicationInfo.packageName)
                Log.e("App № " + Integer.toString(i), appName)
            }

        }

        Log.d("SecondMethod","Mthods are actually being called people")
    }
}