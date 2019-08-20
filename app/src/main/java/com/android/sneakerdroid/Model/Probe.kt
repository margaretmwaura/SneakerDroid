package com.android.sneakerdroid.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Probe (var name: String,
                  var id: Int,
                  var description: String,
                  var dataCollectionType: String,
                  var samplingFrequency: Float) : Parcelable

