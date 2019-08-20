package com.android.sneakerdroid.presenterpackage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sneakerdroid.Model.Participant
import com.android.sneakerdroid.Model.RegistrationResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel  : ViewModel()
{

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<RegistrationResults>()

    // The external immutable LiveData for the response String
    val response: LiveData<RegistrationResults>
        get() = _response

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {

    }


    public fun registerUser(participant: Participant) {
        coroutineScope.launch {


            val participant = RetrofitFactory.makeRetrofitService().getEventTypeData(participant)
            try {
                // Await the completion of our Retrofit request
                var listResult = participant.await()
                _response.value = listResult
            } catch (e: Exception) {
                Log.d("Error","There was a huge error ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}