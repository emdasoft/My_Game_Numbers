package com.emdasoft.mygamenumbers.domain.usecases

import com.emdasoft.mygamenumbers.domain.entity.Question
import com.emdasoft.mygamenumbers.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}