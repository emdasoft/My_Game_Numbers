package com.emdasoft.mygamenumbers.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.emdasoft.mygamenumbers.R
import com.emdasoft.mygamenumbers.domain.entity.GameResult

@BindingAdapter("requiredAnswer")
fun bindRequiredAnswer(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_required_answer),
        count
    )
}

@BindingAdapter("scoreAnswer")
fun bindScoreAnswer(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_score_answer),
        count
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_required_percent),
        count
    )
}

@BindingAdapter("scorePercent")
fun bindScorePercent(textView: TextView, result: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_score_percent),
        getPercentOfRightAnswers(result)
    )
}

@BindingAdapter("image_smile")
fun bindImageSmile(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getPercentOfRightAnswers(result: GameResult) = with(result) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getSmileResId(winner: Boolean): Int {
    return if(winner) {
        R.drawable.fun_smile
    } else {
        R.drawable.sad_smile
    }
}