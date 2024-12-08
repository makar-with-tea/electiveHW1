package ru.hse.electivehw1.domain.usecase

import ru.hse.electivehw1.domain.models.RandomNumber
import ru.hse.electivehw1.domain.models.RandomNumberInputParams
import java.util.Random
import kotlin.math.exp
import kotlin.math.sqrt

class GetRandomNumberUseCase {
    fun execute(inputParams: RandomNumberInputParams): RandomNumber {
        val sigma = sqrt(inputParams.sigma2)
        val mu = inputParams.mu
        val normalRandom = Random().nextGaussian() * sigma + mu
        return RandomNumber(exp(normalRandom))
    }
}