package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import com.wasilyk.app.mydictionary.model.appstate.AppState
import com.wasilyk.app.mydictionary.model.appstate.Error
import com.wasilyk.app.mydictionary.model.appstate.Loading
import com.wasilyk.app.mydictionary.model.appstate.Success
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import com.wasilyk.app.mydictionary.model.interactor.MainInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainViewModel (
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        CoroutineScope(Job()).launch {
            liveData.postValue(Loading)
            try {
                val deferred = async { getDataAsync(word) }
                val definition = deferred.await()[0].meanings[0].definitions[0].definition
                liveData.postValue(Success(definition))
            } catch(e: Exception) {
                liveData.postValue(Error(e))
            }
        }

        return super.getData(word, isOnline)
    }

    private suspend fun getDataAsync(word: String): List<WordDefinition> =
        suspendCoroutine { continuation ->
            val call = interactor.getDataAsync(word, true)
            call.enqueue(object : Callback<List<WordDefinition>> {
                override fun onResponse(
                    call: Call<List<WordDefinition>>,
                    response: Response<List<WordDefinition>>
                ) {
                    val body = response.body()
                    if(response.isSuccessful && body != null) {
                            continuation.resume(body)
                    } else {
                        continuation.resumeWithException(Throwable("The request failed"))
                    }
                }

                override fun onFailure(call: Call<List<WordDefinition>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }

    fun subscribe(): LiveData<AppState> = liveData
}

