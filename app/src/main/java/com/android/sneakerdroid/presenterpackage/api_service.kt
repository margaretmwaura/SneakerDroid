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

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/v1/mobile/participants/")
    fun getEventTypeData(@Field("first_name") firstname : String ,
                          @Field("last_name") lastname : String,
                          @Field("phone_number") phonenumber : String,
                           @Field("project_code") projectCode : String ,
                           @Field("app_version") appversion : Int,
                           @Field("fcm_key") fcmkey : Long,
                            @Field("device_details") devicedetails : String) : Deferred<RegistrationResults>



    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/v1/mobile/probes-data/create-bulk")
    fun upLoadRequest(@Field("app_version") appversion: Int,
                      @Field("participant_id") participant_id : String,
                      @Field("probes_data") data : String) : Response<SampleResponse>
}