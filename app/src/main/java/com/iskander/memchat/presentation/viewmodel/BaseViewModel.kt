package com.iskander.memchat.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iskander.memchat.domain.type.HandleOnce
import com.iskander.memchat.domain.type.exception.Failure

abstract class BaseViewModel: ViewModel() {
    /**
     *  объект LiveData, содержащий ошибку(Failure).
     *  Обернут в класс HandleOnce для предотвращения повторных отрисовок
     *  одних и тех же ошибок.
     */
    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()

    /**
     * сеттер, присваивающий ошибку. Обертывает в HandleOnce.
     * Принимает Failure. Ничего не возвращает.
     */
    protected fun handleFailure(failure: Failure) {
        this.failureData.value = HandleOnce(failure)
    }
}