package ru.hse.electivehw1.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.hse.electivehw1.domain.models.RandomNumberInputParams
import ru.hse.electivehw1.domain.usecase.GetRandomNumberUseCase

class MainViewModel : ViewModel() {
    // юзкейсу не нужен контекст, поэтому можно создать его здесь
    // а до DI я еще не доросла...
    private val getRandomNumberUseCase = GetRandomNumberUseCase()

    private val _randomNumber = MutableLiveData<Double>()
    val randomNumber: LiveData<Double> get() = _randomNumber

    private val _mu = MutableLiveData<Double?>()

    private val _sigma2 = MutableLiveData<Double?>()

    // для отображения сообщений о необходимости ввести данные
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    // генерация случайного числа
    fun generateNumber() {
        // проверка данных, обновление сообщения при необходимости
        if (_mu.value == null) {
            _toastMessage.value = "Please, enter µ"
            return
        }
        if (_sigma2.value == null) {
            _toastMessage.value = "Please, enter σ^2"
            return
        }
        // использование соответствующего юзкейса
        _randomNumber.value = getRandomNumberUseCase.execute(
            RandomNumberInputParams(_mu.value!!, _sigma2.value!!)
        ).number
    }

    // функции обновления значений
    fun setMu(value: Double?) {
        _mu.value = value
    }

    fun setSigma2(value: Double?) {
        _sigma2.value = value
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
