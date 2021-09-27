package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasilyk.app.mydictionary.BuildConfig
import com.wasilyk.app.mydictionary.model.datasource.room.favorite.FavoriteEntity
import com.wasilyk.app.mydictionary.model.datasource.room.history.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Loading
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.model.entities.dictionary.DictionaryResponse
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
import com.wasilyk.app.mydictionary.model.interactor.FavoriteInteractor
import com.wasilyk.app.mydictionary.model.interactor.HistoryInteractor
import com.wasilyk.app.mydictionary.model.interactor.MainInteractor
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainViewModel (
    private val mainInteractor: MainInteractor,
    private val historyInteractor: HistoryInteractor,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _liveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState> get() = _liveData

    private val _imageUrlLiveData = MutableLiveData<String>()
    val imageUrlLiveData get() = _imageUrlLiveData

    private val _favoriteButtonLiveData = MutableLiveData<Boolean>()
    val favoriteButtonLiveData get() = _favoriteButtonLiveData

    fun getData(word: String, isOnline: Boolean) {
        scope.launch {
            _liveData.postValue(Loading)
            try {
                val dictionaryResponse = getDictionaryResponse(word)
                val definition = dictionaryResponse[0].meanings[0].definitions[0].definition
                _liveData.postValue(Success(definition))
                saveToHistory(word, definition)
                loadImageUrl(word)
                _favoriteButtonLiveData.postValue(
                    findFavoriteEntityByTitle(word)
                )
            } catch(e: Exception) {
                _liveData.postValue(Error(e))
            }
        }
    }

    private suspend fun getDictionaryResponse(word: String): List<DictionaryResponse> =
        suspendCoroutine { continuation ->
            val call = mainInteractor.getDictionaryResponse(word, true)
            call.enqueue(object : Callback<List<DictionaryResponse>> {
                override fun onResponse(
                    call: Call<List<DictionaryResponse>>,
                    response: Response<List<DictionaryResponse>>
                ) {
                    val body = response.body()
                    if(response.isSuccessful && body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(Throwable("The request failed"))
                    }
                }

                override fun onFailure(call: Call<List<DictionaryResponse>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }

    private fun loadImageUrl(word: String) {
        mainInteractor
            .getPexelResponse(BuildConfig.PEXELS_API_KEY, word)
            .enqueue(object : Callback<PexelResponse> {
                override fun onResponse(
                    call: Call<PexelResponse>,
                    response: Response<PexelResponse>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        val url = body.photos[0].src.original
                        _imageUrlLiveData.postValue(url)
                    } else {
                        _imageUrlLiveData.postValue("error")
                    }
                }

                override fun onFailure(call: Call<PexelResponse>, t: Throwable) {
                    _imageUrlLiveData.postValue("error")
                }
            })

    }

    suspend fun findFavoriteEntityByTitle(title: String): Boolean =
        scope.async { favoriteInteractor.selectByTitle(title) != null }.await()

    fun saveToFavorite(title: String, definition: String, imageUrl: String?) {
        scope.launch {
            val favoriteEntity = FavoriteEntity(0, title, definition, imageUrl)
            favoriteInteractor.insert(favoriteEntity)
            _favoriteButtonLiveData.postValue(true)
        }
    }

    private fun saveToHistory(word: String, definition: String) {
        val historyEntity = HistoryEntity(0, Date().toString(), word, definition)
        historyInteractor.insert(historyEntity)
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}