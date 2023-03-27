package com.emdasoft.mygamenumbers.domain.repository

import com.emdasoft.mygamenumbers.domain.entity.GameSettings
import com.emdasoft.mygamenumbers.domain.entity.Level
import com.emdasoft.mygamenumbers.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings

}