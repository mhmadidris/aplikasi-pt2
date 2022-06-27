package org.pt2.laundry

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.pt2.laundry.model.Profile
import org.pt2.laundry.network.ProfileApi
import org.pt2.laundry.network.UpdateWorker
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val data = MutableLiveData<List<Profile>>()

    init {
        retrieveData()
    }

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            "updater",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.postValue(ProfileApi.service.getProfile())
                println(ProfileApi.service.getProfile() + " coba ")
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                println("Gagal")
            }
        }
    }
}
