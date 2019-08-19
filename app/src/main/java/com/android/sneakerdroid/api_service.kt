package com.android.sneakerdroid

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface api_service
{
    @POST("/api/v1/mobile/participants/")
    fun getEventTypeData(@Body user : Participant) : Deferred<RegistrationResults>

}