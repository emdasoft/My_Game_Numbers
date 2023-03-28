package com.emdasoft.mygamenumbers.data

import com.emdasoft.mygamenumbers.domain.entity.GameSettings
import com.emdasoft.mygamenumbers.domain.entity.Level
import com.emdasoft.mygamenumbers.domain.entity.Question
import com.emdasoft.mygamenumbers.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_VISIBLE_NUMBER = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER, sum)
        val rightAnswer = sum - visibleNumber
        val options = HashSet<Int>()
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_VISIBLE_NUMBER)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }

        return Question(sum, visibleNumber, options.toList())

    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    5,
                    80,
                    8
                )
            }
            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    50,
                    300
                )
            }
            Level.NORMAL -> {
                GameSettings(
                    20,
                    15,
                    80,
                    300
                )
            }
            Level.HARD -> {
                GameSettings(
                    100,
                    20,
                    80,
                    300
                )
            }
        }
    }
}