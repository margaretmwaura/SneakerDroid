package com.android.sneakerdroid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {

    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
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