package com.android.sneakerdroid

import android.os.Parcelable
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
    var fcmKey: Int,
    var deviceDetails: DeviceDetails) : Parcelable


