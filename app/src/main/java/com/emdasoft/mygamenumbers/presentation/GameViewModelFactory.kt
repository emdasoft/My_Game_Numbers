package com.emdasoft.mygamenumbers.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emdasoft.mygamenumbers.domain.entity.Level

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, level) as T
        } else {
            throw RuntimeException("Unknown modelClass: $modelClass")
        }
    }
}