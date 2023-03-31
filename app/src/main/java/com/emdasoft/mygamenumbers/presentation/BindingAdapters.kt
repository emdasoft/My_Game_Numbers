package com.emdasoft.mygamenumbers.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.emdasoft.mygamenumbers.R
import com.emdasoft.mygamenumbers.domain.entity.GameResult

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

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

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("barAnswersProgress")
fun bindBarAnswersProgress(progressBar: ProgressBar, count: Int) {
    progressBar.setProgress(count, true)
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, state: Boolean) {
    val color = getColorByState(progressBar.context, state)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, state: Boolean) {
    val color = getColorByState(textView.context, state)
    textView.setTextColor(color)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

private fun getPercentOfRightAnswers(result: GameResult) = with(result) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.fun_smile
    } else {
        R.drawable.sad_smile
    }
}

private fun getColorByState(context: Context, state: Boolean): Int {
    val colorResId = if (state) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}