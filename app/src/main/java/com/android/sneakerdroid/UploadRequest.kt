package com.android.sneakerdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class UploadRequest (var appVersion: Int,
                     var participantId: String,
                     var probesData: List<ProbesDatum>) : Parcelable

