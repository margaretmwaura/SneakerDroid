package com.android.sneakerdroid.Model

import android.os.Parcelable
import com.android.sneakerdroid.Model.DeviceDetails
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Participant (
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var projectCode: String,
    var appVersion: Int,
    var fcmKey: Long,
    var deviceDetails: DeviceDetails
) : Parcelable


