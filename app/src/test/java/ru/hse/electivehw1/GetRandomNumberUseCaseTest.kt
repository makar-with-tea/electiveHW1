package ru.hse.electivehw1

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.hse.electivehw1.domain.models.RandomNumberInputParams
import ru.hse.electivehw1.domain.usecase.GetRandomNumberUseCase
import kotlin.math.exp
import kotlin.math.sqrt

class GetRandomNumberUseCaseTest {

    // тест написан по аналогии с instrumented test, но только для юзкейса
    @Test
    fun testLogNormalDistribution() {
        val useCase = GetRandomNumberUseCase()
        val inputParams = RandomNumberInputParams(mu = 0.0, sigma2 = 0.1)
        val generatedNums = ArrayList<Double>()
        val sampleSize = 10000

        repeat(sampleSize) {
            val result = useCase.execute(inputParams)
            generatedNums.add(result.number)
        }

        checkLogNorm(
            generatedNums,
            exp(inputParams.mu + inputParams.sigma2 / 2.0),
            exp(2 * inputParams.mu + inputParams.sigma2) * (exp(inputParams.sigma2) - 1),
            sqrt(exp(inputParams.sigma2) - 1) * (exp(inputParams.sigma2) + 2),
            exp(4 * inputParams.sigma2) + 2 * exp(3 * inputParams.sigma2) + 3 * exp(2 * inputParams.sigma2) - 6
        )
    }

    private fun checkLogNorm(a: ArrayList<Double>, m: Double, v: Double, sk: Double, kur: Double) {
        val d = a.toDoubleArray()
        val gm = StatUtils.mean(d)
        val gv = StatUtils.variance(d)
        val gskewness = DescriptiveStatistics(d).skewness
        val gkurtosis = DescriptiveStatistics(d).kurtosis
        val meanDelta = 0.1
        val varianceDelta = 0.1
        val skewnessDelta = 0.1
        val kurtosisDelta = 0.1

        assertEquals("Mean is different", gm, m, meanDelta)
        assertEquals("Variance is different", gv, v, varianceDelta)
        assertEquals("Skewness is different", gskewness, sk, skewnessDelta)
        assertEquals("Kurtosis is different", gkurtosis, kur, kurtosisDelta)
    }
}
