package com.android.sneakerdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
 data class SampleResponse (var statusCode: Int,
                            var message: String) : Parcelable


