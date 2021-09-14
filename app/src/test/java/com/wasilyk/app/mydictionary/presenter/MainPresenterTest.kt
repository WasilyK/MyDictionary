package com.wasilyk.app.mydictionary.presenter

import com.wasilyk.app.mydictionary.model.datasource.DataSource
import com.wasilyk.app.mydictionary.model.entities.Definition
import com.wasilyk.app.mydictionary.model.entities.Meaning
import com.wasilyk.app.mydictionary.model.entities.WordDefinition
import com.wasilyk.app.mydictionary.view.MainView
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    private lateinit var presenter: MainPresenter
    private val definitions = "used as a greeting or to begin a phone conservation"
    private val wordDefinitions = listOf(
        WordDefinition(
            listOf(
                Meaning(
                    listOf(Definition(definitions))
                )
            )
        )
    )

    @Mock
    lateinit var dataSource: DataSource
    @Mock
    lateinit var view: MainView

    @Before
    fun setUp() {
        presenter = MainPresenter(
            dataSource,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        presenter.attachActivity(view)
    }

    /*
    * проверка вызыва метода view.showDefinitions при удачном запросе
    */
    @Test
    fun shouldShowWordDefinition() {
        `when`(view.getWord())
            .thenReturn("hello")
        `when`(dataSource.getListWordDefinition(view.getWord()))
            .thenReturn(Single.just(wordDefinitions))

        presenter.showWordDefinition()

        verify(view)?.showWordDefinition(definitions)
    }

    /*
    * проверка вызова метода view.showToast при ошибке
     */
    @Test
    fun shouldShowErrorIfDatasourceFalls() {
        val throwable = Throwable("error")
        `when`(view.getWord())
            .thenReturn("hello")
        `when`(dataSource.getListWordDefinition(view.getWord()))
            .thenReturn(Single.error(throwable))

        presenter.showWordDefinition()

        verify(view)?.showToast(throwable.message ?: "Unknown error")
    }
}