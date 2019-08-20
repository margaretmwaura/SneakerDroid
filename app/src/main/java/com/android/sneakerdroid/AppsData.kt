package com.android.sneakerdroid

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log

class AppsData
{

    var apps = ArrayList<String>()
    var installedApps = ArrayList<String>()
    fun getAppData(context: Context) : ArrayList<String>
    {

        val pm = context.getPackageManager()
//get a list of installed apps.
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (packageInfo in packages) {

            Log.d(TAG, "Installed package :" + packageInfo.packageName)
            apps.add(packageInfo.packageName)

        }

        return apps
    }


    fun installedApps(context: Context) : ArrayList<String>
    {
        val packList = context.getPackageManager().getInstalledPackages(0)
        for (i in packList.indices) {
            val packInfo = packList.get(i)
            if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = packInfo.applicationInfo.loadLabel(context.getPackageManager()).toString()
                Log.d("PackageName", "This is the package name of the app " + packInfo.applicationInfo.packageName)

                installedApps.add(packInfo.applicationInfo.packageName)

            }

        }

        return installedApps
    }

    fun checkForInstalledApp(allApps : List<String> , newApps : List<String> ) : ArrayList<String>
    {
        val additionalDataInListA = ArrayList<String>(allApps)
        val additionalDataInListB = ArrayList<String>(newApps)

        additionalDataInListB.removeAll(additionalDataInListA)

        return additionalDataInListB
    }

    fun checkForUnistalledApp(allApps : List<String> , newApps : List<String> ) : ArrayList<String>
    {
        val additionalDataInListA = ArrayList<String>(allApps)
        val additionalDataInListB = ArrayList<String>(newApps)

        additionalDataInListA.removeAll(additionalDataInListB)

        return additionalDataInListA
    }

    fun checkIfAppIsInstalledApp(installedApps : List<String> , packageName : String ) : Boolean
    {
        if(installedApps.contains(packageName))
        {
            return true
        }

        return false
    }


}