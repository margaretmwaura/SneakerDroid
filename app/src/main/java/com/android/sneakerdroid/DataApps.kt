package com.android.sneakerdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DataApps (var name: String,
                     var isSystem: Boolean,
                     var isInstalled: Boolean) : Parcelable


