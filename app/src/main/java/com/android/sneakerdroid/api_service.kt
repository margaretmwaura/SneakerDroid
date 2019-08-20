package com.android.sneakerdroid

import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface api_service
{
    @POST("/api/v1/mobile/participants/")
    fun getEventTypeData(@Body user : Participant) : Deferred<RegistrationResults>

}