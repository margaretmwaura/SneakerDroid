package com.android.sneakerdroid.Model

import android.os.Parcelable
import com.android.sneakerdroid.Model.DataApps
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProbesDatum (var data: DataApps,
                        var loggedTime: String,
                        var probe: Int) : Parcelable

