package com.android.sneakerdroid.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DeviceDetails (var deviceModel: String,
                          var deviceType: String
                          ):Parcelable


