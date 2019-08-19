package com.android.sneakerdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ParticipantDetails (var firstName: String,
                               var lastName: String,
                               var id: String,
                               var phoneno: String) : Parcelable

