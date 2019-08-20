package com.android.sneakerdroid.Model

import android.os.Parcelable
import com.android.sneakerdroid.Model.ParticipantDetails
import com.android.sneakerdroid.Model.Probe
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class RegistrationResults (
    var accessToken: String,
    var probes: List<Probe>,
    var _0participantDetails: ParticipantDetails
) : Parcelable
