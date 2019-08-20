package com.android.sneakerdroid.presenterpackage

import com.android.sneakerdroid.Model.Participant
import com.android.sneakerdroid.Model.RegistrationResults
import com.android.sneakerdroid.Model.SampleResponse
import com.android.sneakerdroid.Model.UploadRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface api_service
{
    @POST("/api/v1/mobile/participants/")
    fun getEventTypeData(@Body user : Participant) : Deferred<RegistrationResults>


    @POST("/api/v1/mobile/probes-data/create-bulk")
    fun upLoadRequest(@Body user : UploadRequest) : Response<SampleResponse>
}