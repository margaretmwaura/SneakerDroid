package com.android.sneakerdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class RegistrationResults (
    var accessToken: String,
    var probes: List<Probe>,
    var _0participantDetails: ParticipantDetails) : Parcelable
