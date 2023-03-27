package com.emdasoft.mygamenumbers.domain.usecases

import com.emdasoft.mygamenumbers.domain.entity.GameSettings
import com.emdasoft.mygamenumbers.domain.entity.Level
import com.emdasoft.mygamenumbers.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}