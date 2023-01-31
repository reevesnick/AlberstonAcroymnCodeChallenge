package com.nr.acronyminitalismcodechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nr.acronyminitalismcodechallenge.model.LFSModel
import com.nr.acronyminitalismcodechallenge.model.SfModel
import com.nr.acronyminitalismcodechallenge.model.VarsModel
import com.nr.acronyminitalismcodechallenge.repo.SfRepo
import kotlinx.coroutines.*

class SfViewModel constructor(private val sfRepo: SfRepo): ViewModel() {

    val errorMessage = MutableLiveData<String>() // Error Message Exception
    val loading = MutableLiveData<Boolean>() // For Loading Indicator state // Todo: All this later if time persists

    val sfData = MutableLiveData<List<SfModel>>()
    val searchInput = MutableLiveData<String>()

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    // Method from the Repo to View Model
    fun getAbbrivatedData(abbr: String){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = sfRepo.getAbbrData(abbr)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    sfData.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()} ")
                    loading.postValue(false)
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}