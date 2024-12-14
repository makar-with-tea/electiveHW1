package ru.hse.electivehw1.domain.usecase

import org.apache.commons.math3.distribution.LogNormalDistribution
import ru.hse.electivehw1.domain.models.RandomNumberInputParams
import ru.hse.electivehw1.domain.models.RandomNumberResult
import kotlin.math.sqrt

class GetRandomNumberUseCase {
    fun execute(inputParams: RandomNumberInputParams): RandomNumberResult {
        val logNormalDistribution = LogNormalDistribution(inputParams.mu, sqrt(inputParams.sigma2))
        val randomValue = logNormalDistribution.sample()
        return RandomNumberResult(randomValue)
    }
}
