package com.android.sneakerdroid.Model

import android.os.Parcelable
import com.android.sneakerdroid.Model.ProbesDatum
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class UploadRequest (var appVersion: Int,
                     var participantId: String,
                     var probesData: List<ProbesDatum>) : Parcelable

