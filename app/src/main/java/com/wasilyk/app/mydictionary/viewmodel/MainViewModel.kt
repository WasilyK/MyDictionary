package com.wasilyk.app.mydictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wasilyk.app.mydictionary.BuildConfig
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteDao
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteEntity
import com.wasilyk.app.mydictionary.model.entities.appstate.AppState
import com.wasilyk.app.mydictionary.model.entities.appstate.Error
import com.wasilyk.app.mydictionary.model.entities.appstate.Loading
import com.wasilyk.app.mydictionary.model.entities.appstate.Success
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryDao
import com.wasilyk.app.mydictionary.model.datasource.room.HistoryEntity
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import com.wasilyk.app.mydictionary.model.entities.pexels.PexelResponse
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
    private val interactor: MainInteractor,
    private val historyDao: HistoryDao,
    private val favoriteDao: FavoriteDao
) : BaseViewModel<AppState>() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _imageUrlLiveData = MutableLiveData<String>()
    val imageUrlLiveData get() = _imageUrlLiveData

    private val _favoritButtonLiveData = MutableLiveData<Boolean>()
    val favoriteButtonLiveData get() = _favoritButtonLiveData

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        scope.launch {
            liveData.postValue(Loading)
            try {
                val deferred = async { getDataAsync(word) }
                val definition = deferred.await()[0].meanings[0].definitions[0].definition
                liveData.postValue(Success(definition))
                saveToHistory(word, definition)
                getUrl(word)
                _favoritButtonLiveData.postValue(
                    findFavoriteEntityByTitle(word)
                )
            } catch(e: Exception) {
                liveData.postValue(Error(e))
            }
        }

        return super.getData(word, isOnline)
    }

    private fun getUrl(word: String) {
        interactor
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
        scope.async { favoriteDao.selectByTitle(title) != null }.await()

    fun saveToFavorite(title: String, definition: String, imageUrl: String) {
        scope.launch {
            val favoriteEntity = FavoriteEntity(0, title, definition, imageUrl)
            favoriteDao.insert(favoriteEntity)
            _favoritButtonLiveData.postValue(true)
        }
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

    private fun saveToHistory(word: String, definition: String) {
        val historyEntity = HistoryEntity(0, Date().toString(), word, definition)
        historyDao.insert(historyEntity)
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}