package com.android.sneakerdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProbesDatum (var data: Data,
                        var loggedTime: String,
                        var probe: String) : Parcelable

