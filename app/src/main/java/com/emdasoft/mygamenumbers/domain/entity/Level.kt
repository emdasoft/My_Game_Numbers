package com.emdasoft.mygamenumbers.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {
    EASY, NORMAL, HARD
}